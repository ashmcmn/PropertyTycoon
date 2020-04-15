package backend.cards;

import backend.board.Board;
import backend.players.Player;

/**
 * The interface Action.
 */
public interface Action {
    /**
     * Action.
     *
     * @param player the player
     * @param board the board
     */
    default void action(Player player, Board board){

    }
}
