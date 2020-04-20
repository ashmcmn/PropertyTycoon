package backend.party;

import backend.board.Group;
import backend.board.PropertySquare;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Bank.
 *
 * @author Ashley McManamon
 */
public class Bank implements Party{
    private int cash;
    private List<PropertySquare> properties;

    /**
     * Instantiates a new Bank.
     *
     * @param cash the cash
     */
    public Bank(int cash) {
        this.cash = cash;
        this.properties = new ArrayList<>();
    }

    /**
     * Gets cash.
     *
     * @return the cash
     */
    public int getCash() {
        return cash;
    }

    /**
     * Sets cash.
     *
     * @param cash the amount to set the bank's cash to
     */
    public void setCash(int cash) {
        this.cash = cash;
    }

    public String getName() { return "the bank"; }

    /**
     * Gets properties owned by the player
     *
     * @return the properties
     */
    public List<PropertySquare> getProperties() {
        return properties;
    }

    /**
     * Add a property to the player's ownership
     *
     * @param property the property
     */
    public void addProperty(PropertySquare property) {
        this.properties.add(property);
    }

    /**
     * Remove a property from the player's ownership
     *
     * @param property the property
     */
    public void removeProperty(PropertySquare property) {
        this.properties.remove(property);
    }

    @Override
    public boolean ownsGroup(Group group) {
        return false;
    }
}
