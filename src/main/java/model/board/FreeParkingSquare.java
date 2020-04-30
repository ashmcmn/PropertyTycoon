package model.board;

import model.players.Player;
import model.transactions.Transaction;

/**
 * The type Free parking square.
 */
public class FreeParkingSquare extends Square {
    /**
     * Instantiates a new Square.
     *
     * @param name the name of the square
     */
    public FreeParkingSquare(String name) {
        super(name);
    }

    /**
     * Gives the player the accumulated fines
     *
     * @param player the player
     * @param board  the board
     */
    @Override
    public void doAction(Player player, Board board) {
        LOG.debug("Giving " + player.getName() + " accumulated fines of " + board.getFreeParking().getCash());
        Transaction transaction = new Transaction(board.getFreeParking(), player, new Object[]{board.getFreeParking().getCash()}, new Object[]{});
        transaction.settle();
    }
}
