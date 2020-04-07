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
    private int moveCounter = 0;

    // current position of cleaner
    private Coordinate currentPosition;
    private Coordinate currentRotation;

    // constructor
    public Cleaner(Coordinate startPosition, World pWorld, Visualizer pVisualizer, Coordinate startRotation) {
        currentPosition = startPosition;
        world = pWorld;
        visualizer = pVisualizer;
        currentRotation = startRotation;
    }

    // helper methods
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
    public void testMovement(Field target) {
        move(target);
    }
    public void smartMovement(int dist, Coordinate... targets) {
        cleanField();
        if (!allClean()) {
            for (Coordinate t : targets) {
                subCycle(t);
            }
            dist++;
            smartMovement(dist, searchDist(dist));
        }
    }
    public void subCycle(Coordinate target) {
        while (!(currentPosition.x == target.x && currentPosition.y == target.y) && !allClean()) {
            turnTowards(target);
            move(world.fields[currentPosition.x][currentPosition.y]);
            cleanField();
        }
    }
    private void move(Field target) {
        int targetIndex = target.index;

        while (!(currentPosition.x == target.coord.x && currentPosition.y == target.coord.y)) {
            // saving color of current field
            Color previous = world.fields[currentPosition.x][currentPosition.y].getBackground();

            // marking current position
            world.fields[currentPosition.x][currentPosition.y].setBackground(new Color(0x0020FF));
            delay();

            // actual movement
            Field currentField = world.fields[currentPosition.x][currentPosition.y];
            Coordinate[] potTargets = searchDist(currentField.index + 1);
            // choosing next subTarget if multiple potential sub targets are available
            Coordinate subTarget = new Coordinate(0, 0);
            if (potTargets.length > 1) {
                int distanceToMainTarget = Math.abs(target.coord.x - currentPosition.x) + Math.abs(target.coord.y - currentPosition.y);
                for (Coordinate potT : potTargets) {
                    if (Math.abs(target.coord.x - potT.x) + Math.abs(target.coord.y - potT.y) < distanceToMainTarget) {
                        distanceToMainTarget = Math.abs(target.coord.x - potT.x) + Math.abs(target.coord.y - potT.y);
                        subTarget = potT;
                    }
                }
            } else {
                subTarget = potTargets[0];
            }

            currentPosition = subTarget;

            // rewriting old color to Field
            world.fields[currentPosition.x][currentPosition.y].setBackground(previous);

            // incrementing moveCounter and updating visuals
            moveCounter++;
            visualizer.update(world);
        }
    }
    private void turnTowards(Coordinate target) {
        int deltaX = target.x - currentPosition.x;
        int deltaY = target.y - currentPosition.y;
        if (Math.abs(deltaX) >= Math.abs(deltaY)) {
            currentRotation = new Coordinate(deltaX, 0);
        } else {
            currentRotation = new Coordinate(0, deltaY);
        }
        currentRotation.normalize();
    }

    // other
    private boolean checkField() {
        Coordinate facedField = new Coordinate(currentPosition.x + currentRotation.x, currentPosition.y + currentRotation.y);
        if ((facedField.x >= 0 && facedField.x <= world.width) && (facedField.y >= 0 && facedField.y <= world.height)) {
            return world.fields[facedField.x][facedField.y].blocked;
        }
        return false;
    }
    private void cleanField() {
        world.fields[currentPosition.x][currentPosition.y].clean();
    }
    public void setDelay(int d) {
        delay = d == 0 ? 1 : d;
    }
    private void delay() {
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // getter
    public Coordinate getCurrentPosition() {
        return currentPosition;
    }
    public int getMoveCounter() {
        return moveCounter;
    }
    public Coordinate getCurrentRotation() {
        return currentRotation;
    }
}
