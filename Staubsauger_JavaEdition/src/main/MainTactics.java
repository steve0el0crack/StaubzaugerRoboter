package main;

import visuals.Visualizer;
import world.Coordinate;
import world.Field;
import world.World;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainTactics {
    private static World world;
    private static Random rng = new Random();
    private static Visualizer visualizer;

    // main process
    public static void main(String[] args) {
        // max size for x and y is 10
        world = new World(10, 10);

        Coordinate origin = setOrigin();

        numbering(searchByDistance(0), 1);

        visualizer = new Visualizer(world);

        randomMove(origin);

        visualizer.repaint();
    }

    private static Coordinate setOrigin() {
        Random rng = new Random();
        int x = rng.nextInt(world.width);
        int y = rng.nextInt(world.height);

        world.fields[x][y].index = 0;
        return world.fields[x][y].coord;
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

    public static int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
        int random = start + rnd.nextInt(end - start + 1 - exclude.length);
        for (int ex : exclude) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }

    // recursive method
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

    // recursive method
    private static int counter = 0;   // random movement iterations counter
    private static void randomMove(Coordinate origin) {
        // increasing iterations counter
        counter++;

        // clean field
        world.fields[origin.x][origin.y].clean();

        // calculating destiny field
        Coordinate destiny = rng.nextBoolean() ? new Coordinate(origin.x + getRandomWithExclusion(rng, -1, 1, 0),
                origin.y) : new Coordinate(origin.x, origin.y + getRandomWithExclusion(rng, -1, 1, 0));
        try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // updating visualizer
        visualizer.updateWorld(world);

        // Termination condition
        if (counter <= 100) {
            randomMove(destiny);
        }
    }
}
