/**
 * For working with 2D vectors.
 * @author Tushar
 */
public class Vector2D {
    private double x, y;
    public Vector2D(double x, double y) {
        this.x = x; this.y = y;
    }
    public Vector2D(Vector2D v) {
        this.x = v.x();
        this.y = v.y();
    }
    public double x() {
        return x;
    }
    public double y() {
        return y;
    }
    public void x(int x) {
        this.x = x;
    }
    public void y(int y) {
        this.y = y;
    }
    public double length() {
        return Math.sqrt(x*x + y*y);
    }
    public double lensq() {
        return x*x + y*y;
    }
    public void add(Vector2D v) {
        this.x += v.x();
        this.y += v.y();
    }
    public Vector2D plus(Vector2D v) {
        return new Vector2D(this.x + v.x(), this.y + v.y());
    }
    public void reverse() {
        this.x = -this.x;
        this.y = -this.y;
    }
    public Vector2D minus(Vector2D v) {
        return new Vector2D(this.x - v.x(), this.y - v.y());
    }
    public void mult(double scale) {
        this.x *= scale; this.y *= scale;
    }
    public Vector2D times(double scale) {
        return new Vector2D(this.x*scale, this.y*scale);
    }
    public Vector2D unit() {
        return new Vector2D(this.x/this.length(), this.y/this.length());
    }
}
