package backend.dice;

import backend.dice.Dice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {
    Dice dice;

    @BeforeEach
    void setUp() {
        dice = new Dice();
    }

    @Test
    void roll() {
        for (int i = 0; i < 1000; i++) {
            dice.roll();
            int[] result = dice.getResult();
            assertTrue(Arrays.asList(1, 2, 3, 4, 5, 6).contains(result[0]));
            assertTrue(Arrays.asList(1, 2, 3, 4, 5, 6).contains(result[1]));
        }
    }
}