import java.awt.EventQueue;

//GŁÓWNA KLASA URUCHAMIAJĄCA APLIKACJĘ

public class App {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow();
            }
        });
    }
}
