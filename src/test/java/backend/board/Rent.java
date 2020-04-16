package backend.board;

import backend.board.Board;
import backend.board.GoSquare;
import backend.board.PropertySquare;
import backend.board.Square;
import backend.dice.Dice;
import backend.game.GameManager;
import backend.party.Bank;
import backend.players.Player;
import backend.players.Token;
import backend.transactions.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Rent {
    GameManager gameManager;

    @BeforeEach
    void setUp() {
        gameManager = new GameManager(new String[]{"P1", "P2"});
    }

    @Test
    void rent() {
        gameManager.setCurrentPlayer(gameManager.getBoard().getPlayer(0));

        Transaction transaction = new Transaction(gameManager.getBoard().getBank(), gameManager.getBoard().getPlayer(1), new Object[]{gameManager.getBoard().getSquares()[1]}, new Object[]{});
        transaction.settle();

        Square square = gameManager.getCurrentPlayer().move(1, false);
        square.doAction(gameManager.getCurrentPlayer(), gameManager.getBoard());

        assertEquals(1498, gameManager.getCurrentPlayer().getCash());
        assertEquals(1502, gameManager.getBoard().getPlayer(1).getCash());
    }
}