package visuals;

import javax.swing.*;
import world.*;

public class Visualizer extends JFrame {
    private Tile[][] tiles;

    public Visualizer() {
        setTitle("world.World visuals.Visualizer");
        setSize(1080, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setVisible(true);

        tiles = new Tile[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                tiles[x][y] = new Tile(1);
                tiles[x][y].setBounds(x * 100 + 3, y * 100 + 3, 100, 100);
                add(tiles[x][y]);
            }
        }
    }
}
