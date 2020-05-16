import java.awt.*;

/** KLASA WĄTKU RYSUJĄCEGO */
public class MyThread implements Runnable {

    private static volatile boolean running = true;
    private static volatile boolean paused = false;
    private static final Object pauseLock = new Object();
    private final Component component;
    private final Thread t;

    public MyThread(Component component) {
        this.component = component;
        t = new Thread(this, "WatekA");
        t.start();
    }

    @Override
    public void run() {
        while (running) {
            synchronized (pauseLock) {
                if (!running) {
                    break;
                }
                if (paused) {
                    try {
                        synchronized (pauseLock) {
                            pauseLock.wait();
                        }
                    } catch (InterruptedException ex) {
                        break;
                    }
                    if (!running) {
                        break;
                    }
                }
            }
            component.repaint();
            try {
                Thread.sleep(33);
            } catch (Exception e) {
                System.out.println("Wyjątek!");
            }
        }
    }

    public static void stop() {
        running = false;
    }

    public static void pause() {
        paused = true;
    }

    public static void pause1() {
        try {
            Thread.sleep(750);
        } catch (Exception e) {
            System.out.println("Wyjątek!");
        }
        paused = true;
    }

    public static void pause2() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Wyjątek!");
        }
        paused = true;
    }

    public static void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }

}