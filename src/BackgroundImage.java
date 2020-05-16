import javax.swing.*;
import java.awt.*;


/** KLASA ODPOWIEDZIALNA ZA WYŚWIETLANIE TŁA W MENU GŁÓWNYM */
public class BackgroundImage extends JComponent {
    final private Image image;

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