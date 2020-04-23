package backend.game;

import backend.board.*;
import backend.cards.Action;
import backend.cards.Card;
import backend.cards.CardPile;
import backend.dice.Dice;
import backend.party.Bank;
import backend.party.Party;
import backend.players.Player;
import backend.players.Token;
import backend.transactions.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Game manager.
 */
public class GameManager {
    private Board board;
    private Player currentPlayer;
    private List<Player> players;
    private boolean ended = false;
    private Dice dice;
    private int turn = 1;
    private static final Logger LOG = LogManager.getLogger(GameManager.class);

    /**
     * Instantiates a new Game manager.
     *
     * @param playerNames the names of the players
     */
    public GameManager(String[] playerNames) {


        players = new LinkedList<>();
        dice = new Dice();

        List<Token> tokens = new ArrayList<>(List.of(Token.values()));
        Random rand = new Random();

        for (String playerName : playerNames) {
            players.add(new Player(playerName, tokens.remove(rand.nextInt(tokens.size())), 1500));
        }

        Collections.shuffle(players);

        this.board = new Board(new Square[]{}, new Bank(50000), players);

        loadConfig("config.json");
    }

    /**
     * Gets board.
     *
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Sets board.
     *
     * @param board the board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Gets the current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the current player.
     *
     * @return the current player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public static void main(String[] args){
        GameManager gameManager = new GameManager(new String[]{"P1", "P2"});
        gameManager.startGame();
    }

    /**
     * Starts the game.
     */
    public void startGame(){
        LOG.debug("Starting the game");
        currentPlayer = board.getPlayer(0);

        while (!ended && turn < 1000){
            LOG.debug(currentPlayer.getName() + " is taking their turn, " + turn);
            if(currentPlayer.isJailed()){
                LOG.debug(currentPlayer.getName() + " is in jail");
                currentPlayer.addJailedTurn();
                if(!currentPlayer.useGoof()){
                    Transaction transaction = new Transaction(currentPlayer, board.getBank(), new Object[]{50}, new Object[]{});
                    if(currentPlayer.getJailedTurns() == 3 && transaction.canSettle()){
                        transaction.settle();
                        currentPlayer.releaseFromJail();
                        LOG.debug(currentPlayer.getName() + " paid out of jail");
                    }
                    else{
                        if(players.indexOf(currentPlayer) == players.size()-1){
                            currentPlayer = players.get(0);
                        }
                        else{
                            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
                        }
                        continue;
                    }
                }
                else LOG.debug(currentPlayer.getName() + " used a goof card");
            }

            dice.roll();
            int[] result = dice.getResult();
            LOG.debug(currentPlayer.getName() + " rolls the dice with a result of " + result[0] + " and " + result[1]);

            Square square = currentPlayer.move(IntStream.of(result).sum(), true);
            LOG.debug(currentPlayer.getName() + " landed on " + square.getName() + "(position: " + square.getPosition() + ")");
            square.doAction(currentPlayer, board);

            while(dice.wasDouble()) {
                LOG.debug("A double was rolled so " + currentPlayer.getName() + " takes another turn");
                dice.roll();
                result = dice.getResult();

                if(dice.getDoubles() == 3){
                    currentPlayer.sendToJail();
                    dice.resetDoubles();
                    break;
                }

                square = currentPlayer.move(IntStream.of(result).sum(), true);
                square.doAction(currentPlayer, board);
            }

            turn++;
            if(players.indexOf(currentPlayer) == players.size()-1){
                currentPlayer = players.get(0);
            }
            else{
                currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
            }
        }
    }

    /**
     * Load game config from json file
     *
     * @param configPath the config file's path
     */
    public void loadConfig(String configPath){
        JSONParser parser = new JSONParser();

        Object obj = null;
        try {
            obj = parser.parse(new FileReader(configPath));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject =  (JSONObject) obj;

        JSONObject board = (JSONObject) jsonObject.get("board");

        JSONArray importCardPiles = (JSONArray) board.get("cardPiles");
        Map<String, CardPile> cardPiles = new HashMap<>();

        LOG.debug("Importing card piles...");
        for(Object pile : importCardPiles) {
            List<Card> cards = new ArrayList<>();
            JSONArray importCards = (JSONArray) ((JSONObject) pile).get("cards");

            LOG.debug("Importing card pile" + ((JSONObject) pile).get("name") + " with " + importCards.size() + " cards");

            for(Object importCard : importCards) {
                JSONObject jsonCard = (JSONObject) importCard;
                Card card = null;
                if(jsonCard.get("actiontype").equals("Pay")) {
                    if (jsonCard.get("from").equals("All")) {
                        card = new Card((String) jsonCard.get("description"), new Action() {
                            public void action() {
                                for (Player player : players) {
                                    if (player != getCurrentPlayer()) {
                                        Transaction transaction = new Transaction(player, getCurrentPlayer(), new Object[]{((Long) jsonCard.get("amount")).intValue()}, new Object[]{});
                                        if (transaction.canSettle()) {
                                            transaction.settle();
                                        }
                                    }
                                }
                            }
                        });
                    } else {
                        Callable from;
                        Callable to;
                        switch (((String) jsonCard.get("from"))) {
                            case "Bank":
                                from = () -> getBoard().getBank();
                                break;
                            case "Player":
                                from = this::getCurrentPlayer;
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + ((String) jsonCard.get("from")));
                        }

                        switch (((String) jsonCard.get("to"))) {
                            case "Bank":
                                to = () -> getBoard().getBank();
                                break;
                            case "Player":
                                to = this::getCurrentPlayer;
                                break;
                            case "FreeParking":
                                to = () -> getBoard().getFreeParking();
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + ((String) jsonCard.get("to")));
                        }

                        card = new Card((String) jsonCard.get("description"), new Action() {
                            public void action() {
                                Transaction transaction = null;
                                try {
                                    transaction = new Transaction((Party) from.call(), (Party) to.call(), new Object[]{((Long) jsonCard.get("amount")).intValue()}, new Object[]{});
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (transaction.canSettle())
                                    transaction.settle();
                            }
                        });
                    }
                }
                else if(jsonCard.get("actiontype").equals("Move")){
                    int where = ((Long) jsonCard.get("where")).intValue();
                    boolean collect = (boolean) jsonCard.get("collect");

                    card = new Card((String) jsonCard.get("description"), new Action() {
                        public void action() {
                            currentPlayer.move(where - currentPlayer.getPosition(), collect);
                        }
                    });
                }
                else if(jsonCard.get("actiontype").equals("MoveBack")){
                    int where = ((Long) jsonCard.get("where")).intValue();

                    card = new Card((String) jsonCard.get("description"), new Action() {
                        public void action() {
                            currentPlayer.move(currentPlayer.getPosition() - where, false);
                        }
                    });
                }
                else if(jsonCard.get("actiontype").equals("PayDraw")){

                        String newPile = (String) jsonCard.get("pile");

                        Callable from;
                        Callable to;
                        switch(((String) jsonCard.get("from"))){
                            case "Bank":
                                from = () -> getBoard().getBank();
                                break;
                            case "Player":
                                from = () -> getCurrentPlayer();
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + ((String) jsonCard.get("from")));
                        }

                        switch(((String) jsonCard.get("to"))){
                            case "Bank":
                                to = () -> getBoard().getBank();
                                break;
                            case "Player":
                                to = () -> getCurrentPlayer();
                                break;
                            case "FreeParking":
                                to = () -> getBoard().getFreeParking();
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + ((String) jsonCard.get("to")));
                        }

                        card = new Card((String) jsonCard.get("description"), new Action() {
                            public void action() {
                                Random rand = new Random();
                                if(rand.nextInt(2) == 0) {
                                    Transaction transaction = null;
                                    try {
                                        transaction = new Transaction((Party) from.call(), (Party) to.call(), new Object[]{((Long) jsonCard.get("amount")).intValue()}, new Object[]{});
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (transaction.canSettle())
                                        transaction.settle();
                                }
                                else {
                                    Card newCard = cardPiles.get(newPile).draw();
                                    if(newCard.getDescription().equals("Get out of jail free")){
                                        currentPlayer.addGoof();
                                    }
                                    else{
                                        newCard.doAction();
                                        cardPiles.get(newPile).addCard(newCard);
                                    }
                                }
                            }
                        });
                }
                else if(jsonCard.get("actiontype").equals("GoJail")){
                    card = new Card((String) jsonCard.get("description"), new Action() {
                        public void action() {
                            currentPlayer.sendToJail();
                        }
                    });
                }
                else if(jsonCard.get("actiontype").equals("PayRes")){
                    JSONArray prices = (JSONArray) jsonCard.get("amount");
                    card = new Card((String) jsonCard.get("description"), new Action() {
                        public void action() {
                            int total = 0;
                            for(PropertySquare prop : getCurrentPlayer().getProperties()){
                                if(prop.getLevel() == 5){
                                    total += ((Long) prices.get(1)).intValue();
                                }
                                else{
                                    total += ((Long) prices.get(0)).intValue() * prop.getLevel();
                                }
                            }
                            Transaction transaction = new Transaction(getCurrentPlayer(), getBoard().getBank(), new Object[]{total}, new Object[]{});
                            if(transaction.canSettle()){
                                transaction.settle();
                            }
                        }
                    });
                }
                else if(jsonCard.get("actiontype").equals("GOOF")){
                    card = new Card((String) jsonCard.get("description"), new Action() {
                        public void action() {

                        }
                    });
                }

                LOG.debug("Importing " + ((JSONObject) pile).get("name")+ " card: " + card.getDescription());
                cards.add(card);
            }

            cardPiles.put((String) ((JSONObject) pile).get("name"), new CardPile(cards));
        }


        JSONArray importSquares = (JSONArray) board.get("squares");

        Square[] squares = new Square[importSquares.size()];

        LOG.debug("Importing squares");
        for(int i = 0; i < importSquares.size(); i++){
            JSONObject importSquare = (JSONObject) importSquares.get(i);

            Square newSquare = null;

            boolean ownable = (importSquare.get("ownable")).equals("Yes");
            if(importSquare.get("name").equals("Go")){
                newSquare = new GoSquare("Go");
                LOG.debug("Adding go square");
            }
            else if(importSquare.get("action") != null && importSquare.get("action").equals("Take card")){
                CardPile cardPile = cardPiles.get(importSquare.get("name"));
                newSquare = new CardDrawSquare((String) importSquare.get("name"), cardPile);
                LOG.debug("Adding card draw square for " + newSquare.getName());
            }
            else if(((String) importSquare.get("name")).contains("Tax")){
                newSquare = new TaxSquare((String) importSquare.get("name"), Integer.parseInt(((String) importSquare.get("action")).split("£")[1]));
                LOG.debug("Adding tax square");
            }
            else if(importSquare.get("name").equals("Jail/Just visiting")){
                newSquare = new JailSquare("Jail/Just visiting");
                LOG.debug("Adding jail square");
            }
            else if(importSquare.get("name").equals("Free Parking")){
                newSquare = new FreeParkingSquare("Free Parking");
                LOG.debug("Adding free parking square");
            }
            else if(importSquare.get("name").equals("Go to Jail")){
                newSquare = new GoJailSquare("Go to Jail");
                LOG.debug("Adding go to jail square");
            }
            else if(ownable){
                JSONArray importRents = (JSONArray) importSquare.get("rent");
                int[] rents = new int[importRents.size()];
                for(int j = 0; j < rents.length; j++){
                    rents[j] = ((Long) importRents.get(j)).intValue();
                }
                Group group;

                switch ((String) importSquare.get("group")){
                    case "Brown":
                        group = Group.BROWN;
                        break;
                    case "Blue":
                        group = Group.BLUE;
                        break;
                    case "Purple":
                        group = Group.PURPLE;
                        break;
                    case "Orange":
                        group = Group.ORANGE;
                        break;
                    case "Red":
                        group = Group.RED;
                        break;
                    case "Yellow":
                        group = Group.YELLOW;
                        break;
                    case "Green":
                        group = Group.GREEN;
                        break;
                    case "Deep blue":
                        group = Group.DEEPBLUE;
                        break;
                    case "Station":
                        group = Group.STATION;
                        break;
                    case "Utilities":
                        group = Group.UTILITIES;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + (String) ((JSONObject) importSquare).get("group"));
                }
                newSquare = new PropertySquare((String) importSquare.get("name"), this.getBoard().getBank(), rents, group, getBoard(), ((Long) importSquare.get("cost")).intValue());
                getBoard().getBank().addProperty((PropertySquare) newSquare);
                LOG.debug("Adding property square: " + newSquare.getName());
            }
            newSquare.setPosition(((Long) importSquare.get("position")).intValue());
            squares[i] = newSquare;
        }

        JSONArray importImprovementCosts = (JSONArray) board.get("propertyCosts");
        Map<Group,int[]> improvementCosts = new HashMap<>();

        LOG.debug("Importing improvement costs");
        for(Object o : importImprovementCosts) {
            Group group;
            switch ((String) ((JSONObject) o).get("group")){
                case "Brown":
                    group = Group.BROWN;
                    break;
                case "Blue":
                    group = Group.BLUE;
                    break;
                case "Purple":
                    group = Group.PURPLE;
                    break;
                case "Orange":
                    group = Group.ORANGE;
                    break;
                case "Red":
                    group = Group.RED;
                    break;
                case "Yellow":
                    group = Group.YELLOW;
                    break;
                case "Green":
                    group = Group.GREEN;
                    break;
                case "Deep blue":
                    group = Group.DEEPBLUE;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + (String) ((JSONObject) o).get("group"));
            }

            int[] costs = new int[2];
            costs[0] = ((Long) ((JSONArray) ((JSONObject) o).get("costs")).get(0)).intValue();
            costs[1] = ((Long) ((JSONArray) ((JSONObject) o).get("costs")).get(1)).intValue();

            improvementCosts.put(group, costs);
        }

        this.getBoard().setImprovementCosts(improvementCosts);
        this.getBoard().setSquares(squares);


    }


}
