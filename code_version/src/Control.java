import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/** Klasa odpowiedzialna za sterowanie i system paliwa */
public class Control {

    private int move_up = 0, move_left = 0, move_right = 0;
    private static int fuel;
    private int combustion;
    private boolean pause = false;
    private static boolean activGame = true;
    private static boolean music;
    private final GameWindow gameWindow;

    /** Konstruktor klasy
     * @param panel panel gry
     * @param fuelIN ilość paliwa
     * @param difLevel poziom trudności
     * @param GameWindow okno gry
     */
    Control(JPanel panel,int fuelIN, String difLevel, GameWindow GameWindow){
        fuel = fuelIN;
        fuelCombustion(difLevel);
        music = true;
        gameWindow = GameWindow;

        InputMap input = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "pause");
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0), "music");
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "restart");
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), "exit");
        ActionMap action = panel.getActionMap();
            action.put("up", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    if (fuel > 0 && activGame) {
                        move_up = -(GameWindow.getGravity() / 3);
                        fuel -= combustion;
                    } else {
                        move_up = 0;
                    }
                }
            });
            action.put("left", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    if (fuel > 0 && activGame) {
                        move_left = -2;
                        fuel -= combustion;
                    } else {
                        move_left = 0;
                    }
                }
            });
            action.put("right", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    if (fuel > 0 && activGame) {
                        move_right = 2;
                        fuel -= combustion;
                    } else {
                        move_right = 0;
                    }
                }
            });
            action.put("pause", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    if (!pause && activGame) {
                        MyThread.pause();
                        pause = true;
                        activGame= false;
                    } else {
                        MyThread.resume();
                        pause = false;
                        activGame = true;
                    }
                }
            });

        action.put("music", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(music){
                    Sound.stopMusic();
                    music = false;
                }else {
                    Sound.startMusic();
                    music = true;
                }
            }
        });

        action.put("restart", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                gameWindow.newGame();
                Game.endGame(false);
            }
        });

        action.put("exit", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                gameWindow.dispose();
                new MainWindow();
            }
        });

    }

    /** Metoda zwracająca przesunięcie w górę */
    public int getMoveUp(){
        return move_up;
    }

    /** Metoda zwracająca przesunięcie w prawo */
    public int getRight(){
        return move_right;
    }

    /** Metoda zwracająca przesunięcie w lewo */
    public int getLeft(){
        return move_left;
    }

    /** Metoda ustawiająca domyślne wartości przesunięć*/
    public void setDefault(){
        move_up = 0;
        move_left = 0;
        move_right = 0;
    }

    /** Metoda zwracająca ilość paliwa */
    public static int getFuel(){
        return fuel;
    }

    /** Metoda ustawiająca ilość paliwa */
    public static void setFuel(int f){
        fuel =f;
    }


    /** Metoda ustawiająca zużycie paliwa zależnie od poziomu trudności */
    public void fuelCombustion(String level){
        if(level.equals("Latwy")){
            combustion= 1;
        }
        else if(level.equals("Sredni")){
            combustion= 2;
        }
        else {
            combustion= 3;
        }
    }

    /** Metoda do zmiany stanu gry */
    public static void changeActivGame(boolean x){
        activGame = x;
    }

    /** Metoda zwracająca czy gra jest aktywna */
    public static boolean getActivGame(){
        return activGame;
    }

    /** Metoda do zmiany stanu muzyki */
    public static void changeMusicStatus(boolean x){
        music = x;
    }
}
