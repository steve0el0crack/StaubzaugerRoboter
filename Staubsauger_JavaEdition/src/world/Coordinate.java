package world;

public class Coordinate {
    public int x, y;

    public Coordinate(int pX, int pY) {
        x = pX;
        y = pY;
    }

    public void normalize() {
        x = x >= 1 ? 1 : Math.max(x, -1);
        y = y >= 1 ? 1 : Math.max(y, -1);
    }
}
