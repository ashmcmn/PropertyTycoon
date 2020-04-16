package backend.board;

import backend.players.Player;

/**
 * The type Jail square.
 *
 * @author Ashley McManamon
 */
public class JailSquare extends Square {
    /**
     * Instantiates a new Square.
     *
     * @param name the name of the square
     */
    public JailSquare(String name) {
        super(name);
    }

    /**
     * Empty functions
     *
     * @param player the player
     * @param board  the board
     */
    @Override
    public void doAction(Player player, Board board) {

    }
}
