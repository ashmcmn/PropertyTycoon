package backend.game;

import backend.board.Board;
import backend.board.GoSquare;
import backend.board.PropertySquare;
import backend.board.Square;
import backend.party.Bank;
import backend.players.Player;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {
    GameManager gameManager;
    Board board;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank(50000);
        board = new Board(new Square[]{
                new GoSquare("Go"),
                new PropertySquare("Prop1", bank, new int[]{1})
        }, new Bank(50000), new ArrayList<Player>());
        gameManager = new GameManager(new String[]{"Jim", "Steve"});
    }

    @Test
    void getBoard() {
        assertEquals(board, gameManager.getBoard());
    }

    @Test
    void setBoard() {
        Board newBoard = new Board(new Square[]{
                new GoSquare("Go"),
                new PropertySquare("Prop2", bank, new int[]{1})
        }, new Bank(50000), new ArrayList<Player>());
        gameManager.setBoard(newBoard);
        assertEquals(newBoard, gameManager.getBoard());
    }

    @Test
    void loadConfig() {
        gameManager.loadConfig("config.json");
        assertEquals("Go", board.getSquares()[0].getName());
        assertEquals("Crapper Street", board.getSquares()[1].getName());
        assertEquals(40, board.getSquares().length);
    }
}