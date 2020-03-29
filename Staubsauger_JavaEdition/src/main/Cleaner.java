package main;

import visuals.Visualizer;
import world.Coordinate;
import world.Field;
import world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Cleaner {
    public static int UNTILCLEAN = 1;

    private Coordinate currentPosition;
    private int delay = 500;
    private Random rng = new Random();
    private World world;
    private int iterationsCounter;
    private Visualizer visualizer;

    public Cleaner(Coordinate startPosition, World pWorld, Visualizer pVisualizer) {
        currentPosition = startPosition;
        world = pWorld;
        iterationsCounter = 0;
        visualizer = pVisualizer;
    }

    // helper method
    private int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
        int random = start + rnd.nextInt(end - start + 1 - exclude.length);
        for (int ex : exclude) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }

    private boolean allClean() {
        for (Field[] slice : world.fields) {
            for (Field f : slice) {
                if (!f.isClean()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void randomMove(int runtimeParam) {
        // incrementing iterationsCounter
        iterationsCounter++;

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

        // updating visualizer
        visualizer.update(world);

        switch (runtimeParam) {
            case (0): {
                return;
            } case (1): {
                if (iterationsCounter >= world.fields.length) {
                    if (!allClean()) {
                        randomMove(UNTILCLEAN);
                    }
                } else {
                    randomMove(UNTILCLEAN);
                }
                break;
            }
            default:
                System.out.println("Error!");
        }
    }

    public void smartMove(int runtimeParam) {

    }

    private void move(Coordinate destiny) {
        // marking current position
        world.fields[currentPosition.x][currentPosition.y].setBackground(new Color(0xEADC6D));

        currentPosition = destiny;
    }

    public void setDelay(int d) {
        delay = d;
    }
}
