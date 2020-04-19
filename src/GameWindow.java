import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

//GŁÓWNE OKNO GRY

public class GameWindow extends JFrame {

    private int readYPlanet[];
    private int readXPlanet[];
    private int readPlanetColor[];
    static int res_width;
    static int res_height;

    public GameWindow(Point Loc) {
        try {
            LoadingLevel File = new LoadingLevel(new File("testLevel.txt"));
            readYPlanet = File.getElevation();
            readXPlanet = File.getPosition();
            readPlanetColor = File.getPlanetColor();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setSize(1280, 720);

        getContentPane().addComponentListener(new ComponentAdapter() { //WYKRYWA ZMIANĘ ROZMIARU OKNA
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Component c = (Component) e.getSource();
                res_height = c.getHeight();
                res_width = c.getWidth();
            }
        });

        Game game = new Game(new DrawPlanet(), new DrawShip(getSize().height, getSize().width), readXPlanet, readYPlanet, getSize().width, getSize().height, readPlanetColor);
        add(game);
        setLocation(Loc);
        setVisible(true);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                new MainWindow();
            }
        });

        new MyThread(game); //TU TWORZY SIĘ NOWY WĄTEK OBSŁUGUJĄCY RYSOWANIE
    }

    //FUNKCJE ZAWIERAJĄCE NOWE WYMIARY OKNA
    static public int getRes_width(){
        return res_width;
    }

    static public int getRes_height(){
        return res_height;
    }
}
