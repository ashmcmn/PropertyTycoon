package test.backend.transactions;

import main.backend.board.Board;
import main.backend.board.Square;
import main.backend.party.Bank;
import main.backend.players.Player;
import main.backend.players.Token;
import main.backend.transactions.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    Transaction transaction;
    Player p;
    Player p2;

    @BeforeEach
    void setUp() {
        Board board = new Board(new Square[]{}, new Bank(50000));
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