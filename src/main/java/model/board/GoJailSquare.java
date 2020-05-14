package model.board;

import model.players.Player;

import java.lang.reflect.Array;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The type Go jail square.
 *
 * @author Ashley McManamon
 */
public class GoJailSquare extends Square {
    /**
     * Instantiates a new Square.
     *
     * @param name the name of the square
     */
    public GoJailSquare(String name) {
        super(name);
    }

    /**
     * Sends the player to jail
     *
     * @param player the player
     * @param board  the board
     */
    @Override
    public void doAction(Player player, Board board) {
        player.sendToJail();
    }
}
