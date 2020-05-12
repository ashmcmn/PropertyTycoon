package model.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Choice {

    List<Runnable> choices;

    public Choice() {
        choices = new ArrayList<>();
    }

    public void add(Runnable runnable) {
        choices.add(runnable);
    }

    public void decide() {
        Random rand = new Random();
        choices.get(rand.nextInt(choices.size())).run();
    }
}
