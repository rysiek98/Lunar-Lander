import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/** KLASA RYSUJĄCA METEORYT */
public class DrawMeteorite extends JPanel {

    final private BufferedImage meteorite;
    final private ArrayList<BufferedImage> explosion;

    DrawMeteorite(BufferedImage img, ArrayList<BufferedImage> img2){
        meteorite = img;
        explosion = img2;
    }

    public void paintMeteorite2(Graphics g, Meteorite[] table) {
        Graphics2D g2d = (Graphics2D)g;
        for(int i = 0; i < table.length; i++){
            if(table[i].getAlive()) {
                g2d.drawImage(meteorite, (int)table[i].getX(), (int)table[i].getY(), this);
            }else {
                g2d.drawImage(explosion.get(27+table[i].getIterator()), (int)table[i].getX(), (int)table[i].getY(), this);
            }
        }
    }

}
