package backend.board;

import backend.party.Party;
import backend.players.Player;
import backend.transactions.Transaction;

/**
 * The type Property square.
 *
 * @author Ashley McManamon
 */
public class PropertySquare extends Square {
    private Party owner;

    /**
     * Instantiates a new Square.
     *
     * @param name  the name of the square
     * @param owner the owner
     */
    public PropertySquare(String name, Party owner) {
        super(name);
        this.owner = owner;
    }

    /**
     * Gets owner.
     *
     * @return the owner
     */
    public Party getOwner() {
        return owner;
    }

    /**
     * Sets owner.
     *
     * @param owner the owner
     */
    public void setOwner(Party owner) {
        this.owner = owner;
    }

    /**
     * Function for buying, paying rent or doing nothing
     *
     * @param player the player
     * @param board  the board
     */
    @Override
    public void doAction(Player player, Board board) {
        if(owner == board.getBank()){
            Transaction transaction = new Transaction(player, board.getBank(), new Object[]{}, new Object[]{this});
            if(transaction.canSettle()){
                transaction.settle();
            }
        }
        else{
            //TODO: pay rent
        }
    }
}
