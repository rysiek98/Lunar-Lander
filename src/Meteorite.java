import java.awt.geom.Rectangle2D;
import java.util.Random;

/** OBIEKTY TEJ KLASY SĄ MODELAMI ZDERZENIOWYMI METEORYTÓW */
public class Meteorite extends Rectangle2D {

    private double x, y, width, height;
    private int iterator;
    private int startTime;
    private final int oxMove;
    private final int ID, LanderX;
    private boolean alive;

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

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }
    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public double getWidth(){
        return this.width;
    }

    public double getHeight(){
        return this.height;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void setRect(double x, double y, double w, double h) {

    }

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
    public int getIterator(){
        return iterator;
    }
    public void setIterator(int x){
        iterator =x;
    }

    //ATRYBUT OKRESLA MOMENT STARTU METEORYTU, WSPÓLPRACUJE Z ATRYBUTEM ITERATOR
    public int getStartTime(){
        return startTime;
    }
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
    public int getOxMove(){
        return oxMove;
    }

    public int getID(){
        return ID;
    }

    public void setAlive(boolean x){
        alive = x;
    }

    public boolean getAlive(){
        return  alive;
    }


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

    public boolean ready(){
        if(iterator > startTime){
            return true;
        }else {
            return false;
        }
    }

}