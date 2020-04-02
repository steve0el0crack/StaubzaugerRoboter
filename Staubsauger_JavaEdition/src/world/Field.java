package world;

import javax.swing.*;
import java.awt.*;

public class Field extends JPanel {
    // fields
    public JLabel indexLabel;
    public Coordinate coord;
    public boolean blocked = false;
    private boolean isClean = false;
    private int cleanness = 0;
    public int index;

    // constructor
    public Field(int x, int y, boolean pBlocked, int pIndex) {
        // initialise Field
        coord = new Coordinate(x, y);
        blocked = pBlocked;
        index = pIndex;

        // initialise indexLabel
        indexLabel = new JLabel();
        indexLabel.setBounds(getX() + 50, getY() + 50, 0, 0);
        indexLabel.setForeground(new Color(0x000000));
        add(indexLabel);
    }

    // methods
    public void clean() {
        isClean = true;
        int r = Math.min(162 + cleanness, 255);
        int g = Math.max(218 - cleanness, 0);
        int b = Math.max(130 - cleanness, 0);
        int a = 200;
        setBackground(new Color(r, g, b, a));
        cleanness += 7;
    }
    public boolean isClean() {
        return isClean;
    }
    public void reset() {
        isClean = false;
        cleanness = 0;
    }
}