package model.board;

import model.dice.Dice;
import model.party.Bank;
import model.party.FreeParking;
import model.players.Player;

import java.util.List;
import java.util.Map;

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
    private Map<Group,int[]> improvementCosts;

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

    /**
     * Gets player.
     *
     * @param index the index
     * @return the player
     */
    public Player getPlayer(int index) { return players.get(index); }

    /**
     * Add player.
     *
     * @param player the player
     */
    public void addPlayer(Player player) { players.add(player); }

    /**
     * Remove player.
     *
     * @param player the player
     */
    public void removePlayer(Player player) { players.remove(player); }

    /**
     * Sets improvement costs.
     *
     * @param improvementCosts the improvement costs
     */
    public void setImprovementCosts(Map<Group, int[]> improvementCosts) {
        this.improvementCosts = improvementCosts;
    }

    /**
     * Get improvement cost for a given group and property type.
     *
     * @param group the group
     * @param type  the type
     * @return the cost
     */
    public int getImprovementCost(Group group, int type){
        return improvementCosts.get(group)[type];
    }
}
