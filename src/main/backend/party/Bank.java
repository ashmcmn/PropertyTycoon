package main.backend.party;

/**
 * The type Bank.
 *
 * @author Ashley McManamon
 */
public class Bank {
    private int cash;

    /**
     * Instantiates a new Bank.
     *
     * @param cash the cash
     */
    public Bank(int cash) {
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
     * @param cash the amount to set the bank's cash to
     */
    public void setCash(int cash) {
        this.cash = cash;
    }
}
