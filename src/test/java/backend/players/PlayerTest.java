package backend.players;

import backend.board.*;
import backend.party.Bank;
import backend.players.Player;
import backend.players.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player;
    Board board;

    @BeforeEach
    void setUp() {
        board = new Board(new Square[]{}, new Bank(50000), new ArrayList<>());
        player = new Player(Token.BOOT, 1500, board);
    }

    @Test
    void getToken() {
        assertEquals(Token.BOOT, player.getToken());
    }

    @Test
    void setToken() {
        player.setToken(Token.CAT);
        assertEquals(Token.CAT, player.getToken());
    }

    @Test
    void getCash() {
        assertEquals(1500, player.getCash());
    }

    @Test
    void setCash() {
        player.setCash(2000);
        assertEquals(2000, player.getCash());
    }

    @Test
    void getPosition() {
        assertEquals(0, player.getPosition());
    }

    @Test
    void setPosition() {
        player.setPosition(5);
        assertEquals(5, player.getPosition());
    }

    @Test
    void getBoard() {
        assertEquals(board, player.getBoard());
    }

    @Test
    void setBoard() {
        Board newBoard = new Board(new Square[]{}, new Bank(50000), new ArrayList<>());
        player.setBoard(newBoard);
        assertEquals(newBoard, player.getBoard());
    }

    @Test
    void containedMove() {
        board.setSquares(new Square[]{new GoSquare("Go"), new PropertySquare("Prop", board.getBank()), new TaxSquare("Tax")});
        player.move(2, true);
        assertEquals(2, player.getPosition());
    }

    @Test
    void overflowMove() {
        board.setSquares(new Square[]{new GoSquare("Go"), new PropertySquare("Prop", board.getBank()), new TaxSquare("Tax")});
        player.move(4, true);
        assertEquals(1, player.getPosition());
    }

    @Test
    void naturalMove() {
        board.setSquares(new Square[]{new GoSquare("Go"), new PropertySquare("Prop", board.getBank()), new TaxSquare("Tax")});
        for (int i = 0; i < 1000; i++) {
            board.getDice().roll();
            player.move(IntStream.of(board.getDice().getResult()).sum(), true);
            assertTrue(player.getPosition() >= 0 && player.getPosition() < board.getSquares().length);
        }
    }

    @Test
    void collectsSalary() {
        board.setSquares(new Square[]{new GoSquare("Go"), new PropertySquare("Prop", board.getBank()), new TaxSquare("Tax")});
        player.setPosition(1);
        player.move(2, true);
        assertEquals(1700, player.getCash());
    }

    @Test
    void getProperties() {
        List<PropertySquare> props = new ArrayList<>();
        assertEquals(props, player.getProperties());
    }

    @Test
    void addProperty() {
        PropertySquare prop = new PropertySquare("Prop", board.getBank());
        List<PropertySquare> props = new ArrayList<>();
        props.add(prop);
        player.addProperty(prop);
        assertEquals(props, player.getProperties());
    }

    @Test
    void removeProperty() {
        PropertySquare prop = new PropertySquare("Prop", board.getBank());
        List<PropertySquare> props = new ArrayList<>();
        props.add(prop);
        player.addProperty(prop);
        assertEquals(props, player.getProperties());
        player.removeProperty(prop);
        assertEquals(new ArrayList<>(), player.getProperties());
    }
}