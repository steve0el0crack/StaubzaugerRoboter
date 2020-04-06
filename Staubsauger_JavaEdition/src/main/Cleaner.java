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
        /*if (currentPosition.x == world.width - 1) randomX = currentPosition.x - 1;
        if (currentPosition.x == 0) randomX = currentPosition.x + 1;
        if (currentPosition.y == world.height - 1) randomY = currentPosition.y - 1;
        if (currentPosition.y == 0) randomY = currentPosition.y + 1;*/

        int x = currentPosition.x;
        int y = currentPosition.y;

        if (world.fields[x + 1][y] == null || world.fields[x + 1][y].blocked)
            randomX = x - 1;
        if (world.fields[x - 1][y] == null || world.fields[x - 1][y].blocked)
            randomX = x + 1;
        if (world.fields[x][y + 1] == null || world.fields[x][y + 1].blocked)
            randomY = y - 1;
        if (world.fields[x][y - 1] == null || world.fields[x][y - 1].blocked)
            randomY = y + 1;

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
        for (Coordinate t : targets) {
            subCycle(t);
        }

        dist++;

        if (!allClean()) {
            //visualizer.update(world);
            smartMovement(dist, searchDist(dist));
        }

        //visualizer.update(world);
    }
    public void subCycle(Coordinate target) {
        Coordinate pos = currentPosition;
        int xDiff = target.x - pos.x;
        int yDiff = target.y - pos.y;

        for (int x = 1; x <= Math.abs(xDiff); x++) {
            Coordinate tmp = new Coordinate(pos.x + x * Integer.signum(xDiff), pos.y);
            move(tmp);
            cleanField(tmp);
        }

        pos.x = currentPosition.x;

        for (int y = 1; y <= Math.abs(yDiff); y++) {
            Coordinate tmp = new Coordinate(pos.x, pos.y + y * Integer.signum(yDiff));
            move(tmp);
            cleanField(tmp);
        }

        pos.y = currentPosition.y;
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
        // saving color of current field
        Color previous = world.fields[currentPosition.x][currentPosition.y].getBackground();

        // marking current position
        world.fields[currentPosition.x][currentPosition.y].setBackground(new Color(0x0020FF));
        if (delay > 0) delay();

        // rewriting old color to Field
        world.fields[currentPosition.x][currentPosition.y].setBackground(previous);

        currentPosition = destiny;

        visualizer.update(world);
    }
    private void cleanField(Coordinate pos) {
        world.fields[pos.x][pos.y].clean();
    }
    public void setDelay(int d) {
        delay = d;
    }
}
