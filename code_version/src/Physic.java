import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/** Klasa odpowiadająca za realizację fizyki w grze */
public class Physic {

    private int x, y, landerY, landerX, metoriteID;
    private double velocityX, velocityY;
    private static boolean landerMove;
    private static boolean meteoriteMove;
    private int[] readLands;
    private int[] readX;
    private int[] readY;
    private final ArrayList<Rectangle> lands = new ArrayList<>();
    private final ArrayList<Meteorite> meteors = new ArrayList<>();
    private Polygon Planet;
    private final Lander Lander;
    private GameWindow gameWindow;
    private double gravity;
    private int meteorite, vYMax;
    private double aPlanet;

    /** Konstruktor klasy
     * @param xLander początkowa współrzędna X lądownika
     * @param yLander początkowa współrzędna Y lądownika
     * @param readLandsIN współrzędne lądnowisk
     * @param readXIN wartości X planety
     * @param readYIN wartości y planety
     * @param Game obiekt obsłógujący grę
     * @param gravityIn wartość grawitacji planety
     * @param meteoriteIN ilość meteorytów planety
     * @param vYMaxIN maksymalna prędkość planety
     * @param aPlanetIN maksymalne przyspieszenie planety
     */
    Physic(int xLander, int yLander, int[] readLandsIN, int[] readXIN, int[] readYIN, GameWindow Game, double gravityIn, int meteoriteIN, int vYMaxIN, double aPlanetIN){
        readLands = readLandsIN;
        readX = readXIN;
        readY = readYIN;
        Lander = new Lander(xLander,yLander,20,20);
        x = xLander;
        y = yLander;
        landerY = yLander;
        landerX = xLander;
        gameWindow = Game;
        gravity = gravityIn;
        meteorite = meteoriteIN;
        velocityX = 0;
        velocityY = 0;
        vYMax = vYMaxIN;
        aPlanet = aPlanetIN;
        metoriteID = 0;
        meteoriteMove = false;
        landerMove = false;

        //TWORZE TABLICE Z LADOWISKAMI (MODEL ZDERZENIOWY)
        for(int i=0; i<readLands.length; i+=3) {
            lands.add(new Rectangle(readLands[i], readLands[i+1], readLands[i+2], 10));
        }

        //TWORZE TABLICE Z METEORYTAMI, LOSUJE IM CZAS STARTU I MIEJSCE STARTU NA OSI OX MAPY (MODEL ZDERZENIOWY)
        prepareMeteorite();

        //TWORZE MODEL ZDERZENIOWY PLANETY
        Planet = new Polygon(readX, readY, readY.length);

    }


    /** Metoda odpowiedzialna za wykrycie kolizji statek-planeta */
    public void colision(){
        if(Planet.intersects(Lander) && Control.getActivGame()){
            System.out.println("Kolizja! Statek uderzyl w planete!");
            landerMove = false;
            meteoriteMove = false;
            Control.changeActivGame(false);
            MyThread.pause2();
            gameWindow.restMap();
        }
    }

    /** Metoda odpowiedzialna za wykrycie pomyślenego lądowania */
    public void landing(){
        for(int i=0; i<lands.size(); i++) {
            if (lands.get(i).intersects(Lander) && Control.getActivGame()) {
                Control.changeActivGame(false);
                if(lands.get(i).width == 50){
                    gameWindow.setPointForSmallLand();
                }
                System.out.println("Brawo! Ladowanie zakonczone sukcesem!");
                landerMove = false;
                meteoriteMove = false;
                MyThread.pause2();
                gameWindow.nextLevel();
            }
        }
    }


    /** Metoda odpowiedzialna za ruch statku w osi OY
     * @return współrzędna Y statku (Integer)
     */
    public int shipPositionY(int move_up){

        if(landerMove) {
            if (move_up == 0) {
                if (y < 700) {
                    //SPRAWDZAM CZY STATEK NIE PORUSZA SIE SZYBCIEJ NIZ VYMAXPLANETY
                    if(velocityY < vYMax) {
                        //TRAKTUJE PIXEL JAKO 1 METR, STAD SKORO RYSUJE 33 RAZY NA SEKUNDE TO GRAWITACJE DZIELE NA 30(ZAOKRAGLENIE) BY UZYSKAC PRZYSPIESZENIE W M/S
                        velocityY += gravity / 30;
                    }
                }
            } else {
                if (y > 10) {
                    if(velocityY > - 3) velocityY += move_up;
                }
            }
            y += velocityY;
        }

        Lander.setY(y);
        //TU ODLICZAM "CZAS" DO STARTU DANEGO METEORYTU
        if(meteorite > 0 && meteoriteMove) {
            ArrayList<Meteorite> tmp= new ArrayList<Meteorite>();
            for(int i=0; i<meteors.size(); i++) {
                meteors.get(i).setIterator(meteors.get(i).getIterator() + 1);

                if (meteors.get(i).getIterator() == -1){
                    tmp.add(meteors.get(i));
                }
            }

            for (int i=0; i<tmp.size(); i++) {
                meteors.remove(tmp.get(i));
                meteorite--;
            }

        }

        activControl();
        return y;
    }

    /** Metoda odpowiedzialna za ruch statku w osi OX
     * @return współrzędna X statku (Integer)
     */
    public int shipPositionX(int move_left, int move_right)
    {
        if(landerMove && Control.getActivGame()) {
            if (move_left != 0 || move_right != 0) {
                //MAX PREDKOSC W OSI OX (TAKA SAM NA KAZDEJ PLANECIE
                if (velocityX >= -4) {
                    if (x > 5) {
                        velocityX += move_left;
                    }
                }
                if (velocityX <= 4) {
                    if (x < 1248) {
                        velocityX += move_right;
                    }
                }
            } else {
                //WYTRACANIE PREDKOSCI PRZEZ STATKE W OX
                if (velocityX > 0) {
                    velocityX += -aPlanet;
                    if (velocityX < 0) {
                        velocityX = 0;
                    }
                }
                if (velocityX < 0) {
                    velocityX += aPlanet;
                    if (velocityX > 0) {
                        velocityX = 0;
                    }
                }

            }
            if (x > 5 && x < 1248) {
                x += velocityX;
            }
        }
        Lander.setX(x);
        return x;
    }

    /** Metoda odpowiedzialna za ustawienie poprawnych danych do nowego poziomu
     * @param xLander początkowa współrzędna X lądownika
     * @param yLander początkowa współrzędna Y lądownika
     * @param readLandsIN współrzędne lądnowisk
     * @param readXIN wartości X planety
     * @param readYIN wartości y planety
     * @param Game obiekt obsłógujący grę
     * @param gravityIn wartość grawitacji planety
     * @param meteoriteIN ilość meteorytów planety
     * @param vYMaxIN maksymalna prędkość planety
     * @param aPlanetIN maksymalne przyspieszenie planety
     */
    public void setStartData(int xLander, int yLander, int[] readLandsIN, int[] readXIN, int[] readYIN, GameWindow Game, double gravityIn, int meteoriteIN, int vYMaxIN, double aPlanetIN){
        setDefaultData();
        readLands = readLandsIN;
        readX = readXIN;
        readY = readYIN;
        Lander.setY(yLander);
        Lander.setX(xLander);
        x = xLander;
        y = yLander;
        landerY = yLander;
        landerX = xLander;
        gameWindow = Game;
        gravity = gravityIn;
        meteorite = meteoriteIN;
        velocityX = 0;
        velocityY = 0;
        vYMax = vYMaxIN;
        aPlanet = aPlanetIN;
        landerMove = false;
        meteoriteMove = false;
        for(int i=0; i<readLands.length; i+=3) {
            lands.add(new Rectangle(readLands[i], readLands[i+1],readLands[i+2], 10));
        }
        prepareMeteorite();
        Planet = new Polygon(readX, readY, readY.length);

    }

    /** Metoda do ustawiania domyślnych wartości dla poziomu */
    public void setDefaultData(){
        readLands = null;
        readX = null;
        readY = null;
        Lander.setY(0);
        Lander.setX(0);
        x = 0;
        y = 0;
        gameWindow = null;
        landerMove = false;
        meteoriteMove = false;
        gravity = 0;
        meteorite = 0;
        lands.clear();
        meteors.clear();
        Planet = null;
        velocityX = 0;
        velocityY = 0;
        vYMax = 0;
        aPlanet = 0;
    }

    /** Metoda odpowiedzialna za wykrycie kolizji meteoryt-statek i statek-planeta */
    public void meteoriteColision(){
        if(meteorite>0) {
            for(int i=0; i<meteors.size(); i++) {
                if (Planet.intersects(meteors.get(i))) {
                    if(meteors.get(i).getAlive()) {
                        System.out.println("Meteoryt uderzyl w planete!");
                        meteors.get(i).setIterator(-27);
                        meteors.get(i).setAlive(false);
                    }

                } else if (meteors.get(i).intersects(Lander) && Control.getActivGame() && meteors.get(i).ready()) {
                    if(meteors.get(i).getAlive()) {
                        meteors.get(i).setIterator(-27);
                        meteors.get(i).setAlive(false);
                    }
                    Control.changeActivGame(false);
                    System.out.println("Meteoryt zniszczyl statek!");
                    meteoriteMove = false;
                    landerMove = false;
                    MyThread.pause2();
                    gameWindow.restMap();
                }
            }
            someMeteorColision();
        }
    }

    /** Metoda rozpoczynająca rysowanie meteorytów */
    public boolean startMeteo(){
        for(int i=0; i<meteors.size(); i++) {
            if (meteors.get(i).ready() || meteors.get(i).getIterator() < -1) {
                return true;
            }
        }
        return false;
    }

    /** Metoda obliczająca ruch meteorytów w osi OX */
    public void runMeteoX(){

        for(int i=0; i<meteors.size(); i++) {
            if(meteors.get(i).ready() && meteoriteMove && meteors.get(i).getAlive()){
                meteors.get(i).setX((int) meteors.get(i).getX() +  meteors.get(i).getOxMove());
            }
        }

    }

    /** Metoda obliczająca ruch meteorytów w osi OY */
    public void runMeteoY(){

        for(int i=0; i<meteors.size(); i++) {
            if(meteors.get(i).ready() && meteoriteMove && meteors.get(i).getAlive()){
                meteors.get(i).setY((int) meteors.get(i).getY() + 4);
            }
        }
    }

    /** Metoda obliczająca gotowość meteorytów do startu */
    public int readyMeteor(){
       int ready = 0;
        for(int i=0; i<meteors.size(); i++) {
            if (meteors.get(i).ready()) {
                ready++;
            }
            if (meteors.get(i).getIterator() < -1){
                ready++;
            }
        }
        return ready;
    }

    /** Metoda usuwająca niepotrzebne meteoryty */
    public void someMeteorColision() {
        Meteorite tmp = null;
        if (meteorite > 0) {
            for (int i = 0; i < meteors.size(); i++) {
                for (int j = 0; j < meteors.size(); j++) {
                    if (i != j && meteors.get(i).intersects(meteors.get(j)) && meteors.get(i).ready() && meteors.get(j).ready()) {
                        tmp = meteors.get(i);
                        meteors.remove(meteors.get(j));
                        meteorite--;
                    }
                }
                if(tmp != null) {
                    if(tmp.getAlive()) {
                        tmp.setIterator(-27);
                        tmp.setAlive(false);
                    }
                }
            }

        }
    }

    /** Metoda przygotowująca meteoryty do startu*/
    public void prepareMeteorite(){
        if(meteorite>0) {
            metoriteID++;
            Random R = new Random();
            meteors.add(new Meteorite(1, 20, 20, R.nextInt(10)+10, gameWindow.getWidth(), metoriteID, true, landerX));

            for (int i = 1; i < meteorite; i++) {
                int tmp = R.nextInt(70) + meteors.get(i - 1).getStartTime();
                metoriteID++;
                meteors.add(new Meteorite(1, 20, 20, tmp, gameWindow.getWidth(), metoriteID, true, landerX));
            }
        }
    }

    /** Metoda zapobiegająca zużyciu paliwa podczas pauzy*/
    public void activControl(){
        if(y>landerY+1 && y<landerY+4){
            Control.changeActivGame(true);
        }
    }

    /** Metoda przygotowująca odpowiednią liczbę meteorytów
     * @return tablica obiektów Meteorite
     * */
    public Meteorite[] getMeteors(){
        Meteorite[] tmp = new Meteorite[readyMeteor()];
        runMeteoY();
        runMeteoX();
        int j=0;
        for(int i=0; i<meteors.size(); i++) {
            if(meteors.get(i).ready()){
                tmp[j]=meteors.get(i);
                j++;
            }
            if(meteors.get(i).getIterator() < -1){
                tmp[j]=meteors.get(i);
                j++;
            }
        }
        return tmp;
    }

    /** Metoda pozwalająca na ruch lądownika
     * @param x pozwolenie na ruch (Boolean)
     */
    public  static void setLanderMove(boolean x){
        landerMove = x;
    }

    /** Metoda pozwalająca na ruch meteorów
     * @param x pozwolenie na ruch (Boolean)
     * */
    public static void setMeteoriteMove(boolean x){
        meteoriteMove = x;
    }

}
