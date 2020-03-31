package main.backend.board;

/**
 * The type Board.
 *
 * @author Ashley McManamon
 */
public class Board {
    private Square[] squares;

    /**
     * Instantiates a new Board.
     *
     * @param squares the squares the board contains
     */
    public Board(Square[] squares) {
        this.squares = squares;
    }

    /**
     * Gets the squares.
     *
     * @return the squares
     */
    public Square[] getSquares() {
        return squares;
    }

    /**
     * Sets squares.
     *
     * @param squares the squares
     */
    public void setSquares(Square[] squares) {
        this.squares = squares;
    }
}
