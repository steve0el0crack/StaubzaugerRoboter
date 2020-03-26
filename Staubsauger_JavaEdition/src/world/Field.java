package world;

public class Field {
    public Coordinate coord;
    public boolean blocked = false;
    public int index;

    Field(int x, int y, boolean pBlocked, int pIndex) {
        coord = new Coordinate(x, y);
        blocked = pBlocked;
        index = pIndex;
    }
}