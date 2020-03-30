package world;

import javax.swing.*;
import java.awt.*;

public class Field extends JPanel {
    public JLabel indexLabel;
    public Coordinate coord;
    public boolean blocked = false;
    private boolean isClean = false;
    public int index;

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

    public void clean() {
        isClean = true;
    }

    public void update() {
        if (isClean) {
            setBackground(new Color(162, 218, 130,  200 - index * 10));
        }
    }

    public boolean isClean() {
        return isClean;
    }
}