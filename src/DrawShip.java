import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//KLASA RYSUJĄCA LĄDOWNIK I JEGO POZYCJĘ

public class DrawShip extends JPanel {

    int y;
    int x;
    private BufferedImage lander;

    DrawShip(int height, int width){

        y = (int) (height*0.01);
        x =  (int) (width*0.48);

        File landerImage = new File("lander.png");
        try {
            lander = ImageIO.read(landerImage);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void paintShip(Graphics g, int width, int height, int move_left, int move_right, int move_up) {
        Graphics2D g2d = (Graphics2D)g;

        //g2d.fillOval(shipPositionX(move_left, move_right), shipPositionY(move_up), 28, 28);
        g2d.drawImage(lander, shipPositionX(move_left, move_right), shipPositionY(move_up), this);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN + Font.BOLD, 15));
        g2d.drawString("X: ", (int) (width*0.01),(int) (height*0.05));
        g2d.drawString("Y: ", (int) (width*0.01) ,(int) (height*0.1));
        g2d.drawString(Integer.toString(x),(int) (width*0.03),(int) (height*0.05));
        g2d.drawString(Integer.toString(y),(int) (width*0.03),(int) (height*0.1));
    }

    //TESTOWA FUNKCJA PORUSZAJĄCA STATKIEM
    public int shipPositionY(int move_up){
        if(move_up == 0) {
            if (y < 610) {
                y += 1;
            }
        }else{
            if(y > 4) {
              y = y +move_up;
            }
        }
        System.out.println("Pozycja y= "+y+" move_up= "+move_up);
        return y;
    }

    public int shipPositionX(int move_left, int move_right)
    {
        if(x > 5){
            x = x + move_left;
        }
        if(x < 1248){
            x = x + move_right;
        }

        return x;
    }

}
