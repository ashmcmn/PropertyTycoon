package main.backend.players;

import main.backend.board.Board;

/**
 * The type Player.
 *
 * @author Ashley McManamon
 */
public class Player {
    private Token token;
    private int cash;
    private int position;
    private Board board;

    /**
     * Instantiates a new Player.
     *
     * @param token the token to represent the player's position on the board
     * @param cash  the amount of cash currently held by the player
     */
    public Player(Token token, int cash, Board board) {
        this.token = token;
        this.cash = cash;
        this.position = 0;
        this.board = board;
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
     * @param amount the amount of squares to travel
     */
    public void move(int amount) {
        int newPosition = (getPosition() + amount) % board.getSquares().length;
        setPosition(newPosition);
    }
}
