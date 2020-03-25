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