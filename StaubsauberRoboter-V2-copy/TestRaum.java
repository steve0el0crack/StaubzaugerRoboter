import greenfoot.*;  
import java.util.Random;

public class TestRaum extends Actor
{
    // Die Anzahl Staubteilchen muss ggf. auf schwaecheren Computern heruntergesetzt werden...
    public static final int ANZAHL_STAUBTEILCHEN = 1000;

    

    public void act() 
    {
        if(Greenfoot.isKeyDown("1"))
        {
            clear();
            initialisiereRaum1();
        }
        if(Greenfoot.isKeyDown("2"))
        {
            clear();
            initialisiereRaum2();
        }
    }    

    private void clear()
    {
        getWorld().removeObjects(getWorld().getObjects(Moebel.class));
        getWorld().removeObjects(getWorld().getObjects(Staub.class));
        getWorld().removeObjects(getWorld().getObjects(Staubsaugerroboter.class));
    }

    private void initialisiereRaum1()
    {
        initialisiereTestRaum(1);

        initialisiereMoebel(100,100,200,400);
        initialisiereMoebel(300,200,100,100);
        initialisiereMoebel(500,200,200,100);

        intialisiereRoboter();

        initialisiereStaub();
    }

    private void initialisiereRaum2()
    {
        initialisiereTestRaum(2);

        initialisiereMoebel(100,100,10,10);
        initialisiereMoebel(250,100,10,10);
        initialisiereMoebel(100,250,10,10);
        initialisiereMoebel(250,250,10,10);

        initialisiereMoebel(200,400,50,200);
        initialisiereMoebel(500,400,50,200);

        initialisiereMoebel(600,0,50,200);

        intialisiereRoboter();

        initialisiereStaub();
    }

    private void initialisiereTestRaum(int i)
    {
        getImage().scale(30, 30);
        getImage().setColor(Color.CYAN);
        getImage().fillRect(0, 0, 30, 30);
        getImage().setColor(Color.BLACK);
        getImage().drawString(Integer.toString(i), 12, 18);
    }

    private void initialisiereMoebel(int xMin, int yMin, int xW, int yW)
    {
        Moebel m = new Moebel(xW,yW);
        getWorld().addObject(m,xMin+xW/2 , yMin+yW/2);
    }

    private void intialisiereRoboter()
    {
        Heinzelmann hz = new Heinzelmann();
        getWorld().addObject(hz, hz.origin.getCenter()[0], hz.origin.getCenter()[1]);
    }

    private void initialisiereStaub()
    {
        for(int i = 0; i < ANZAHL_STAUBTEILCHEN; i++)
        {
            int xCandidate;
            int yCandidate;
            Staub s = new Staub();
            getWorld().addObject(s, 0, 0);

            do
            {
                xCandidate = Greenfoot.getRandomNumber(StaubsaugerTestAreal.RAUM_BREITE - 2*Staub.ABSTAND_ZU_MOEBELN + 1) + Staub.ABSTAND_ZU_MOEBELN;
                yCandidate = Greenfoot.getRandomNumber(StaubsaugerTestAreal.RAUM_LAENGE - 2*Staub.ABSTAND_ZU_MOEBELN+1) + Staub.ABSTAND_ZU_MOEBELN;
                s.setLocation(xCandidate, yCandidate);
            } while(!s.istStaubAufFreiemAreal(xCandidate, yCandidate));

            getWorld().addObject(new Staub(), xCandidate, yCandidate); 
        }
    }

}
