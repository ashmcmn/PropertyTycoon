package backend.board;

import backend.board.Board;
import backend.board.GoSquare;
import backend.board.PropertySquare;
import backend.board.Square;
import backend.dice.Dice;
import backend.party.Bank;
import backend.players.Player;
import backend.players.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        board = new Board(squares, bank, new ArrayList<Player>());
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