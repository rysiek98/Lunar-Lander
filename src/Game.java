import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

//PANEL SPAJAJĄCY ELEMENTY GRY

public class Game extends JPanel {

    private DrawPlanet Planet;
    private DrawShip Ship;
    private BackgroundRepeat bgd = new BackgroundRepeat("sky_bcg.jpg");
    private int y[];
    private int x[];
    private int width;
    private int height;
    private int planetColor[];
    private int move_up = 0, move_left = 0, move_right = 0;

    Game(DrawPlanet Planet, DrawShip Ship, int readX[], int readY[], int widthIN, int heightIN, int[] planetColorIN){
        y = readY;
        x = readX;
        this.Planet = Planet;
        this.Ship = Ship;
        width = widthIN;
        height = heightIN;
        planetColor = planetColorIN;
        setupKeyboard();
    }

    //TESTY STEROWANIA
    private void setupKeyboard() {
        InputMap input = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        ActionMap action = this.getActionMap();
        action.put("up", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

               move_up = -3;
               System.out.println("Key up");
            }
        });

        action.put("left", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

                move_left = -4;
            }
        });
        action.put("right", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

                move_right = 4;
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        bgd.fillBackground(this, g2d);
        Planet.paintPlanet(g2d,x,  y, width, height, planetColor, this, scaleY(), scaleX());
        Ship.paintShip(g2d, width, height, move_left, move_right, move_up);
        move_up = 0;
        move_right = 0;
        move_left = 0;
    }

    //METODY OBLICZAJĄCE SKALOWANIE GRY
    public double scaleY(){
        return  (double) GameWindow.getRes_height()/(double) height;
    }

    public double scaleX(){
        return  (double) GameWindow.getRes_width()/(double) width;
    }

}
