package backend.dice;

import java.util.Random;

/**
 * The type Dice.
 *
 * @author Ashley McManamon
 */
public class Dice {
    private int[] result;

    /**
     * Instantiates a new Dice.
     */
    public Dice() {
    }

    /**
     * Get stored result.
     *
     * @return the result
     */
    public int[] getResult() {
        return result;
    }

    /**
     * Roll the dice to generate  a new result.
     */
    public void roll() {
        Random random = new Random();
        result = new int[]{1 + random.nextInt(6), 1 + random.nextInt(6)};
    }
}
