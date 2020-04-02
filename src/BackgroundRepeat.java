import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.*;

public class BackgroundRepeat {

    private BufferedImage tile;
    int tileWidth, tileHeight, panelHeight, panelWidth;

    public BackgroundRepeat(String imgPath){
        try {
            tile = ImageIO.read(getClass().getResource(imgPath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        tileWidth = tile.getWidth();
        tileHeight = tile.getHeight();
    }

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
