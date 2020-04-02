
import java.awt.Component;


public class MyThread implements  Runnable{

    private final Component component;
    private Thread t;
    public MyThread(Component component) {
        this.component = component;
        t = new Thread(this, "WatekA");
        t.start();
    }

    public void run() {

        while (true) {
            component.repaint();
            try {
                Thread.sleep(5);
            } catch (Exception e) {
                System.out.println("Wyjątek!");
            }
        }

    }
}