import javax.swing.*;
import java.awt.*;

/**
 * @author  Michał Ryszka, Wojciech Kowalski
 * @version 1.00
 * @since   2020-06-08

/** Główna klasa uruchamiająca aplikację*/

public class App {
    public static void main() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow();
                if(Client.Online()) {
                    JOptionPane.showMessageDialog(null, "Gra w trybie online!");
                }else {
                    JOptionPane.showMessageDialog(null, "Gra w trybie offline!");
                }
            }
        });
    }
}
