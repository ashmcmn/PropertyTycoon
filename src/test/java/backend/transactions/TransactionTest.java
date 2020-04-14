package backend.transactions;

import backend.board.Board;
import backend.board.Square;
import backend.party.Bank;
import backend.players.Player;
import backend.players.Token;
import backend.transactions.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionTest {
    Transaction transaction;
    Player p;
    Player p2;

    @BeforeEach
    void setUp() {
        Board board = new Board(new Square[]{}, new Bank(50000), new ArrayList<>());
        p = new Player(Token.BOOT, 1500, board);
        p2 = new Player(Token.CAT, 1500, board);
        transaction = new Transaction(p, p2, new Object[]{100}, new Object[]{});
    }

    @Test
    void settleCash() {
        transaction.settle();
        assertEquals(1400, p.getCash());
        assertEquals(1600, p2.getCash());
    }
}