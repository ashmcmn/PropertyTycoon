package backend.board;

import backend.board.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SquareTest {
    Square square;

    @BeforeEach
    void setUp() {
        square = new Square("Square1");
    }

    @Test
    void getName() {
        assertEquals("Square1", square.getName());
    }

    @Test
    void setName() {
        square.setName("Square2");

        assertEquals("Square2", square.getName());
    }
}