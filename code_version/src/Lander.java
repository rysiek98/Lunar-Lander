import java.awt.geom.Rectangle2D;

/** Klasa obiektu lądownik do obliczania zderzeń */
public class Lander extends Rectangle2D {

    private double x, y, width, height;

    /** Konstruktor kalsy
     * @param x współrzędna X
     * @param y współrzędna Y
     * @param width szerokość lądownika
     * @param height wysokość lądownika
     */
    Lander(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
}
