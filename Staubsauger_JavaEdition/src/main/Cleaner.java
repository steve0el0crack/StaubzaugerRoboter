package main;

import world.Coordinate;
import world.World;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Cleaner {
    private Coordinate currentPosition;
    private int delay = 500;
    private Random rng = new Random();
    private World world;

    public Cleaner(Coordinate startPosition, World pWorld) {
        currentPosition = startPosition;
        world = pWorld;
    }

    // helper method
    public int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
        int random = start + rnd.nextInt(end - start + 1 - exclude.length);
        for (int ex : exclude) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }

    public void randomMove() {
        // clean field
        world.fields[currentPosition.x][currentPosition.y].clean();

        // calculating destiny field

        // checking borders
        int randomX = currentPosition.x + getRandomWithExclusion(rng, -1, 1, 0);
        int randomY = currentPosition.y + getRandomWithExclusion(rng, -1, 1, 0);
        if (currentPosition.x == world.width - 1) randomX = currentPosition.x - 1;
        if (currentPosition.x == 0) randomX = currentPosition.x + 1;
        if (currentPosition.y == world.height - 1) randomY = currentPosition.y - 1;
        if (currentPosition.y == 0) randomY = currentPosition.y + 1;

        Coordinate destiny = rng.nextBoolean() ? new Coordinate(randomX, currentPosition.y) : new Coordinate(currentPosition.x, randomY);
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // moving to destiny
        currentPosition = destiny;

        move(destiny);
    }

    public void setDelay(int d) {
        delay = d;
    }

    private void move(Coordinate destiny) {
        // marking current position
        world.fields[currentPosition.x][currentPosition.y].setBackground(new Color(0xEADC6D));

        currentPosition = destiny;
    }
}
