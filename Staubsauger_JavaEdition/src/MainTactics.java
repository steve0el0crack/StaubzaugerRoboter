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

    private static int[] setOrigin() {
        world.fields.get(rng.nextInt(world.fields.size())).index = 0;
        return world.fields.get(rng.nextInt(world.fields.size())).coords;
    }

    private static void setIndex(int[] coords, int value) {
        for (int i = 0; i < world.fields.size(); i++) {
            if (world.fields.get(i).coords == coords) {
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
            if (f.coords[0] == x) {
                if (f.coords[1] == y) {
                    return f;
                }
            }
        }

        return null;
    }

    private static int[] searchInWorld(Field field) {
        if (field == null) {
            return null;
        }

        int[] coords = new int[2];

        for (Field f : world.fields) {
            if (f == field) {
                coords = f.coords;
            }
        }

        return coords;
    }

    private static ArrayList<int[]> searchByDistance(int value) {
        ArrayList<int[]> coords = new ArrayList<>();

        for (Field f : world.fields) {
            if (f.index == value) {
                coords.add(f.coords);
            }
        }

        return coords;
    }

    // recursive method
    private static void numbering(ArrayList<int[]> origins, int layer) {
        ArrayList<int[]> recurcoords = new ArrayList<>();

        for (int[] origin : origins) {
            int x = origin[0];
            int y = origin[1];


            for (int i = -1; i < 1; i++) {
                if (i == 0) i = 1;

                // for x-values
                int[] coords = searchInWorld(searchField(x + i, y));
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
