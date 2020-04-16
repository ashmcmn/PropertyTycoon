package backend.board;

import backend.players.Player;

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
        //TODO
    }
}
