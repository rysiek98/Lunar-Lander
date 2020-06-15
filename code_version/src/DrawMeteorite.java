import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/** Klasa rysująca meteoryt */
public class DrawMeteorite extends JPanel {

    final private BufferedImage meteorite;
    final private ArrayList<BufferedImage> explosion;
    final int imageCount=27;

    /** Konstruktor klasy
     * @param img obraz meteorytu
     * @param img2 ArrayList obrazów do animacji wybuchu
     */
    DrawMeteorite(BufferedImage img, ArrayList<BufferedImage> img2){
        meteorite = img;
        explosion = img2;
    }

    /** Metoda rysująca odpowiedni obraz meteorytu lub wybuchu */
    public void paintMeteorite2(Graphics g, Meteorite[] table) {
        Graphics2D g2d = (Graphics2D)g;
        for(int i = 0; i < table.length; i++){
            if(table[i].getAlive()) {
                g2d.drawImage(meteorite, (int)table[i].getX(), (int)table[i].getY(), this);
            }else {
                g2d.drawImage(explosion.get(imageCount+table[i].getIterator()), (int)table[i].getX(), (int)table[i].getY(), this);
            }
        }
    }
}
