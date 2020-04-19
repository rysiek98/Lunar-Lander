import javax.swing.*;
import java.awt.*;

//ZALĄŻEK KLASY WYSWIETLAJĄCY ZASADY GRY

public class Rules extends JFrame {

    public Rules(int Loc[]){

        pack();
        setSize(new Dimension(640, 480));
        setLocation(setLoc(Loc));
        setVisible(true);
    }

    private Point setLoc(int[] Loc) {
        Point result = new Point();
        Loc[2] = Loc[2]/2 - (this.getSize().width/2);
        Loc[3] = Loc[3]/2 - (this.getSize().height/2);
        result.x = Loc[0] + Loc[2];
        result.y = Loc[1] + Loc[3];
        return result;
    }


}
