package main.backend.board;

import main.backend.dice.Dice;
import main.backend.party.Bank;

/**
 * The type Board.
 *
 * @author Ashley McManamon
 */
public class Board {
    private Square[] squares;
    private Dice dice;
    private Bank bank;

    /**
     * Instantiates a new Board.
     *
     * @param squares the squares the board contains
     * @param bank    the bank
     */
    public Board(Square[] squares, Bank bank) {
        this.squares = squares;
        this.dice = new Dice();
        this.bank = bank;
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

    /**
     * Gets bank.
     *
     * @return the bank
     */
    public Bank getBank() {
        return bank;
    }

    /**
     * Sets bank.
     *
     * @param bank the bank
     */
    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
