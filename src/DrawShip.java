import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


/** KLASA RYSUJĄCA LĄDOWNIK I JEGO AKTUALNĄ POZYCJE */
public class DrawShip extends JPanel {

    final private BufferedImage lander;


    DrawShip(BufferedImage x){
        lander = x;
    }
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
