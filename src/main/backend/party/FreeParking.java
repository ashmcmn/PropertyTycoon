package main.backend.party;

/**
 * The type Free parking.
 *
 * @author Ashley McManamon
 */
public class FreeParking {
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
}
