import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

//KLASA RYSUJĄCA POWIERZCHNIĘ PLANETY I DODATKOWE KOMPONENTY

public class DrawPlanet extends JPanel {

     protected int panelWidth, panelHeight;
     int health = 3;
     private BufferedImage lander;
     private Polygon planet;

     DrawPlanet(){
          File landerImage = new File("lander.png");
          try {
               lander = ImageIO.read(landerImage);
          }catch(IOException e){
               e.printStackTrace();
          }
     }


   public void paintPlanet(Graphics g, int readX[], int readY[], int width, int height, int[] planetColor, JPanel panel, double scaleY, double scaleX) {
        Graphics2D g2d = (Graphics2D) g;

        panelWidth = panel.getWidth();
        panelHeight = panel.getHeight();

        //SKALOWANIE
        AffineTransform at = new AffineTransform();
        at.scale(scaleX, scaleY);
        g2d.setTransform(at);

        new Color(planetColor[0], planetColor[1], planetColor[2]);
        g2d.fillPolygon(readX, readY, 18);
        g2d.setColor(Color.white);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN + Font.BOLD, 50));
        g2d.drawString("#1",(int) (width*0.48), (int) (height*0.1));
        g2d.setFont(new Font("TimesRoman", Font.PLAIN + Font.BOLD, 20));
        g2d.drawString("12345", width - 100,70);
        g2d.setColor(Color.red);

        for(int i = 0; i < health; i++){
             double x = i*0.05 + 0.01;
             g2d.drawImage(lander, (int) (width*x), (int) (height*0.95), this);
        }

        g2d.fillRect(width - 120,20, 100,20);

   }

   public void setHealth(int h){
        health = h;
   }
}
