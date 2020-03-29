package world;

public class World {
    public Field[][] fields;

    public final int width;
    public final int height;

    public Coordinate origin;

    public World(int x, int y) {
        width = x;
        height = y;

        fields = new Field[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fields[i][j] = new Field(i, j, false, -1);
            }
        }
    }

    public void present() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                for (Field[] slice : fields) {
                    for (Field f : slice) {
                        if (f.coord.x == x && f.coord.y == y) {
                            System.out.print(f.index + "  ");
                        }
                    }
                }
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    public void setOrigin(Coordinate o) {
        origin = o;
    }
}
