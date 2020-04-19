import javax.swing.*;
import java.awt.*;

//ZALĄŻEK KLASY WYSWIETLAJĄCY TABELĘ WYNIKÓW

public class ResultsTable extends JFrame {

    public ResultsTable(Point Loc, int width, int heigth){

        pack();
        setSize(new Dimension(width, heigth));
        setLocation(Loc);
        setVisible(true);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                new MainWindow();
            }
        });
    }

}
