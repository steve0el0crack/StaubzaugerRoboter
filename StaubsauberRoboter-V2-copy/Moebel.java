import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Möbel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Moebel extends Actor
{
    private int xMin, yMin, xW, yW;

    public Moebel(int xWidth, int yWidth)
    {
        xW = xWidth;
        yW = yWidth;
        
        this.getImage().scale(xW, yW);
    }

    /**
     * Act - do whatever the Möbel wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }    
}
