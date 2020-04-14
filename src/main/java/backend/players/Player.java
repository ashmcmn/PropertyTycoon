package backend.players;

import backend.board.Board;
import backend.board.PropertySquare;
import backend.party.Party;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Player.
 *
 * @author Ashley McManamon
 */
public class Player implements Party {
    private Token token;
    private int cash;
    private int position;
    private Board board;
    private List<PropertySquare> properties;

    /**
     * Instantiates a new Player.
     *
     * @param token the token to represent the player's position on the board
     * @param cash  the amount of cash currently held by the player
     * @param board the board
     */
    public Player(Token token, int cash, Board board) {
        this.token = token;
        this.cash = cash;
        this.position = 0;
        this.board = board;
        this.properties = new ArrayList<>();
    }

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
     */
    public void move(int amount, boolean collectSalary) {
        int newPosition = (getPosition() + amount) % board.getSquares().length;

        if(newPosition < getPosition() && collectSalary){
            setCash(getCash() + 200);
        }

        setPosition(newPosition);
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
}
