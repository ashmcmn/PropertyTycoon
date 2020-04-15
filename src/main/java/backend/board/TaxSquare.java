package backend.board;

import backend.players.Player;

/**
 * The type Tax square.
 *
 * @author Ashley McManamon
 */
public class TaxSquare extends Square {
    /**
     * Instantiates a new Square.
     *
     * @param name the name of the square
     */
    public TaxSquare(String name) {
        super(name);
    }

    /**
     * Player pays tax to the bank
     *
     * @param player the player
     * @param board  the board
     */
    @Override
    public void doAction(Player player, Board board) {
        //TODO
    }
}
