package model.players;

public enum Token {
    BOOT("boot.png"),
    SMARTPHONE("smartphone.png"),
    GOBLET("goblet.png"),
    HATSTAND("hatstand.png"),
    CAT("cat.png"),
    SPOON("spoon.png");

    private final String path;

    private Token(final String path) {
        this.path = path;
    }

    public String getPath() { return path; }
}
