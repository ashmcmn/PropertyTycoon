package model.party;

import model.board.Group;
import model.board.PropertySquare;

import java.util.List;

/**
 * The type Free parking.
 *
 * @author Ashley McManamon
 */
public class FreeParking implements Party{
    private int cash;

    /**
     * Instantiates a new free parking.
     *
     * @param cash the cash
     */
    public FreeParking(int cash) {
        this.cash = cash;
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
     * @param cash the amount to set the free parking's cash to
     */
    public void setCash(int cash) {
        this.cash = cash;
    }

    public String getName() { return "free parking"; }

    public List<PropertySquare> getProperties() {
        return null;
    }

    public void addProperty(PropertySquare property) {
    }

    public void removeProperty(PropertySquare property) {
    }

    @Override
    public boolean ownsGroup(Group group) {
        return false;
    }
}
