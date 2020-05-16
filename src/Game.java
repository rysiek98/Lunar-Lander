import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/** KLASA RYSUJĄCA ELEMENTY GRY */
public class Game extends JPanel {

    private final DrawPlanet Planet;
    private final DrawShip Ship;
    private final DrawMeteorite Meteorite;
    private final BackgroundRepeat bgd = new BackgroundRepeat("img/sky_bcg.jpg");
    private final int width;
    private final int height;
    private final Control control;
    private final Physic physic;
    private int health;
    private final BufferedImage lander, musicON, musicOFF;
    private static String level;
    private int points;
    private static boolean end = false;
    static String musicPath;
    static AudioInputStream audioInput;
    static Clip clip;
    private static boolean music;
    private int iterator;

    Game(DrawMeteorite MeteoriteIN, DrawPlanet PlanetIN, DrawShip ShipIN, int widthIN, int heightIN, BufferedImage landerIN, Physic physicIN, String difLevel, int  healthIN, int pointsIN, BufferedImage musicONIN, BufferedImage musicOFFIN, boolean musicSettings, GameWindow gameWindow){
        music = musicSettings;
        Planet = PlanetIN;
        Ship = ShipIN;
        width = widthIN;
        height = heightIN;
        control = new Control(this, 100, difLevel, gameWindow);
        physic = physicIN;
        lander = landerIN;
        health =  healthIN;
        points = pointsIN;
        Meteorite = MeteoriteIN;
        musicOFF = musicOFFIN;
        musicON = musicONIN;
        iterator = 0;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        physic.colision();
        physic.landing();
        bgd.fillBackground(this, g2d);
        if(iterator >3 && iterator <9){
            Physic.setLanderMove(false);
            Physic.setMeteoriteMove(false);
            startScreen(g2d);
            if(iterator >4 && iterator <9) {
                MyThread.pause1();
                MyThread.resume();
            }
        }
        else {
            Physic.setLanderMove(true);
            Physic.setMeteoriteMove(true);
        }
        Planet.paintPlanet(g2d,this, GameWindow.scaleY(height), GameWindow.scaleX(width));
        Ship.paintShip(g2d, width, height, physic.shipPositionX(control.getLeft(), control.getRight()), physic.shipPositionY(control.getMoveUp()));
        g2d.setColor(Color.white);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN + Font.BOLD, 50));
        g2d.drawString(level,(int) (width*0.48), (int) (height*0.1));
        g2d.setFont(new Font("TimesRoman", Font.PLAIN + Font.BOLD, 20));
        g2d.drawString(Integer.toString(points), width - 100,70);
        g2d.setColor(Color.red);
        drawHealth(g2d);
        g2d.fillRect(width - 120,20, 105,20);
        g2d.setColor(Color.WHITE);
        int fuelOld = 100;
        g2d.fillRect(width - 118+(fuelOld - Control.getFuel()),22, Control.getFuel(),16);
        if(physic.startMeteo()){
            physic.meteoriteColision();
            Meteorite.paintMeteorite2(g2d, physic.getMeteors());
        }
        musicIcon(g2d);
        control.setDefault();
        if(end){
            endScreen(g2d);
        }
        iterator++;
    }

    public void setHealth(int h){
        health = h;
    }

    public void drawHealth(Graphics2D g2d){
        for(int i = 0; i < health; i++){
            double x = i*0.05 + 0.01;
            g2d.drawImage(lander, (int) (width*x), (int) (height*0.95), this);
        }
    }

    public static void setLevelText(String lv){
        level = lv;
    }

    public void setPoints(int points) {
        this.points = points;
    }


    public void endScreen(Graphics2D g2d){
        g2d.setColor(Color.white);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN + Font.BOLD, 50));
        g2d.drawString("Koniec gry!",(int) (width*0.40), (int) (height*0.25));
        g2d.setFont(new Font("TimesRoman", Font.PLAIN + Font.BOLD, 30));
        g2d.setColor(Color.RED);
        g2d.drawString("Twoj wynik: "+points,(int) (width*0.40), (int) (height*0.35));
        g2d.setColor(Color.white);
        g2d.drawString("(E) - Koniec gry",(int) (width*0.33), (int) (height*0.45));
        g2d.drawString("(R) - Nowa gra",(int) (width*0.51), (int) (height*0.45));

    }
    public static void endGame(boolean x){
        end = x;
    }

    public static void playSound(boolean play) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        musicPath = "2001.wav";

        audioInput = AudioSystem.getAudioInputStream(new File(musicPath).getAbsoluteFile());

        clip = AudioSystem.getClip();

        clip.open(audioInput);
        if(play) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }else{
            Control.changeMusicStatus(false);
            music = false;
        }
    }

    public static void stopMusic(){
        clip.stop();
        music = false;
    }

    public static void startMusic(){
        clip.start();
        music = true;
    }

    public void musicIcon(Graphics2D g2d){
        if(music) {
            g2d.drawImage(musicON, (int) (width * 0.95), (int) (height * 0.92), this);
        }else {
            g2d.drawImage(musicOFF, (int) (width * 0.95), (int) (height * 0.92), this);
        }
    }

    public void startScreen(Graphics2D g2d){
        g2d.setColor(Color.white);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN + Font.BOLD, 50));
        if(iterator < 7) {
            g2d.drawString(Integer.toString(iterator - 3), (int) (width * 0.48), (int) (height * 0.25));
        }else{
            g2d.drawString("Start!", (int) (width * 0.45), (int) (height * 0.25));
        }
    }

    public void resetIterator(){
        iterator = 0;
    }
}
