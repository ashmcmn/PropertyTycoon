package model.dice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * The type Dice.
 *
 * @author Ashley McManamon
 */
public class Dice {
    private int[] result;
    private int doubles;
    private boolean wasDouble;
    private static final Logger LOG = LogManager.getLogger(Dice.class);

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
     * Gets the number doubles thrown in current turn.
     *
     * @return the doubles
     */
    public int getDoubles() {
        return doubles;
    }

    /**
     * Reset number of doubles.
     */
    public void resetDoubles() {
        doubles = 0;
    }

    /**
     * Checks if last throw was a double.
     *
     * @return the boolean
     */
    public boolean wasDouble() {
        return wasDouble;
    }

    /**
     * Roll the dice to generate  a new result.
     */
    public void roll() {
        Random random = new Random();
        result = new int[]{1 + random.nextInt(6), 1 + random.nextInt(6)};

        LOG.debug("The dice show " + result[0] + " and " + result[1]);

        if(result[0] == result[1]){
            doubles++;
            wasDouble = true;
        }
        else{
            wasDouble = false;
            doubles = 0;
        }
    }
}
