import javax.swing.*;
import java.awt.*;

class Tile extends JPanel {
    private JLabel indexLabel;

    public Tile(int index) {
        setBackground(new Color(0xF4C894));

        // initialise indexLabel
        indexLabel = new JLabel(Integer.toString(index));
        indexLabel.setBounds(getWidth() - 5, getHeight() - 5, 3, 3);
        add(indexLabel);
    }
}