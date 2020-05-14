package model.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * A choice to be made by an AI player.
 */
public class Choice {

    /**
     * The Choices.
     */
    List<Runnable> choices;

    /**
     * Instantiates a new Choice.
     */
    public Choice() {
        choices = new ArrayList<>();
    }

    /**
     * Adds a new options
     *
     * @param runnable the runnable
     */
    public void add(Runnable runnable) {
        choices.add(runnable);
    }

    /**
     * Decide randomly which option to choose
     */
    public void decide() {
        Random rand = new Random();

        choices.get(rand.nextInt(choices.size())).run();
    }
}
