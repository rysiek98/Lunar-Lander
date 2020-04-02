import javax.swing.*;
import java.awt.*;

public class DrawShip extends JPanel {

    int y;
    int x;

    DrawShip(int height, int width){
        y = (int) (height*0.01);
        x =  (int) (width*0.48);
    }

    public void paintComponent(Graphics g, int width, int height) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.white);
        g2d.fillOval(shipPositionX(), shipPositionY(), 30, 30);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN + Font.BOLD, 15));
        g2d.drawString("X: ", (int) (width*0.01),(int) (height*0.05));
        g2d.drawString("Y: ",(int) (width*0.01) ,(int) (height*0.1));
        g2d.drawString(Integer.toString(x),(int) (width*0.03),(int) (height*0.05));
        g2d.drawString(Integer.toString(y),(int) (width*0.03),(int) (height*0.1));
    }
    public int shipPositionY(){
        if(y<510) {
            y += 1;
        }
        return y;
    }

    public int shipPositionX(){
        return x;
    }
}
