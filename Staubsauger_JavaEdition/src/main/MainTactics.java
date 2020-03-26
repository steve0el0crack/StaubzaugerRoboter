package main;

import visuals.Visualizer;
import world.Coordinate;
import world.Field;
import world.World;

import java.util.ArrayList;
import java.util.Random;

public class MainTactics {
    private static World world;
    private static Random rng = new Random();

    // main process
    public static void main(String[] args) {
        world = new World(10, 10);

        setOrigin();

        numbering(searchByDistance(0), 1);

        Visualizer visualizer = new Visualizer(world.fields);
        visualizer.repaint();
    }

    private static Coordinate setOrigin() {
        world.fields.get(rng.nextInt(world.fields.size())).index = 0;
        return world.fields.get(rng.nextInt(world.fields.size())).coord;
    }

    private static void setIndex(Coordinate coord, int value) {
        for (int i = 0; i < world.fields.size(); i++) {
            if (world.fields.get(i).coord == coord) {
                world.fields.get(i).index = value;
            }
        }
    }

    private static int countPlaces(int pIndex) {
        int counter = 0;
        for (Field f : world.fields) {
            if (f.index == pIndex) {
                counter++;
            }
        }
        return counter;
    }

    private static Field searchField(int x, int y) {

        for (Field f : world.fields) {
            if (f.coord.x == x) {
                if (f.coord.y == y) {
                    return f;
                }
            }
        }

        return null;
    }

    private static Coordinate searchInWorld(Field field) {
        if (field == null) {
            return null;
        }

        Coordinate coord;

        for (Field f : world.fields) {
            if (f == field) {
                return f.coord;
            }
        }

        return null;
    }

    private static ArrayList<Coordinate> searchByDistance(int value) {
        ArrayList<Coordinate> coords = new ArrayList<>();

        for (Field f : world.fields) {
            if (f.index == value) {
                coords.add(f.coord);
            }
        }

        return coords;
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

        if (countPlaces(-1) > 0) {
            numbering(recurcoords, layer+1);
        }
    }
}
