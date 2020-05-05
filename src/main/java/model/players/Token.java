package model.players;

public enum Token {
    BOOT("boot.jpg"),
    SMARTPHONE("smartphone.jpg"),
    GOBLET("goblet.jpg"),
    HATSTAND("hatstand.jpg"),
    CAT("cat.jpg"),
    SPOON("spoon.jpg");

    private final String path;

    private Token(final String path) {
        this.path = path;
    }

    public String getPath() { return path; }
}
