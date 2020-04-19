import java.awt.Component;

//WĄTEK OBSŁUGUJĄCY RYSOWANIE GRY

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
                Thread.sleep(30);
            } catch (Exception e) {
                System.out.println("Wyjątek!");
            }
        }
    }
}