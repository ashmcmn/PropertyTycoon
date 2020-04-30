package model.board;

import model.players.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Square.
 *
 * @author Ashley McManamon
 */
public abstract class Square {
    private String name;
    private int position;
    protected Board board;
    protected static final Logger LOG = LogManager.getLogger(Square.class);

    /**
     * Instantiates a new Square.
     *
     * @param name the name of the square
     */
    public Square(String name) {
        this.name = name;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the position of this square on the board
     *
     * @return the position
     */
    public int getPosition() { return position; }

    /**
     * Abstract function for completing actions
     *
     * @param player the player
     * @param board  the board
     */
    public abstract void doAction(Player player, Board board);

    /**
     * Sets position of the square.
     *
     * @param position the position
     */
    public void setPosition(int position) {
        this.position = position;
    }
}
