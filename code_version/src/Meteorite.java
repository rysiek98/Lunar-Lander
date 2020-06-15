import java.awt.geom.Rectangle2D;
import java.util.Random;

/** Klasa obiektów meteoryt do obliczania zderzeń */
public class Meteorite extends Rectangle2D {

    private double x, y, width, height;
    private int iterator;
    private int startTime;
    private final int oxMove;
    private final int ID, LanderX;
    private boolean alive;

    /** Konstruktor klasy
     * @param y położenie meteorytu w osi OY
     * @param width szerokość meteorytu
     * @param height wysokość meteorytu
     * @param sTime czas startu
     * @param gameWindowWidth szerokość okna
     * @param IDIN numer meteorytu
     * @param live boolean mówiący czy meteoryt istnieje
     * @param xLander położenie lądownika w osi OX
     */
    Meteorite(int y, int width, int height, int sTime, int gameWindowWidth, int IDIN, boolean live, int xLander){
        this.x = randXMeteorPosition();
        this.y = y;
        this.width = width;
        this.height = height;
        iterator = 0;
        startTime = sTime;
        oxMove = randX(gameWindowWidth);
        ID = IDIN;
        alive = live;
        LanderX = xLander;
    }

    /**Metoda do zmiany wartości x */
    public void setX(int x){
        this.x = x;
    }

    /**Metoda do zmiany wartości y */
    public void setY(int y){
        this.y = y;
    }

    /**Metoda do zmiany wartości width */
    public void setWidth(int width){
        this.width = width;
    }

    /**Metoda do zmiany wartości height */
    public void setHeight(int height){
        this.height = height;
    }

    /**Metoda do odczytu wartości x */
    public double getX(){
        return this.x;
    }

    /**Metoda do odczytu wartości y */
    public double getY(){
        return this.y;
    }

    /**Metoda do odczytu wartości width */
    public double getWidth(){
        return this.width;
    }

    /**Metoda do odczytu wartości height */
    public double getHeight(){
        return this.height;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void setRect(double x, double y, double w, double h) {}

    @Override
    public int outcode(double x, double y) {
        return 0;
    }

    @Override
    public Rectangle2D createIntersection(Rectangle2D r) {
        return null;
    }

    @Override
    public Rectangle2D createUnion(Rectangle2D r) {
        return null;
    }

    //ATRYBUT ITERATOR OKRESLA "CZAS" OCZEKIWANIA OBIEKTU NA START, ODMIERZA GO
    /**Metoda do odczytu wartości iterator */
    public int getIterator(){
        return iterator;
    }

    /**Metoda do ustawiania wartości iterator */
    public void setIterator(int x){
        iterator =x;
    }

    //ATRYBUT OKRESLA MOMENT STARTU METEORYTU, WSPÓLPRACUJE Z ATRYBUTEM ITERATOR
    /**Metoda do odczytu wartości startTime */
    public int getStartTime(){
        return startTime;
    }

    /**Metoda do ustawiania wartości startTime */
    public void setStartTime(int x){
        startTime =x;
    }

    //METODA LOSUJE RUCH METEORYTU W OSI OX
    public int randX(int width){
        Random R = new Random();
        if(x<width*0.35) {
           return R.nextInt(5);
        }
        else if(x>width*0.7){
           return  -R.nextInt(5);
        }else {
           return R.nextInt(9)-4;
        }
    }
    //METODA ZWRACA WYLOSOWANY RUCH W OSI OX
    /**Metoda do odczytu wartości oxMove */
    public int getOxMove(){
        return oxMove;
    }

    /**Metoda do odczytu wartości ID */
    public int getID(){
        return ID;
    }

    /**Metoda do ustawienia wartości startTime */
    public void setAlive(boolean x){
        alive = x;
    }

    /**Metoda do odczytu wartości alive */
    public boolean getAlive(){
        return  alive;
    }

    /**Metoda do losowania położenia meteorytu */
    private int randXMeteorPosition(){
        Random R = new Random();
        boolean flag=true;
        int x=0;
        while (flag) {
            x = R.nextInt(1050) + 50;
            if(x> LanderX+100 || x< LanderX-100){
                flag=false;
            }
        }
        return x;
    }

    /**Metoda do sprawdzenia gotowości meteorytów
     * @return Boolean gotowości do startu
     * */
    public boolean ready(){
        if(iterator > startTime){
            return true;
        }else {
            return false;
        }
    }
}