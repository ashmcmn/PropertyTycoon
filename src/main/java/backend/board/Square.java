package backend.board;

/**
 * The type Square.
 *
 * @author Ashley McManamon
 */
public class Square {
    private String name;

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
}
