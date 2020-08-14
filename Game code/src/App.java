import javax.swing.*;
import java.awt.*;

/**
 * @author  Michał Ryszka, Wojciech Kowalski
 * @version 1.01
 * @since   2020-06-08

/** Główna klasa uruchamiająca aplikację*/

public class App {
    public static void main() {
        EventQueue.invokeLater(() -> {
            new MainWindow();
            if(Client.Online()) {
                String[] options = { "Ok"};
               JOptionPane.showOptionDialog(
                        null,
                        "Gra w trybie online!",
                        "Stan sieciowy aplikacji",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        null);
            }else {
                String[] options = { "Ok"};
                JOptionPane.showOptionDialog(
                        null,
                        "Gra w trybie offline!",
                        "Stan sieciowy aplikacji",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        null);
            }
        });
    }
}
