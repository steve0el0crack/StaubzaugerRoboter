import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Staub here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Staub extends Actor
{
    public static final int ABSTAND_ZU_MOEBELN = 10;
    
    Staub()
    {
        getImage().scale(2,2);
    }

    /**
     * Act - do whatever the Staub wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }    

    public boolean istStaubAufFreiemAreal(int x, int y)
    {
        return getIntersectingObjects(Moebel.class).size() == 0 &&
            getIntersectingObjects(Staub.class).size() == 0 &&
            getObjectsAtOffset(-ABSTAND_ZU_MOEBELN, -ABSTAND_ZU_MOEBELN, Moebel.class).size() == 0 &&
            getObjectsAtOffset(-ABSTAND_ZU_MOEBELN, ABSTAND_ZU_MOEBELN, Moebel.class).size() == 0 &&
            getObjectsAtOffset(ABSTAND_ZU_MOEBELN, -ABSTAND_ZU_MOEBELN, Moebel.class).size() == 0 &&
            getObjectsAtOffset(ABSTAND_ZU_MOEBELN, ABSTAND_ZU_MOEBELN, Moebel.class).size() == 0 
            ;
    }
}
