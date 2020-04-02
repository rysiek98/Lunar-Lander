import javax.swing.*;
import java.awt.*;

public class Game extends JPanel {

    DrawPlanet Planet;
    DrawShip Ship;
    BackgroundRepeat bgd = new BackgroundRepeat("sky_bcg.jpg");
    int y[];
    int width;
    int height;
    Game(DrawPlanet Planet, DrawShip Ship, int readY[], int widthIN, int heightIN){
        y = readY;
        this.Planet = Planet;
        this.Ship = Ship;
        width = widthIN;
        height = heightIN;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        bgd.fillBackground(this, g2d);

        Planet.paintComponent(g2d, y, width, height, this);
        Ship.paintComponent(g2d, width, height);
    }

}
