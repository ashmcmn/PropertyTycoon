package model.transactions;

import model.board.Board;
import model.board.Group;
import model.board.PropertySquare;
import model.board.Square;
import model.party.Bank;
import model.players.Player;
import model.players.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    Board board;
    Transaction transaction;
    Player p;
    Player p2;

    @BeforeEach
    void setUp() {
        board = new Board(new Square[]{}, new Bank(50000), new ArrayList<>());
        p = new Player("P1", Token.BOOT, 1500);
        p2 = new Player("P2", Token.CAT, 1500);
    }

    @Test
    void settleCash() {
        transaction = new Transaction(p, p2, new Object[]{100}, new Object[]{});

        assertTrue(transaction.canSettle());

        transaction.settle();

        assertEquals(1400, p.getCash());
        assertEquals(1600, p2.getCash());
    }

    @Test
    void settleProperty() {
        PropertySquare prop = new PropertySquare("Prop", p, new int[]{1}, Group.GREEN, board, 100);
        p.addProperty(prop);

        transaction = new Transaction(p, p2, new Object[]{prop}, new Object[]{});

        assertTrue(transaction.canSettle());

        transaction.settle();

        assertEquals(List.of(prop), p2.getProperties());
    }

    @Test
    void settleMixed() {
        PropertySquare prop = new PropertySquare("Prop", p, new int[]{1}, Group.GREEN, board, 100);
        p.addProperty(prop);

        transaction = new Transaction(p, p2, new Object[]{prop}, new Object[]{200});

        assertTrue(transaction.canSettle());

        transaction.settle();

        assertEquals(List.of(prop), p2.getProperties());
        assertEquals(1300, p2.getCash());
    }

    @Test
    void cantAffordSettle() {
        p.setCash(80);
        transaction = new Transaction(p, p2, new Object[]{100}, new Object[]{});
        assertFalse(transaction.canSettle());
    }
}