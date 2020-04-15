package backend.game;

import backend.board.*;
import backend.dice.Dice;
import backend.party.Bank;
import backend.players.Player;
import backend.players.Token;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

/**
 * The type Game manager.
 */
public class GameManager {
    private Board board;
    private Player currentPlayer;
    private List<Player> players;
    private boolean ended = false;
    private Dice dice;

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
     * Starts the game.
     */
    public void startGame(){
        currentPlayer = board.getPlayer(0);

        while (!ended){
            dice.roll();
            int[] result = dice.getResult();

            Square square = currentPlayer.move(IntStream.of(result).sum(), true);
            //square.doAction(currentPlayer, board);

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

        JSONArray importSquares = (JSONArray) board.get("squares");

        Square[] squares = new Square[importSquares.size()];

        for(int i = 0; i < importSquares.size(); i++){
            JSONObject importSquare = (JSONObject) importSquares.get(i);

            Square newSquare = null;

            boolean ownable = (importSquare.get("ownable")).equals("Yes");
            if(importSquare.get("name").equals("Go")){
                newSquare = new GoSquare("Go");
            }
            else if(importSquare.get("name").equals("Pot Luck")){
                newSquare = new CardDrawSquare("Pot Luck");
            }
            else if(importSquare.get("name").equals("Income Tax")){
                newSquare = new TaxSquare("Income Tax");
            }
            else if(importSquare.get("name").equals("Opportunity Costs")){
                newSquare = new CardDrawSquare("Opportunity Costs");
            }
            else if(importSquare.get("name").equals("Jail/Just visiting")){
                newSquare = new JailSquare("Jail/Just visiting");
            }
            else if(importSquare.get("name").equals("Free Parking")){
                newSquare = new FreeParkingSquare("Free Parking");
            }
            else if(importSquare.get("name").equals("Go to jail")){
                newSquare = new GoJailSquare("Go to jail");
            }
            else if(importSquare.get("name").equals("Super Tax")){
                newSquare = new TaxSquare("Super Tax");
            }
            else if(ownable){
                newSquare = new PropertySquare((String) importSquare.get("name"), this.getBoard().getBank());
            }

            squares[i] = newSquare;
        }

        this.getBoard().setSquares(squares);


    }


}
