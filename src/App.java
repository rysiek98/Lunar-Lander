import java.awt.*;

/**
 * @author  Michał Ryszka, Wojciech Kowalski
 * @version 0.89
 * @since   2020-05-15
 */

/** GŁÓWNA KLASA URUCHAMIAJĄCA APLIKACJE */

public class App {
    public static void main() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow();
            }
        });
    }
}
