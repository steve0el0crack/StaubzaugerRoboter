import greenfoot.*;
import java.util.Random;
import java.util.ArrayList;

public class Heinzelmann extends Staubsaugerroboter {
    private Random rand = new Random();
    private int iterationsCount = 0;

    public Field[][] fields = new Field[10][10];

    public Field origin;

    private int subCycleCounter = 0;
    
    public Heinzelmann() {
        super();
        Random rng = new Random();
        origin = new Field(rng.nextInt(10), rng.nextInt(10));
        origin.setNumber(0);
        cell(10, 10);
    }

    public void act() {
        turnTowards(subCycle()[0], subCycle()[1]);
        if (getX() == subCycle()[0] && getY() == subCycle()[1]) {
            subCycleCounter++;
        } else {
            move(1);
        }
    }
    
    // hin und zurück
    private void cycle() {
        
    }
    
    // one step
    private int[] subCycle() {
        ArrayList<int[]> coords = searchField(origin.getNumber() + subCycleCounter + 1);
        
        return coords.get(0);
    }
    
    private ArrayList<int[]> searchField(int number) {
        ArrayList<int[]> coords = new ArrayList();
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (fields[i][j].getNumber() == number) {
                    coords.add(fields[i][j].getValues());
                }
            }
        }
        
        return coords;
    }
    
    private ArrayList<int[]> maxField() {
        int maxNumber = 0;
        
        ArrayList<int[]> coords = new ArrayList();
        
        // find highest fields
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (fields[i][j].getNumber() >= maxNumber && fields[i][j].clean == false) {
                    maxNumber = fields[i][j].getNumber();
                }
            }
        }
        
        // extract coords of highest fields
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (fields[i][j].getNumber() == maxNumber && fields[i][j].clean == false) {
                    coords.add(fields[i][j].getCoords());
                }
            }
        }
        
        return coords;
    }

    public void cell(int cellCountX, int cellCountY) {
        // creating cells
        for (int i = 0; i < cellCountX; i++) {
            for (int j = 0; j < cellCountY; j++) {
                fields[i][j] = new Field(i, j);
            }
        }

        // numbering cells
        for (int i = 0; i < cellCountX; i++) {
            for (int j = 0; j < cellCountY; j++) {
                fields[i][j].setNumber(Math.abs((i+j) - (origin.getCoords()[0] + origin.getCoords()[1])));
            }
        }
    }
}