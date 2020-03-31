package main.backend.players;

public class Player {
    private Token token;
    private int cash;

    public Player(Token token, int cash) {
        this.token = token;
        this.cash = cash;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }
}
