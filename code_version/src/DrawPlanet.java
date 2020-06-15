
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;


/** Klasa rysująca powierzchnię planety */
public class DrawPlanet extends JPanel {

     protected int panelWidth, panelHeight;
     private int[] readX;
     private int[] readY;
     private int[] planetColor;
     private int[] lands;

    /** Konstruktor klasy
     * @param x tablica wartości współrzędnych x poligonu
     * @param y tablica wartości współrzędnych y poligonu
     * @param color tablica wartości RGB koloru planety
     * @param landIN tablica wartości współrzędnych lądowisk
     */
     DrawPlanet(int[] x, int[] y, int[] color, int[] landIN){
         readX = x;
         readY = y;
         planetColor = color;
         lands = landIN;
     }

    /** Metoda rysująca planetę
     * @param g komponent Graphics
     * @param panel panel w krórym rysowana jest gra
     * @param scaleY skala w osi OY w której rysowana jest gra
     * @param scaleX skala w osi OX w której rysowana jest gra
     */
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

    /** Metoda rysująca lądowiska
     * @param g2d komponent Graphics g2d
     */
   public void drawLands(Graphics2D g2d){
       g2d.setColor(Color.RED);
       for(int i=0; i<lands.length; i+=3){
           g2d.fillRect(lands[i], lands[i+1], lands[i+2],8);
       }
   }

    /** Metoda przypisująca dane planecie
     * @param readXPlanet współrzędne x planety
     * @param readYPlanet współrzędne y planety
     * @param readPlanetColor kolor planety
     * @param readLands współrzędne lądowisk
     */
    public void loadData(int[] readXPlanet, int[] readYPlanet, int[] readPlanetColor, int[] readLands) {
        readX = readXPlanet;
        readY = readYPlanet;
        planetColor = readPlanetColor;
        lands = readLands;
    }
}
