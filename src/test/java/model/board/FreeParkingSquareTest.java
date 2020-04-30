package model.board;

import controller.GameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FreeParkingSquareTest {
    GameManager gameManager;

    @BeforeEach
    void setUp() {
        gameManager = new GameManager(new String[]{"P1"});
        gameManager.loadConfig("config.json");
        gameManager.setCurrentPlayer(gameManager.getBoard().getPlayer(0));
    }

    @Test
    void doAction() {
        gameManager.getBoard().getFreeParking().setCash(100);

        Square square = gameManager.getBoard().getSquares()[20];
        square.doAction(gameManager.getCurrentPlayer(), gameManager.getBoard());

        assertEquals(1600, gameManager.getCurrentPlayer().getCash());
    }
}