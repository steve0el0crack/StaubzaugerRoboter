package world;

import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {
    public JLabel indexLabel;

    public Tile() {
        // initialise indexLabel
        indexLabel = new JLabel();
        indexLabel.setBounds(getX() + 50, getY() + 50, 0, 0);
        indexLabel.setForeground(new Color(0xD8F59C));
        add(indexLabel);
    }
}