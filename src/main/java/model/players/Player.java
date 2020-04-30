package model.players;

import javafx.scene.Node;
import model.board.Board;
import model.board.Group;
import model.board.PropertySquare;
import model.board.Square;
import model.party.Party;
import model.transactions.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Player.
 *
 * @author Ashley McManamon
 */
public class Player implements Party {
    private String name;
    private Token token;
    private int cash;
    private int position;
    private Board board;
    private boolean canBuy;
    private List<PropertySquare> properties;
    private boolean jailed;
    private int jailedTurns = 0;
    private int goof;
    private Node boardPiece;
    protected static final Logger LOG = LogManager.getLogger(Player.class);

    /**
     * Instantiates a new Player.
     *
     * @param name  the player's name
     * @param token the token to represent the player's position on the board
     * @param cash  the amount of cash currently held by the player
     */
    public Player(String name, Token token, int cash) {
        this.name = name;
        this.token = token;
        this.cash = cash;
        this.position = 0;
        this.properties = new ArrayList<>();
    }

    /**
     * Gets player's name.
     *
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Gets token.
     *
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    /**
     * Gets cash.
     *
     * @return the cash
     */
    public int getCash() {
        return cash;
    }

    /**
     * Sets cash.
     *
     * @param cash the amount to set the player's cash to
     */
    public void setCash(int cash) {
        this.cash = cash;
    }

    /**
     * Gets position.
     *
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets position.
     *
     * @param position the position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Gets the board the player is on.
     *
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Sets the board the player is on
     *
     * @param board the board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Move the player
     *
     * @param amount        the amount of squares to travel
     * @param collectSalary whether or not to collect £200 salary passing Go
     * @return the player's new square
     */
    public Square move(int amount, boolean collectSalary) {
        int newPosition = (getPosition() + amount) % board.getSquares().length;

        if(newPosition <= getPosition() && collectSalary){
            setCash(getCash() + 200);
        }

        setPosition(newPosition);

        LOG.debug(getName() + " landed on " + board.getSquares()[newPosition].getName() + "(position: " + board.getSquares()[newPosition].getPosition() + ")");

        return board.getSquares()[newPosition];
    }

    /**
     * Gets properties owned by the player
     *
     * @return the properties
     */
    public List<PropertySquare> getProperties() {
        return properties;
    }

    /**
     * Add a property to the player's ownership
     *
     * @param property the property
     */
    public void addProperty(PropertySquare property) {
        this.properties.add(property);
    }

    /**
     * Remove a property from the player's ownership
     *
     * @param property the property
     */
    public void removeProperty(PropertySquare property) {
        this.properties.remove(property);
    }

    /**
     * Send player to jail.
     */
    public void sendToJail() {
        jailed = true;
        setPosition(Stream.of(board.getSquares()).filter(s -> s.getName().equals("Jail/Just visiting")).collect(Collectors.toList()).get(0).getPosition() - 1);
    }

    /**
     * Give a player a get out of jail free card
     */
    public void addGoof() {
        goof++;
    }

    public boolean ownsGroup(Group group){
        return getProperties().containsAll(Stream.of(board.getSquares())
                .filter(PropertySquare.class::isInstance)
                .map(PropertySquare.class::cast)
                .filter(s -> s.getGroup() == group)
                .collect(Collectors.toList()));
    }

    /**
     * Improve a given property.
     *
     * @param prop the property
     * @return whether it was successful
     */
    public boolean improve(PropertySquare prop){
        int type = 0;
        if(prop.getLevel() == 4)
            type = 1;

        if(ownsGroup(prop.getGroup())){
            Transaction transaction = new Transaction(this, board.getBank(), new Object[]{board.getImprovementCost(prop.getGroup(), type)}, new Object[]{});
            if(transaction.canSettle()){
                if(prop.addLevel())
                    transaction.settle();
                else return false;
            }
            else{
                return false;
            }

        }
        else{
            return false;
        }
        return true;
    }

    /**
     * Devalue a given property.
     *
     * @param prop the property
     * @return whether it was successful
     */
    public boolean devalue(PropertySquare prop){
        int type = 0;
        if(prop.getLevel() == 5)
            type = 1;

        if(ownsGroup(prop.getGroup())){
            Transaction transaction = new Transaction(board.getBank(), this, new Object[]{board.getImprovementCost(prop.getGroup(), type)}, new Object[]{});
            if(prop.removeLevel())
                transaction.settle();
            else return false;
        }
        else{
            return false;
        }
        return true;
    }

    /**
     * Checks if player is jailed
     *
     * @return the boolean
     */
    public boolean isJailed() {
        return jailed;
    }

    /**
     * Add a turn in jail
     */
    public void addJailedTurn() { jailedTurns++; }

    /**
     * Gets turns in jail
     *
     * @return the jailed turns
     */
    public int getJailedTurns() { return jailedTurns; }

    /**
     * Release from jail.
     */
    public void releaseFromJail() {
        jailed = false;
        jailedTurns = 0;
    }

    public boolean useGoof() {
        if(goof > 0){
            goof--;
            releaseFromJail();
            return true;
        }
        return false;
    }

    public Node getBoardPiece() {
        return boardPiece;
    }

    public void setBoardPiece(Node boardPiece) {
        this.boardPiece = boardPiece;
    }
}
