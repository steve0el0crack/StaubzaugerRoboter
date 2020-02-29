import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StaubsaugerTestAreal extends World
{
    public static final int RAUM_LAENGE = 600;
    public static final int RAUM_BREITE = 800;
    
    
    
    public StaubsaugerTestAreal() {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(RAUM_BREITE, RAUM_LAENGE, 1); 
        
        prepare();
    }
    
    private void prepare() {
        TestRaum t = new TestRaum();
        addObject(t, RAUM_BREITE-50, 50);
    }
}
