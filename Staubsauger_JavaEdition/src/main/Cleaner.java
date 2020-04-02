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
    // private fields
    private int delay = 500;
    private Random rng = new Random();
    private World world;
    private Visualizer visualizer;

    // current position of cleaner
    private Coordinate currentPosition;

    // constructor
    public Cleaner(Coordinate startPosition, World pWorld, Visualizer pVisualizer) {
        currentPosition = startPosition;
        world = pWorld;
        visualizer = pVisualizer;
    }

    // helper methods
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
    private Coordinate[] searchDist(int value) {
        ArrayList<Coordinate> coords = new ArrayList<>();

        for (Field[] slice : world.fields) {
            for (Field f : slice) {
                if (f.index == value) {
                    coords.add(f.coord);
                }
            }
        }

        // converting to array
        Coordinate[] ret = new Coordinate[coords.size()];
        for (int i = 0; i < coords.size(); i++) {
            ret[i] = coords.get(i);
        }

        return ret;
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

    // movement methods
    public void randomMovement() {
        // clean field
        cleanField(currentPosition);

        // calculating destiny field

        // checking borders
        int randomX = currentPosition.x + getRandomWithExclusion(rng, -1, 1, 0);
        int randomY = currentPosition.y + getRandomWithExclusion(rng, -1, 1, 0);
        if (currentPosition.x == world.width - 1) randomX = currentPosition.x - 1;
        if (currentPosition.x == 0) randomX = currentPosition.x + 1;
        if (currentPosition.y == world.height - 1) randomY = currentPosition.y - 1;
        if (currentPosition.y == 0) randomY = currentPosition.y + 1;

        Coordinate destiny = rng.nextBoolean() ? new Coordinate(randomX, currentPosition.y) : new Coordinate(currentPosition.x, randomY);

        // moving
        move(destiny);
        // updating visualizer
        visualizer.update(world);

        if (!allClean()) {
            randomMovement();
        }
    }
    public void smartMovement(int dist, Coordinate... targets) {
        //cleanField(currentPosition);
        for (Coordinate t : targets) {
            subCycle(t);
            //cleanField(t);
        }

        dist++;

        if (!allClean()) {
            visualizer.update(world);
            smartMovement(dist, searchDist(dist));
        }

        visualizer.update(world);
    }
    private void subCycle(Coordinate target) {
        int xDiff = target.x - currentPosition.x;
        int yDiff = target.y - currentPosition.y;

        for (int x = 0; x <= Math.abs(xDiff); x++) {
            for (int y = 0; y <= Math.abs(yDiff); y++) {
                Coordinate tmp = new Coordinate(currentPosition.x + x * Integer.signum(xDiff), currentPosition.y + y * Integer.signum(yDiff));
                move(tmp);
                //cleanField(tmp);
            }
        }
    }

    // other
    private void delay() {
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void move(Coordinate destiny) {
        Color previous = world.fields[currentPosition.x][currentPosition.y].getBackground();

        // marking current position
        world.fields[currentPosition.x][currentPosition.y].setBackground(new Color(0xEA6E55));
        delay();

        //world.fields[currentPosition.x][currentPosition.y].setBackground(previous);

        currentPosition = destiny;
    }
    private void cleanField(Coordinate pos) {
        world.fields[pos.x][pos.y].clean();
    }
    public void setDelay(int d) {
        delay = d;
    }
}
