package backend.party;

import backend.board.PropertySquare;

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
}
