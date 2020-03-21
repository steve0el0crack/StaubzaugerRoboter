import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Visualizer extends JFrame {
    private Tile[][] tiles;

    public Visualizer(ArrayList<World.Field> fields) {
        setTitle("World Visualizer");
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setVisible(true);



        tiles = new Tile[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                tiles[x][y] = new Tile();
                tiles[x][y].setBounds(x * 95, y * 95, 95, 95);

                for (World.Field f : fields) {
                    if (f.coords[0] == x && f.coords[1] == y) {
                        tiles[x][y].indexLabel.setText(Integer.toString(f.index));
                        tiles[x][y].setBackground(new Color(f.index * 17, f.index * 17, f.index * 17));
                    }
                }
                add(tiles[x][y]);
            }
        }
    }
}
