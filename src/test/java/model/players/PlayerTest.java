package model.players;

import model.board.*;
import controller.GameManager;
import model.transactions.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    GameManager gameManager;

    @BeforeEach
    void setUp() {
        gameManager = new GameManager(new String[]{"P1"});
        gameManager.setCurrentPlayer(gameManager.getBoard().getPlayer(0));
    }

    @Test
    void containedMove() {
        gameManager.getCurrentPlayer().move(2, true);
        assertEquals(2, gameManager.getCurrentPlayer().getPosition());
    }

    @Test
    void overflowMove() {
        gameManager.getCurrentPlayer().move(gameManager.getBoard().getSquares().length+1, true);
        assertEquals(1, gameManager.getCurrentPlayer().getPosition());
    }

    @Test
    void collectsSalary() {
        gameManager.getCurrentPlayer().move(gameManager.getBoard().getSquares().length-5, true);
        gameManager.getCurrentPlayer().move(10, true);
        assertEquals(1700, gameManager.getCurrentPlayer().getCash());
    }

    @Test
    void dontOwnImprove() {
        assertFalse(gameManager.getCurrentPlayer().improve((PropertySquare) gameManager.getBoard().getSquares()[1]));
    }

    @Test
    void unevenImprove() {
        List<PropertySquare> props = Stream.of(gameManager.getBoard().getSquares())
                .filter(PropertySquare.class::isInstance)
                .map(PropertySquare.class::cast)
                .filter(s -> s.getGroup() == Group.BLUE)
                .collect(Collectors.toList());

        for(PropertySquare prop : props) {
            Transaction transaction = new Transaction(gameManager.getBoard().getBank(), gameManager.getCurrentPlayer(), new Object[]{prop}, new Object[]{});
            transaction.settle();
        }

        gameManager.getCurrentPlayer().improve(props.get(0));
        assertFalse(gameManager.getCurrentPlayer().improve(props.get(0)));
    }

    @Test
    void unevenDevalue() {
        List<PropertySquare> props = Stream.of(gameManager.getBoard().getSquares())
                .filter(PropertySquare.class::isInstance)
                .map(PropertySquare.class::cast)
                .filter(s -> s.getGroup() == Group.BLUE)
                .collect(Collectors.toList());

        for(PropertySquare prop : props) {
            Transaction transaction = new Transaction(gameManager.getBoard().getBank(), gameManager.getCurrentPlayer(), new Object[]{prop}, new Object[]{});
            transaction.settle();
        }

        gameManager.getCurrentPlayer().improve(props.get(0));
        gameManager.getCurrentPlayer().improve(props.get(1));
        gameManager.getCurrentPlayer().improve(props.get(2));

        gameManager.getCurrentPlayer().improve(props.get(1));
        gameManager.getCurrentPlayer().improve(props.get(2));

        assertFalse(gameManager.getCurrentPlayer().devalue(props.get(0)));
    }
}