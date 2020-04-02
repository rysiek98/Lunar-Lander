import javax.swing.*;
import java.awt.*;

public class BackgroundImage extends JComponent {
    private Image image;
    private double scale_x, scale_y;

    public BackgroundImage(Image image, JFrame frame) {
        this.image = image;
        scale_x = obliczscale(frame)[0];
        scale_y = obliczscale(frame)[1];
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        //g2.scale(scale_x,scale_y);
        g2.drawImage(image, 0, 0, this);
    }

    private double[]  obliczscale(JFrame frame) {
        double[] Loc = new double[2];
        Loc[0] = ((double) frame.getSize().width)/(double) 640;
        Loc[1] = ((double)frame.getSize().height)/(double)480;
        return Loc;
    }


}