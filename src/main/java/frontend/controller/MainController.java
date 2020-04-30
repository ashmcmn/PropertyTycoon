package frontend.controller;

import backend.game.GameManager;
import javafx.stage.Stage;

public class MainController {
    private GameManager gameManager;

    public MainController(Stage stage) {
        gameManager = new GameManager(new String[]{"P1","P2"});
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}