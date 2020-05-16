
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;


/** KLASA RYSUJĄCA POWIERZCHNIE PLANETY */
public class DrawPlanet extends JPanel {

     protected int panelWidth, panelHeight;
     private int[] readX;
     private int[] readY;
     private int[] planetColor;
     private int[] lands;

     DrawPlanet(int[] x, int[] y, int[] color, int[] landIN){
         readX = x;
         readY = y;
         planetColor = color;
         lands = landIN;
     }

   public void paintPlanet(Graphics g, JPanel panel, double scaleY, double scaleX) {
        Graphics2D g2d = (Graphics2D) g;

        panelWidth = panel.getWidth();
        panelHeight = panel.getHeight();

        //SKALOWANIE OKNA GRY
        AffineTransform at = new AffineTransform();
        at.scale(scaleX, scaleY);
        g2d.setTransform(at);
        g2d.setColor(new Color(planetColor[0], planetColor[1], planetColor[2]));
        g2d.fillPolygon(readX, readY, readY.length);
        drawLands(g2d);
   }

   public void drawLands(Graphics2D g2d){
       g2d.setColor(Color.RED);
       for(int i=0; i<lands.length; i+=3){
           g2d.fillRect(lands[i], lands[i+1], lands[i+2],8);
       }
   }

    public void loadData(int[] readXPlanet, int[] readYPlanet, int[] readPlanetColor, int[] readLands) {
        readX = readXPlanet;
        readY = readYPlanet;
        planetColor = readPlanetColor;
        lands = readLands;
    }
}
