import javax.swing.*;
import java.awt.*;

/** Interfejs dostraczający metody do lokalizacji okna na ekranie*/
public interface WindowLocation {

    /** Metoda statyczna pomagająca wyśrodkować okno
     * @param Loc tablica 4 wartości Integer [współrzędna x, współrzędna y, szerokość okna, wysokość okna]
     * @param frame obiekt rozszerzający JFrame na rzecz którego wykonujemy metode
     * @return obiekt klasy Point - punkt na środku okna
     * */
    static Point setLoc(int[] Loc, JFrame frame) {
        Point result = new Point();
        Loc[2] = Loc[2]/2 - (frame.getSize().width/2);
        Loc[3] = Loc[3]/2 - (frame.getSize().height/2);
        result.x = Loc[0] + Loc[2];
        result.y = Loc[1] + Loc[3];
        return result;
    }

    /** Metoda pomocnicza do środkowania okien
     * @param frame obiekt typu JFrame pobieramy jego współrzędne i wymiary
     * @return tablica 4 wartości Integer [współrzędna x, współrzędna y, szerokość okna, wysokość okna]
     *  */
    static int[]  centerFrame(JFrame frame) {
        int[] Loc = new int[4];
        Loc[0] = frame.getLocationOnScreen().x;
        Loc[1] = frame.getLocationOnScreen().y;
        Loc[2] = frame.getSize().width;
        Loc[3] = frame.getSize().height;
        return Loc;
    }

}
