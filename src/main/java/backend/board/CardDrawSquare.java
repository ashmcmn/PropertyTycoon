package backend.board;

import backend.players.Player;

/**
 * The type Card draw square.
 *
 * @author Ashley McManamon
 */
public class CardDrawSquare extends Square {
    /**
     * Instantiates a new Square.
     *
     * @param name the name of the square
     */
    public CardDrawSquare(String name) {
        super(name);
    }

    /**
     * Draws a card for the player
     *
     * @param player the player
     * @param board  the board
     */
    @Override
    public void doAction(Player player, Board board) {
        //TODO
    }
}
