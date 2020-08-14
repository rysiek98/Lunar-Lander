import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/** Klasa powtarzająca obraz w tle podczas zmieniania wielkośći okna */
public class BackgroundRepeat {

    private BufferedImage tile;
    int tileWidth, tileHeight, panelHeight, panelWidth;

    /**Konstruktor klasy
     * @param imgPath ścieżka prowadząca do obrazu
     */
    public BackgroundRepeat(String imgPath){
        try {
            tile = ImageIO.read(new File(imgPath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        tileWidth = tile.getWidth();
        tileHeight = tile.getHeight();
    }

    /** Metoda wypełniająca tło
     * @param panel obiekt JPanel, którego tło ma być wypełnione
     * @param g2d obiekt Graphics2D
     */
    public void fillBackground(JPanel panel, Graphics2D g2d){
        panelHeight = panel.getHeight();
        panelWidth = panel.getWidth();
        for (int y1 = 0; y1 < panelHeight; y1 += tileHeight) {
            for (int x1 = 0; x1 < panelWidth; x1 += tileWidth) {
                g2d.drawImage(tile, x1, y1, panel);
            }
        }
    }
}
