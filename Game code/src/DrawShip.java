import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


/** Klasa rysująca lądownik*/
public class DrawShip extends JPanel {

    final private BufferedImage lander;

    /** Konstruktor klasy
     * @param x BufferedImage lądownika
     */
    DrawShip(BufferedImage x){
        lander = x;
    }

    /** Metoda wysująca statek oraz jego pozycję
     * @param g komponent Graphics
     * @param width szerokość lądownika
     * @param height wysokość lądownika
     * @param move_x pozycja X lądownika
     * @param move_y pozycja Y lądownika
     */
    public void paintShip(Graphics g, int width, int height, int move_x, int move_y) {
        Graphics2D g2d = (Graphics2D)g;

        g2d.drawImage(lander, move_x, move_y, this);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN + Font.BOLD, 15));
        g2d.setColor(Color.red);
        g2d.drawString("X: ", (int) (width*0.01),(int) (height*0.05));
        g2d.drawString("Y: ", (int) (width*0.01) ,(int) (height*0.1));
        g2d.drawString(Integer.toString(move_x),(int) (width*0.03),(int) (height*0.05));
        g2d.drawString(Integer.toString(move_y),(int) (width*0.03),(int) (height*0.1));
    }

}
