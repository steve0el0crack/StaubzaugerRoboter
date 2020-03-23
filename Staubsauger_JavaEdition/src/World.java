import java.util.ArrayList;

public class World {
    public ArrayList<Field> fields = new ArrayList<>();

    final int width;
    final int height;

    public World(int x, int y) {
        width = x;
        height = y;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                fields.add(new Field(i, j, false, -1));
            }
        }
    }

    public void present() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                for (Field f : fields) {

                    if (f.coords[0] == x && f.coords[1] == y) {
                        System.out.print(f.index + "  ");
                    }
                }

            }
            System.out.println();
        }
        System.out.println("\n");
    }

    public class Field {
        public int[] coords = new int[2];
        public boolean blocked = false;
        public int index;

        Field(int pX, int pY, boolean pBlocked, int pIndex) {
            coords[0] = pX;
            coords[1] = pY;
            blocked = pBlocked;
            index = pIndex;
        }
    }
}
