package visuals;

import world.Field;
import world.World;
import javax.swing.*;
import java.awt.*;

public class Visualizer extends JFrame {
    // constructor
    public Visualizer(World world) {
        setTitle("world.World visuals.Visualizer");
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(true);
        setVisible(true);

        for (int x = 0; x < world.width; x++) {
            for (int y = 0; y < world.height; y++) {
                world.fields[x][y].setBounds(x * 95, y * 95, 95, 95);
                world.fields[x][y].indexLabel.setText(Integer.toString(world.fields[x][y].index));
                int color = Math.abs(world.fields[x][y].index) * 10 + 50;
                if (!world.fields[x][y].blocked) world.fields[x][y].setBackground(new Color(color, color, color));
                add(world.fields[x][y]);
            }
        }
        repaint();
    }

    // update visualizer method
    public void update(World world) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                world.fields[x][y].setBounds(x * 95, y * 95, 95, 95);
                world.fields[x][y].indexLabel.setText(Integer.toString(world.fields[x][y].index));
                add(world.fields[x][y]);
            }
        }
        repaint();
    }
}
