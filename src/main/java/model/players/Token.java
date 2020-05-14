package model.players;

/**
 * The enum Token.
 */
public enum Token {
    /**
     * Boot token.
     */
    BOOT("boot.png"),
    /**
     * Smartphone token.
     */
    SMARTPHONE("smartphone.png"),
    /**
     * Goblet token.
     */
    GOBLET("goblet.png"),
    /**
     * Hatstand token.
     */
    HATSTAND("hatstand.png"),
    /**
     * Cat token.
     */
    CAT("cat.png"),
    /**
     * Spoon token.
     */
    SPOON("spoon.png");

    private final String path;

    private Token(final String path) {
        this.path = path;
    }

    /**
     * Gets the file path for the token's image.
     *
     * @return the path
     */
    public String getPath() { return path; }
}
