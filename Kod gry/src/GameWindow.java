import javax.imageio.ImageIO;
import javax.sound.midi.SysexMessage;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/** Klasa tworząca okno gry */

public class GameWindow extends JFrame implements WindowLocation{

    private int[] readYPlanet;
    private int[] readXPlanet;
    private int[] readPlanetColor;
    private int[] readLands;
    private int xLander, yLander;
    static int res_width;
    static int res_height;
    private BufferedImage lander, musicON, musicOFF;
    private BufferedImage meteorPNG;
    private final ArrayList<BufferedImage> explosion = new ArrayList<BufferedImage>(26);;
    private final String Nick, DifficultLevel, MusicSettings;
    private int health = 3;
    private final Game game;
    private final Physic physic;
    private final DrawPlanet drawPlanet;
    private static int gravity;
    private int points;
    private int pointForSmallLand = 0;
    private int meteorites;
    private int vYMax; //MAX PREDKOSC STATKU NA DANEJ PLANECIE
    private double aPlanet; //OPOR NA DANEJ PLANECIE (DO ZWALNIANIA STATKU W OX)
    private int level;
    private final ArrayList<String> levels;

    /** Konstruktor klasy
     * @param Loc tablica 4 wartości Integer [współrzędna x, współrzędna y, szerokość okna, wysokość okna]
     * @param nick nazwa gracza
     * @param difLevel poziom trudności
     * @param musicSettings ustawienie muzyki
     */
    public GameWindow(int[] Loc, String nick, String difLevel, String musicSettings) {
        //DODAWANIE SCIEZEK DO PLIKOW KONF. POSZCZEGOLNYCH MAP
        if(!Client.Online()){
            levels = new ArrayList<String>();
            levels.add("level1");
            levels.add("level2");
            levels.add("level3");
            levels.add("level4");
            levels.add("level5");
            levels.add("level6");
            levels.add("level7");
            levels.add("level8");
            levels.add("level9");
        }else{
            levels = new ArrayList<String>(Client.getLevelList());
        }
        //WCZYTYWANIE DANYCH MAPY
        loadData(levels.get(0));
        setSize(1280, 720);
        MusicSettings = musicSettings;
        //WYKRYWANIE ZMIANY ROZMIARU OKNA
        getContentPane().addComponentListener(new ComponentAdapter() { //WYKRYWA ZMIANE ROZMIARU OKNA
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Component c = (Component) e.getSource();
                res_height = c.getHeight();
                res_width = c.getWidth();
            }
        });

        level = 1;
        physic = new Physic(xLander, yLander, readLands, readXPlanet, readYPlanet, this, gravity, meteorites, vYMax, aPlanet);
        drawPlanet = new DrawPlanet(readXPlanet, readYPlanet,  readPlanetColor, readLands);
        game = new Game(new DrawMeteorite(meteorPNG, explosion), drawPlanet, new DrawShip(lander),getSize().width, getSize().height, lander, physic, difLevel, health, points, musicON, musicOFF, musicSetting(), this);

        try {
            Sound.playSound(musicSetting());
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            System.err.println("Blad odtwarzania dzwieku! " + e);
        }

        Game.setLevelText("#"+ level);
        add(game);
        setLocation(WindowLocation.setLoc(Loc,this));
        setVisible(true);
        Nick = nick;
        DifficultLevel = difLevel;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                new MainWindow();
            }
        });
        points = 0;
        newGame();
        new MyThread(game); //TU TWORZY SIE NOWY WATEK OBSLUGUJACY RYSOWANIE
    }

    /** Metoda do obliczania przeskalowania okna gry */
    static public double scaleY(int height){
        return  (double) res_height/(double) height;
    }

    /** Metoda do obliczania przeskalowania okna gry */
    static public double scaleX(int width){
        return  (double) res_width/(double) width;
    }

    /** Metoda służąca do zresetowania mapy */
    void restMap(){
        if(health>1){
            health -=1;
            loadData(levels.get(level - 1));
            physic.setStartData(xLander, yLander, readLands, readXPlanet, readYPlanet, this, gravity, meteorites, vYMax, aPlanet);
            drawPlanet.loadData(readXPlanet, readYPlanet, readPlanetColor, readLands);
            game.setHealth(health);
            game.setPoints(points);
            game.resetIterator();
            Control.setFuel(100);
            Game.setLevelText("#" + level);
            MyThread.resume();
            Game.endGame(false);
        }else {
            MyThread.pause();
            Game.endGame(true);
            Sound.stopMusic();
            saveResults();
        }
    }

    /** Metoda służąca do wczytania kolejnego poziomu */
    void nextLevel(){
        System.out.println("NextLevel");
        countPoints();
        if(health>=1){
            if(level < levels.size()) {
                System.out.println("Option1");
                loadData(levels.get(level));
                level++;
                physic.setStartData(xLander, yLander, readLands, readXPlanet, readYPlanet, this, gravity, meteorites, vYMax, aPlanet);
                drawPlanet.loadData(readXPlanet, readYPlanet, readPlanetColor, readLands);
                game.setHealth(health);
                game.setPoints(points);
                game.resetIterator();
                Control.setFuel(100);
                Game.setLevelText("#" + level);
                MyThread.resume();
            }else {
                System.out.println("Option2");
                saveResults();
                Game.endGame(true);
                Sound.stopMusic();
                MyThread.pause();
            }
        }else {
            Game.endGame(true);
            saveResults();
            Sound.stopMusic();
            MyThread.pause();
        }
    }

    /** Metoda służąca do wyboru metody załadowania danych*/
    private void loadData(String path){
        if (Client.Online()) {
            loadDataFromServer(path);
        }else {
            loadDataFromFile(path);
        }
    }

    /** Metoda służąca do załadownia danych mapy z serwera*/
    private void loadDataFromServer(String path) {
        String explosionPath;
        ArrayList<String> data = Client.getLevel(path);
        readYPlanet = LoadingLevel.getDataTable(data.get(1));
        readXPlanet = LoadingLevel.getDataTable(data.get(0));
        readPlanetColor = LoadingLevel.getDataTable(data.get(4));
        readLands = LoadingLevel.getDataTable(data.get(6));
        xLander = LoadingLevel.getDataTable(data.get(5))[0];
        yLander = LoadingLevel.getDataTable(data.get(5))[1];
        gravity = LoadingLevel.getDataInt(data.get(2));
        meteorites = LoadingLevel.getDataInt(data.get(3));
        vYMax = LoadingLevel.getDataInt(data.get(7));
        aPlanet = LoadingLevel.getDataDouble(data.get(8));

        //WCZYTYWANIE OBRAZKU STATKU
        lander = Client.getImage("lander.png");
        musicOFF = Client.getImage("musicOFF.png");
        musicON = Client.getImage("musicON.png");
        meteorPNG = Client.getImage("meteor.png");
        for(int i=0; i<26; i++) {
            explosionPath = "bum"+(i+1)+".png";
            explosion.add(Client.getImage(explosionPath));
        }
    }

    /** Metoda służąca do załadownia danych mapy z własnych danych*/
    private void loadDataFromFile(String path) {
        String explosionPath;
        try {
            LoadingLevel File = new LoadingLevel(new File("levelConfig/"+path+".txt"));
            readYPlanet = File.getElevation();
            readXPlanet = File.getPosition();
            readPlanetColor = File.getPlanetColor();
            xLander = File.getLanderStartPosition()[0];
            yLander = File.getLanderStartPosition()[1];
            readLands = File.getLands();
            gravity = File.getGravity();
            meteorites = File.getMeteorites();
            vYMax = File.getvYMax();
            aPlanet = File.getaPlanet();
        } catch (Exception e) {
            System.err.println("GameWindow loadDataFromFile " + e);
        }
        //WCZYTYWANIE OBRAZKU STATKU
        File landerImage = new File("img/lander.png");
        File musicONImage = new File("img/musicON.png");
        File musicOFFImage = new File("img/musicOFF.png");
        try {
            lander = ImageIO.read(landerImage);
            musicON = ImageIO.read(musicONImage);
            musicOFF = ImageIO.read(musicOFFImage);
        } catch (IOException e) {
            System.err.println("GameWindow loadDataFromFile " + e);
        }

        //WCZYTYWANIE OBRAZKU METEORU
        File meteorImage = new File("img/meteor.png");

        try {
            meteorPNG = ImageIO.read(meteorImage);
        } catch (IOException e) {
            System.err.println("GameWindow loadDataFromFile " + e);
        }


        //Wczytywanie klatek eksplozji
        for(int i=0; i<26; i++) {
            explosionPath = "img/bum"+(i+1)+".png";
            File explosionGif = new File(explosionPath);
            try {
                explosion.add(ImageIO.read(explosionGif));
            } catch (IOException e) {
                System.err.println("GameWindow loadDataFromFile " + e);
            }
        }
    }

    /** Metoda do odczytu wartości gravity */
    public static int getGravity(){
        return gravity;
    }

    /** Metoda do oblicznia wyniku punktowego */
    public void countPoints(){
        // Za ladowanie
        points += 100;
        //Za małe lotnisko
        points += pointForSmallLand;
        //Za niewykorzystane paliwo
        points += Control.getFuel()*10;
        //Mnożnik planety
        points = (int) (points*((double)gravity/3));
        //Zerowanie punktów za mniejsze ladowisko
        pointForSmallLand = 0;
    }

    /** Metoda do ustawiania punktacji lądowiska */
    public void setPointForSmallLand(){
        pointForSmallLand = 100;
    }

    /** Metoda do zapisu wyniku */
    public void saveResults(){
        try {
            if (!Client.Online()) {
                SaveResults.saveResult(Nick, points, DifficultLevel);
            }else {
                Client.setResultData(Nick, points, DifficultLevel);
            }
        } catch (Exception e) {
            System.err.println("GameWindow saveResult " + e);
        }
    }

    /** Metoda do odczytu ustawień muzyki*/
    public boolean musicSetting(){
        System.out.println(MusicSettings);
        if(MusicSettings == null || MusicSettings == "Włączona"){
            return  true;
        }
        else {
            return  false;
        }
    }

    /**Metoda do ustawienia danych nowej gry podczas powtarzania */
    public  void newGame(){
        health = 4;
        level = 1;
        points = 0;
        restMap();
    }
}
