package main.backend.players;

/**
 * The type Player.
 *
 * @author Ashley McManamon
 */
public class Player {
    private Token token;
    private int cash;

    /**
     * Instantiates a new Player.
     *
     * @param token the token to represent the player's position on the board
     * @param cash  the amount of cash currently held by the player
     */
    public Player(Token token, int cash) {
        this.token = token;
        this.cash = cash;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    /**
     * Sets token.
     *
     * @param token The token to give to the player
     */
    public void setToken(Token token) {
        this.token = token;
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
     * @param cash The amount to set the player's cash to
     */
    public void setCash(int cash) {
        this.cash = cash;
    }
}
