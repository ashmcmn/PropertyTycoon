package backend.board;

import backend.players.Player;

/**
 * The type Go square.
 *
 * @author Ashley McManamon
 */
public class GoSquare extends Square {
    /**
     * Instantiates a new Square.
     *
     * @param name the name of the square
     */
    public GoSquare(String name) {
        super(name);
    }

    /**
     * Empty function
     *
     * @param player the player
     * @param board  the board
     */
    @Override
    public void doAction(Player player, Board board) {

    }
}
