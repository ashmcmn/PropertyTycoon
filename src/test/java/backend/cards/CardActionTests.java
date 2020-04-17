package backend.cards;

import backend.game.GameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CardActionTests {
    GameManager gameManager;

    @BeforeEach
    void setUp() {
        gameManager = new GameManager(new String[]{"P1", "P2"});
        gameManager.setCurrentPlayer(gameManager.getBoard().getPlayer(0));
        gameManager.loadConfig("config.json");
    }

    @Test
    void actionTest() {
        for(int i = 0; i < 12; i ++){
            gameManager.getBoard().getSquares()[2].doAction(gameManager.getCurrentPlayer(), gameManager.getBoard());
        }
    }
}