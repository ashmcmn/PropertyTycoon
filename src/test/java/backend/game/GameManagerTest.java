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

    @BeforeEach
    void setUp() {
        board = new Board(new Square[]{
                new GoSquare("Go"),
                new PropertySquare("Prop1")
        }, new Bank(50000), new ArrayList<Player>());
        gameManager = new GameManager(board);
    }

    @Test
    void getBoard() {
    }

    @Test
    void setBoard() {
    }

    @Test
    void loadConfig() {
        gameManager.loadConfig("config.json");
        assertEquals("Go", board.getSquares()[0].getName());
        assertEquals("Crapper Street", board.getSquares()[1].getName());
        assertEquals(40, board.getSquares().length);
    }
}