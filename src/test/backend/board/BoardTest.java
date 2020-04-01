package test.backend.board;

import main.backend.board.Board;
import main.backend.board.GoSquare;
import main.backend.board.PropertySquare;
import main.backend.board.Square;
import main.backend.dice.Dice;
import main.backend.party.Bank;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board board;
    Square[] squares;
    Bank bank;

    @BeforeEach
    void setUp() {
        squares = new Square[]{
                new GoSquare("Go"),
                new PropertySquare("Prop1")
        };
        bank = new Bank(50000);
        board = new Board(squares, bank);
    }

    @Test
    void getSquares() {
        assertArrayEquals(squares, board.getSquares());
    }

    @Test
    void setSquares() {
        squares = new Square[]{
                new GoSquare("Go"),
                new PropertySquare("Prop2")
        };

        board.setSquares(squares);

        assertArrayEquals(squares, board.getSquares());
    }

    @Test
    void getDice() {
        assertSame(Dice.class, board.getDice().getClass());
    }

    @Test
    void setDice() {
        Dice newDice = new Dice();
        board.setDice(newDice);
        assertEquals(newDice, board.getDice());
    }

    @Test
    void getBank() {
        assertEquals(bank, board.getBank());
    }

    @Test
    void setBank() {
        Bank newBank = new Bank(20000);
        board.setBank(newBank);
        assertEquals(newBank, board.getBank());
    }
}