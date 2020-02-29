import greenfoot.*;

public class Field extends Actor {
    public static final int fieldWidth = 80;
    public static final int fieldHeight = 60;

    private int xValue;
    private int yValue;
    private int xCoord;
    private int yCoord;
    private int number;

    private boolean blocked;
    public boolean clean;

    // constructor
    public Field(int pX, int pY) {
        clean = false;
        blocked = false;

        xCoord = pX;
        yCoord = pY;

        xValue = pX * fieldWidth;
        yValue = pY * fieldHeight;
    }

    // get center of field
    public int[] getCenter() {
        int[] tmp = {xValue + fieldWidth / 2, yValue + fieldHeight / 2};
        return tmp;
    }

    public int[] getCoords() {
        int[] tmp = {xCoord, yCoord};
        return tmp;
    }

    public int[] getValues() {
        int[] tmp = {xValue, yValue};
        return tmp;
    }

    // get number
    public int getNumber() {
        return number;
    }

    // set number
    public void setNumber(int n) {
        number = n;
    }
}