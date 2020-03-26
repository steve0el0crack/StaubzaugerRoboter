package visuals;

import world.Field;
import world.World;

import javax.swing.*;
import java.awt.*;

public class Visualizer extends JFrame {
    private Field[][] tiles;

    public static void main(String[] args) {

    }

    public Visualizer(World world) {
        setTitle("world.World visuals.Visualizer");
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setVisible(true);

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                world.fields[x][y].setBounds(x * 95, y * 95, 95, 95);
                world.fields[x][y].indexLabel.setText(Integer.toString(world.fields[x][y].index));
                int colorMultiplier = Math.abs(world.fields[x][y].index) * 10;
                world.fields[x][y].setBackground(new Color(colorMultiplier, colorMultiplier, colorMultiplier));
                add(world.fields[x][y]);
            }
        }
        repaint();
    }

    public void update(World world) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                world.fields[x][y].setBounds(x * 95, y * 95, 95, 95);
                world.fields[x][y].indexLabel.setText(Integer.toString(world.fields[x][y].index));
                world.fields[x][y].update();
                add(world.fields[x][y]);
            }
        }
        repaint();
    }
}
