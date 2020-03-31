package main.backend.board;

import main.backend.dice.Dice;

/**
 * The type Board.
 *
 * @author Ashley McManamon
 */
public class Board {
    private Square[] squares;
    private Dice dice;

    /**
     * Instantiates a new Board.
     *
     * @param squares the squares the board contains
     */
    public Board(Square[] squares) {
        this.squares = squares;
        this.dice = new Dice();
    }

    /**
     * Gets the squares.
     *
     * @return the squares
     */
    public Square[] getSquares() {
        return squares;
    }

    /**
     * Sets squares.
     *
     * @param squares the squares
     */
    public void setSquares(Square[] squares) {
        this.squares = squares;
    }

    /**
     * Gets dice.
     *
     * @return the dice
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * Sets dice.
     *
     * @param dice the dice
     */
    public void setDice(Dice dice) {
        this.dice = dice;
    }
}
