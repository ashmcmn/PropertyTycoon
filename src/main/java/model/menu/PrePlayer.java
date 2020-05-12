package model.menu;

import model.players.Token;

public class PrePlayer {
    private Token token;
    private String name;
    private Boolean ai;

    public PrePlayer(Token token, String name, Boolean ai) {
        this.token = token;
        this.name = name;
        this.ai = ai;
    }

    public PrePlayer(String name, Boolean ai) {
        this.name = name;
        this.ai = ai;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAi() {
        return ai;
    }

    public void setAi(Boolean ai) {
        this.ai = ai;
    }
}
