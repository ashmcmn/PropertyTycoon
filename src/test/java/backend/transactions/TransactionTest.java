package backend.transactions;

import backend.board.Board;
import backend.board.PropertySquare;
import backend.board.Square;
import backend.party.Bank;
import backend.players.Player;
import backend.players.Token;
import backend.transactions.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    Transaction transaction;
    Player p;
    Player p2;

    @BeforeEach
    void setUp() {
        Board board = new Board(new Square[]{}, new Bank(50000), new ArrayList<>());
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
        PropertySquare prop = new PropertySquare("Prop", p, new int[]{1});
        p.addProperty(prop);

        transaction = new Transaction(p, p2, new Object[]{prop}, new Object[]{});

        assertTrue(transaction.canSettle());

        transaction.settle();

        assertEquals(List.of(prop), p2.getProperties());
    }

    @Test
    void settleMixed() {
        PropertySquare prop = new PropertySquare("Prop", p, new int[]{1});
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