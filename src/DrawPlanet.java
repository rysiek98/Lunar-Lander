import javax.swing.*;
import java.awt.*;

public class DrawPlanet extends JPanel {

   int panelWidth, panelHeight;
   public void paintComponent(Graphics g, int readY[], int width, int height, JPanel panel) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        panelWidth = panel.getWidth();
        panelHeight = panel.getHeight();

        int[] readX = new int[readY.length];
        for(int i = 0; i < readX.length; i++) {
           readX[i] = i*panelWidth/17;
        }

        g2d.fillPolygon(readX, readY, 18);
        g2d.setColor(Color.white);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN + Font.BOLD, 50));
        g2d.drawString("#1",(int) (height*0.48), (int) (height*0.1));
        g2d.setFont(new Font("TimesRoman", Font.PLAIN + Font.BOLD, 20));
        g2d.drawString("12345",(int) (width*0.9),(int) (height*0.1));
        g2d.setColor(Color.red);
        g2d.fillOval((int) (width*0.01),(int) (height*0.88) , 30, 30);
        g2d.fillOval((int) (width*0.06),(int) (height*0.88) , 30, 30);
        g2d.fillOval((int) (width*0.11),(int) (height*0.88) , 30, 30);
        g2d.fillRect((int) (width*0.85),(int) (height*0.02), 100,20);

     }
}
