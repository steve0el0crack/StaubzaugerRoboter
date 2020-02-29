import greenfoot.*;
import java.util.List;
import java.lang.Math;

public class Staubsaugerroboter extends Actor {
    private static final int MAX_DIST_SENSOR = 80;
    private static final int STEP_SIZE = 5;

    Staubsaugerroboter() {
        getImage().rotate(90);
        getImage().scale(50, 50);
    }
    
    public void move(int dist) {
        int tempX = getX();
        int tempY = getY();

        int xCoor = (int)(Math.cos(((double)getRotation())/360.*2*Math.PI)*5.);
        int yCoor = (int)(Math.sin(((double)getRotation())/360.*2*Math.PI)*5.);
        super.setLocation(getX()+xCoor, getY() + yCoor);
        //        super.move(5);
        if(getIntersectingObjects(Moebel.class).size() > 0)
        {
            //          super.move(-5);
            super.setLocation(tempX,tempY);
        }

        if(atWorldEdge())
        {
            super.setLocation(tempX,tempY);
        }
    }

    private boolean atWorldEdge()
    {
        int WIDTH = 30;
        
        if(getX() <WIDTH || getX() > StaubsaugerTestAreal.RAUM_BREITE - WIDTH)
        {
            return true;
        }
        if(getY() <WIDTH || getY() > StaubsaugerTestAreal.RAUM_LAENGE - WIDTH)
        {
            return true;
        }
        
        return false;
    }

    protected void saugeStaubAuf()
    {
        List staubTeilchen = getIntersectingObjects(Staub.class);

        for(int i=0; i < staubTeilchen.size(); i++)
        {
            getWorld().removeObject((Staub)staubTeilchen.get(i));
        }
    }

    public int abstandVoraus()
    {
        double xCoor, yCoor;
        double xCoorLeft, xCoorRight,yCoorLeft,yCoorRight;
        int ROBOT_WIDTH = 60;

        for (int i = 30; i <= MAX_DIST_SENSOR + ROBOT_WIDTH/2; i+=STEP_SIZE)
        {
            xCoor = (Math.cos(((double)getRotation())/360.*2*Math.PI)*i);
            yCoor = (Math.sin(((double)getRotation())/360.*2*Math.PI)*i);

            //System.out.println("xCoor = " + xCoor + "; yCoor = " + yCoor);

            // zentral vor dem Roboter
            if(offsetSchneidetMoebelOderRand(xCoor,yCoor))
            {
                return i-ROBOT_WIDTH/2;
            }

            // ein bischen links vom Zentrum vor dem Roboter
            xCoorLeft = xCoor + (Math.cos((((double)getRotation())-90.)/360.*2*Math.PI)*5);
            yCoorLeft = yCoor + (Math.sin((((double)getRotation())-90.)/360.*2*Math.PI)*5);

            //System.out.println("xCoor = " + xCoor + "; yCoor = " + yCoor);
            //System.out.println("xCoorLeft = " + xCoorLeft + "; yCoorLeft = " + yCoorLeft);

            if(offsetSchneidetMoebelOderRand(xCoorLeft,yCoorLeft))
            {
                return i-ROBOT_WIDTH/2;
            }

            // ein bischen rechts vom Zentrum vor dem Roboter
            xCoorRight = xCoor + (Math.cos(((double)getRotation()+90)/360.*2*Math.PI)*5);
            yCoorRight = yCoor + (Math.sin(((double)getRotation()+90)/360.*2*Math.PI)*5);

            if(offsetSchneidetMoebelOderRand(xCoorRight,yCoorRight))
            {
                return i-ROBOT_WIDTH/2;
            }
        }
        return MAX_DIST_SENSOR;
    }

    private boolean offsetSchneidetMoebelOderRand(double x, double y) {
        return getX() + x < 0 || getX() + x > StaubsaugerTestAreal.RAUM_BREITE ||
        getY() + y < 0 || getY() + y > StaubsaugerTestAreal.RAUM_LAENGE ||
        getObjectsAtOffset((int)x, (int)y, Moebel.class).size() !=0;
    }
}
