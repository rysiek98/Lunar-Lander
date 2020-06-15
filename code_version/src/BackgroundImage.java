import javax.swing.*;
import java.awt.*;


/** Klasa odpowiedzialna za wyświetlanie tła w menu głównym */
public class BackgroundImage extends JComponent {
    final private Image image;

    /** Konstruktor klasy
     * @param image obiekt klasy Image - obraz w tle menu głównego
     */
    public BackgroundImage(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, 0, 0, this);
    }

}