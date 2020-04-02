package main;

import visuals.Visualizer;
import world.Coordinate;
import world.Field;
import world.World;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.CheckedOutputStream;

public class Main {
    // private fields
    private static World world;
    private static Random rng = new Random();
    private static Visualizer visualizer;

    // actual cleaning robot class
    private static Cleaner cleaner;

    // main process
    public static void main(String[] args) {
        // max size for x and y is 10
        world = new World(10, 10);
        world.setOrigin(setOrigin());

        numbering(searchByDistance(0), 1);
        visualizer = new Visualizer(world);

        cleaner = new Cleaner(world.origin, world, visualizer);
        cleaner.setDelay(400);
        //cleaner.randomMovement();
        cleaner.smartMovement(0, world.origin);
        visualizer.update(world);
    }

    // methods
    private static Coordinate setOrigin() {
        int x = rng.nextInt(world.width);
        int y = rng.nextInt(world.height);

        world.fields[x][y].index = 0;
        return world.fields[x][y].coord;
    }
    private static void numbering(ArrayList<Coordinate> origins, int layer) {
        ArrayList<Coordinate> recurcoords = new ArrayList<>();

        for (Coordinate origin : origins) {
            int x = origin.x;
            int y = origin.y;


            for (int i = -1; i < 1; i++) {
                if (i == 0) i = 1;

                // for x-values
                Coordinate coords = searchInWorld(searchField(x + i, y));
                if (coords != null && searchField(x + i, y).index == -1) {
                    setIndex(coords, layer);
                    recurcoords.add(coords);
                }

                // for y-values
                coords = searchInWorld(searchField(x, y + i));
                if (coords != null && searchField(x, y + i).index == -1) {
                    setIndex(coords, layer);
                    recurcoords.add(coords);
                }
            }
        }

        if (countPlacesByIndex(-1) > 0) {
            numbering(recurcoords, layer+1);
        }
    }
    private static void setIndex(Coordinate c, int value) {
        world.fields[c.x][c.y].index = value;
    }
    private static int countPlacesByIndex(int pIndex) {
        int counter = 0;
        for (Field[] slice : world.fields) {
            for (Field f : slice) {
                if (f.index == pIndex) {
                    counter++;
                }
            }
        }
        return counter;
    }
    private static Field searchField(int x, int y) {
        for (Field[] slice : world.fields) {
            for (Field f : slice) {
                if (f.coord.x == x) {
                    if (f.coord.y == y) {
                        return f;
                    }
                }
            }
        }
        return null;
    }
    private static Coordinate searchInWorld(Field field) {
        if (field == null) {
            return null;
        }

        for (Field[] slice : world.fields) {
            for (Field f : slice) {
                if (f == field) return f.coord;
            }
        }
        return null;
    }
    private static ArrayList<Coordinate> searchByDistance(int value) {
        ArrayList<Coordinate> coords = new ArrayList<>();

        for (Field[] slice : world.fields) {
            for (Field f : slice) {
                if (f.index == value) {
                    coords.add(f.coord);
                }
            }
        }

        return coords;
    }
}
