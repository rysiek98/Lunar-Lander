import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/** KLASA ODPOWIEDZIALNA ZA STEROWANIE I SYSTEM PALIWA */
public class Control {

    private int move_up = 0, move_left = 0, move_right = 0;
    private static int fuel;
    private int combustion;
    private boolean pause = false;
    private static boolean activGame = true;
    private static boolean music;
    private final GameWindow gameWindow;


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
                    Game.stopMusic();
                    music = false;
                }else {
                    Game.startMusic();
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

    public int getMoveUp(){
        return move_up;
    }
    public int getRight(){
        return move_right;
    }
    public int getLeft(){
        return move_left;
    }

    public void setDefault(){
        move_up = 0;
        move_left =0;
        move_right =0;
    }

    public static int getFuel(){
        return fuel;
    }

    public static void setFuel(int f){
        fuel =f;
    }


    /** METODA NA PODSTAWIE POZIOMU TRUDNOŚCI USTALA ZUŻYCIE PALIWA */
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
    public static void changeActivGame(boolean x){
        activGame = x;
    }

    public static  boolean getActivGame(){
        return activGame;
    }

    public static void changeMusicStatus(boolean x){
        music = x;
    }
}
