package model.menu;

import model.players.Token;

/**
 * The a hypothetical player, used in the main menu
 */
public class PrePlayer {
    private Token token;
    private String name;
    private Boolean ai;

    /**
     * Instantiates a new 'pre' player.
     *
     * @param token the token
     * @param name  the name
     * @param ai    whether or not this player is ai controller
     */
    public PrePlayer(Token token, String name, Boolean ai) {
        this.token = token;
        this.name = name;
        this.ai = ai;
    }

    /**
     * Instantiates a new 'pre' player.
     *
     * @param name the name
     * @param ai   whether or not this player is ai controller
     */
    public PrePlayer(String name, Boolean ai) {
        this.name = name;
        this.ai = ai;
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
     * @param token the token
     */
    public void setToken(Token token) {
        this.token = token;
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

    /**
     * Gets ai.
     *
     * @return the ai
     */
    public Boolean getAi() {
        return ai;
    }

    /**
     * Sets ai.
     *
     * @param ai the ai
     */
    public void setAi(Boolean ai) {
        this.ai = ai;
    }
}
