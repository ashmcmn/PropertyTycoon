package model.party;

import model.board.Group;
import model.board.PropertySquare;

import java.util.List;

/**
 * The interface Party.
 *
 * @author Ashley McManamon
 */
public interface Party {
    /**
     * Gets cash.
     *
     * @return the cash
     */
    int getCash();

    /**
     * Sets cash.
     *
     * @param cash the cash
     */
    void setCash(int cash);

    String getName();

    /**
     * Gets properties.
     *
     * @return the properties
     */
    List<PropertySquare> getProperties();

    /**
     * Add property.
     *
     * @param property the property
     */
    void addProperty(PropertySquare property);


    /**
     * Remove property.
     *
     * @param property the property
     */
    void removeProperty(PropertySquare property);

    /**
     * Checks if a player owns a full group
     *
     * @param group the group
     * @return the boolean
     */
    boolean ownsGroup(Group group);
}
