package backend.players;

import backend.board.Board;
import backend.board.PropertySquare;
import backend.board.Square;
import backend.party.Party;

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
    private int goof;

    /**
     * Instantiates a new Player.
     *
     * @param name the player's name
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
     * Sets token.
     *
     * @param token The token to give to the player
     */
    public void setToken(Token token) {
        this.token = token;
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

        if(newPosition < getPosition() && collectSalary){
            setCash(getCash() + 200);
        }

        setPosition(newPosition);

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
}
