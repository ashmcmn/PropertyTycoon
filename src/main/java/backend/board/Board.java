package backend.board;

import backend.dice.Dice;
import backend.party.Bank;
import backend.party.FreeParking;
import backend.players.Player;

import java.util.List;

/**
 * The type Board.
 *
 * @author Ashley McManamon
 */
public class Board {
    private Square[] squares;
    private Dice dice;
    private Bank bank;
    private FreeParking freeParking;
    private List<Player> players;

    /**
     * Instantiates a new Board.
     *
     * @param squares the squares the board contains
     * @param bank    the bank
     * @param players array of players for the game
     */
    public Board(Square[] squares, Bank bank, List<Player> players) {
        this.squares = squares;
        this.dice = new Dice();
        this.bank = bank;
        this.freeParking = new FreeParking(0);
        this.players = players;
        for (Player player : players
             ) {
            player.setBoard(this);
        }
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

    /**
     * Gets free parking.
     *
     * @return the free parking
     */
    public FreeParking getFreeParking() { return freeParking; }

    public Player getPlayer(int index) { return players.get(index); }

    public void addPlayer(Player player) { players.add(player); }

    public void removePlayer(Player player) { players.remove(player); }
}
