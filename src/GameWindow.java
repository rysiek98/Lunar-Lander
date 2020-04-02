import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GameWindow extends JFrame {

    int readYPlanet[];

    public GameWindow(Point Loc) {
        try {
            LoadingLevel File = new LoadingLevel(new File("testLevel.txt"));
            readYPlanet = File.getElevation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSize(800, 700);
        scaleYPlanet();
        Game game = new Game(new DrawPlanet(), new DrawShip(getSize().height, getSize().width), readYPlanet, getSize().width, getSize().height);
        add(game);
        setLocation(Loc);
        setVisible(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                new MainWindow(locPoint(), getWidth(), getHeigth());
            }
        });

        new MyThread(game);
    }


    private Point locPoint() {
        return this.getLocation();
    }

    public int getWidth() {
        return this.getSize().width;
    }

    public int getHeigth() {
        return this.getSize().height;
    }

    public void scaleYPlanet(){
        Dimension size = getSize();
        for(int i =0; i<readYPlanet.length; i++){
            readYPlanet[i] = (int)(((double)readYPlanet[i]/100)*size.height);
            System.out.println(readYPlanet[i]);
        }
    }
}
