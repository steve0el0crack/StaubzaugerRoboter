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
        indexLabel.setForeground(new Color(0xD8F59C));
        add(indexLabel);
    }

    public void clean() {
        setBackground(new Color(0x4B82D2));
        isClean = true;
    }

    public boolean isClean() {
        return isClean;
    }
}