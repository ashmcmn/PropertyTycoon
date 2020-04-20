package backend.board;

import backend.players.Player;
import backend.transactions.Transaction;

/**
 * The type Tax square.
 *
 * @author Ashley McManamon
 */
public class TaxSquare extends Square {
    private int amount;

    /**
     * Instantiates a new Square.
     *
     * @param name the name of the square
     */
    public TaxSquare(String name, int amount) {
        super(name);
        this.amount = amount;
    }

    /**
     * Player pays tax to the bank
     *
     * @param player the player
     * @param board  the board
     */
    @Override
    public void doAction(Player player, Board board) {
        Transaction transaction = new Transaction(player, board.getBank(), new Object[]{amount}, new Object[]{});
        if(transaction.canSettle())
            transaction.settle();
    }
}
