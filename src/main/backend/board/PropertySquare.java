package main.backend.board;

import main.backend.party.Party;

/**
 * The type Property square.
 *
 * @author Ashley McManamon
 */
public class PropertySquare extends Square {
    private Party owner;

    /**
     * Instantiates a new Square.
     *
     * @param name  the name of the square
     * @param owner the owner
     */
    public PropertySquare(String name, Party owner) {
        super(name);
        this.owner = owner;
    }

    /**
     * Gets owner.
     *
     * @return the owner
     */
    public Party getOwner() {
        return owner;
    }

    /**
     * Sets owner.
     *
     * @param owner the owner
     */
    public void setOwner(Party owner) {
        this.owner = owner;
    }
}
