import java.awt.*;
import java.util.*;
import java.io.*;

/**
 * Class to represent a dolphin, and methods to draw one on the canvas.
 * @author <i> Tushar </i>
 */
public class Dolphin implements Comparable<Dolphin>, Iterable<Integer> {
    private static final double cX = 400.0, cY = 335.0;  // Coordinates of the canvas' center.
    private static double currentAngle = 0;              // The first dolphin will be along theta = 0 degrees.
    private static final double increment = 5.8064;      // Each subsequent dolphin's angle in polar coordinates (degrees).
    private static double radius = 300.0;                // Radius in polar coordinates (set according to canvas).
    private static double delta = 2.0;                   // Controls separation between a dolphin node and its name (label).


    private int id;                                      // Signifies array index (unsorted). Also used in edge connection.
    private String label;                                // The dolphin's name.
    private double x, y, theta;                          // Cartesian coordinates.
    private LinkedList<Integer> network;                 // The dolphins that this one communicated with (by ID).
    private int mass;                                    // Mass = Network.size()
    private Vector2D accel;                              // Acceleration for force-direction.
    private Vector2D velocity;                           // Velocity for force direction.
    private Vector2D position;
    private int massIncrement;
    /**
     * This method is used to set the radius for the visualization.
     * @param r  the new radius of the peripheral ring for the dolphin graph.
     */
    public static void setRadius(double r) {
        Dolphin.radius = r;
    }
    /**
     * Adjusts the distance of the labeling text from the dolphin node.
     * @param delta  the new distance.
     */
    public static void setDelta(double delta) {
        Dolphin.delta = delta;
    }
    /**
     * Assigns id and label to a dolphin, and determines coordinates on canvas.
     * Also assigns currentAngle to theta and increments the former.
     * @param id  the ID of the dolphin.
     * @param label  the name of the dolphin.
     */
    public Dolphin(int id, String label) {
        this.id = id;
        this.label = label;
        network = new LinkedList<>();
        mass = 1;
        velocity = new Vector2D(0, 0);
        accel = new Vector2D(0, 0);
        massIncrement = 2;
    }
    /**
     * Sets the angle for the current dolphin.
     * @param dolphins the set of all dolphins.
     */
    public static void setAngle(Dolphin[] dolphins) {
        for (Dolphin dolphin : dolphins) {
            dolphin.theta = currentAngle;
            dolphin.setCoordinates();
            currentAngle += increment;
        }
    }
    /**
     * This method determines the output coordinates of a dolphin based on the
     * currentAngle and radius.
     */
    private void setCoordinates() {
        x = cX + radius * Math.cos(theta * (Math.PI/180.0));
        y = cY + radius * Math.sin(theta * (Math.PI/180.0));
        position = new Vector2D(x, y);
    }
    private void setRandomCoordinates() {
        double x = Math.random() * 801;
        double y = Math.random() * 671;
        this.position(new Vector2D(x, y));
    }
    public static void setRandom(Dolphin[] dolphins) {
        for (Dolphin d : dolphins) {
            d.setRandomCoordinates();
        }
    }
    /**
     * The compareTo() method to compare this to another dolphin 'that'
     * Compares based on networkSize().
     * @param that The other dolphin.
     * @return -1 if this < that, 1 if this > that, and 0 otherwise.
     */
    @Override
    public int compareTo(Dolphin that) {
        if (this.sizeOfNetwork() < that.sizeOfNetwork()) {
            return -1;
        }
        else if (this.sizeOfNetwork() > that.sizeOfNetwork()) {
            return 1;
        }
        else return 0;
    }
    /**
     * Returns the x-coordinate of this dolphin.
     * @return The x-coordinate of this dolphin.
     */
    public double x() {
        return position.x();
    }
    /**
     * Returns the y-coordinate of this dolphin.
     * @return The y-coordinate of this dolphin.
     */
    public double y() {
        return position.y();
    }
    /**
     * Returns the mass (network size) of the dolphin.
     * @return The mass of the dolphin.
     */
    public double mass() {
        return mass;
    }
    /**
     * Draws the dolphin (node) as a point on the canvas, along with the label.
     * @param rDolphin  the radius of the pen when drawing the dolphin node.
     * @param rLabel  the radius of the pen when labeling the node.
     */
    public void drawNode(double rDolphin, double rLabel) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(rDolphin + sizeOfNetwork()/650.0);  // The more the connections,
        StdDraw.point(x, y);                                     // the bigger the node.
        StdDraw.setPenRadius(rLabel);
        double xText = cX + (radius + delta)* Math.cos(theta * (Math.PI/180.0));
        double yText = cY + (radius + delta)* Math.sin(theta * (Math.PI/180.0));
        if (theta <= 90 || theta >= 270)
            StdDraw.text(xText, yText, label, theta);
        else
            StdDraw.text(xText, yText, label, 180 + theta);
    }
    public void drawNode2(double rDolphin, double rLabel) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(rDolphin + sizeOfNetwork()/650.0);  // The more the connections,
        StdDraw.point(position.x(), position.y());                                     // the bigger the node.
//        StdDraw.setPenRadius(rLabel);
//        double xText = cX + (radius + delta)* Math.cos(theta * (Math.PI/180.0));
//        double yText = cY + (radius + delta)* Math.sin(theta * (Math.PI/180.0));
//        if (theta <= 90 || theta >= 270)
//            StdDraw.text(xText, yText, label, theta);
//        else
//            StdDraw.text(xText, yText, label, 180 + theta);
    }
    public void eraseNode2(double rDolphin, double rLabel) {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setPenRadius(rDolphin + sizeOfNetwork()/300);  // The more the connections,
        StdDraw.point(position.x(), position.y());                                     // the bigger the node.
    }
    /**
     * Draws the dolphin (node).
     * @param rDolphin
     */
    public void drawNodeOnly(double rDolphin, Color color) {
        StdDraw.setPenColor(color);
        StdDraw.setPenRadius(rDolphin + sizeOfNetwork()/650.0);
        StdDraw.point(x, y);
    }
    /**
     * Draws the edges from this dolphin to all the other dolphins in the network.
     * @param rEdge the radius of the pen when drawing the edge.
     * @param dolphins the set of all dolphins (array).
     */
    public void drawNetwork(double rEdge, Dolphin[] dolphins) {
        StdDraw.setPenRadius(rEdge);
        for (Dolphin dolphin : dolphins)
            if (this.network.contains(dolphin.id()))
                StdDraw.line(this.x, this.y, dolphin.x(), dolphin.y());
    }
    /**
     * Draws the edges from this dolphin to all the other dolphins in the network.
     * @param rEdge  the radius of the pen when drawing the edge.
     * @param color  the color of the edges.
     * @param dolphins the set of all dolphins (array).
     */
    public void drawNetworkEdges(double rEdge, Color color, Dolphin[] dolphins) {
        StdDraw.setPenRadius(rEdge);
        StdDraw.setPenColor(color);
        for (Dolphin dolphin : dolphins)
            if (this.network.contains(dolphin.id()))
                 StdDraw.line(this.x, this.y, dolphin.x(), dolphin.y());
    }
    /**
     * Draws the dolphins in this dolphin's network.
     * @param rEdge
     * @param color
     * @param dolphins the set of all dolphins (array).
     */
    public void drawNetworkNodes(double rEdge, Color color, Dolphin[] dolphins) {
        StdDraw.setPenRadius(rEdge);
        StdDraw.setPenColor(color);
        for (Dolphin dolphin : dolphins)
            if (this.network.contains(dolphin.id()))
                dolphin.drawNodeOnly(rEdge, color);
    }
    /**
     * Adds a new dolphin (that) to this one's network and vice versa.
     * This and that communicated with each other.
     *
     * @param that  the dolphin this communicated with.
     */
    public void addToNetwork(Dolphin that) {
        if (!network.contains(that.id())) {
            network.add(that.id());
            that.network.add(this.id());
            mass += massIncrement;
            massIncrement += 2;
            that.mass += that.massIncrement;
            that.massIncrement += 2;
        }
    }
    /**
     * Returns the id of the dolphin.
     * @return
     */
    public int id() {
        return this.id;
    }
    /**
     * Returns the size of this dolphin's network.
     *
     * @return  the number other dolphins in this one's network (network size).
     */
    public int sizeOfNetwork() {
        return network.size();
    }
    /**
     * Returns the average network size.
     * @param dolphins  the array containing the dolphin nodes.
     * @return  the mean of all the network sizes.
     */
    public static double meanNetworkSize(Dolphin[] dolphins) {
        double mean = 0;
        for (int i = 0; i < 62; i++) mean += dolphins[i].sizeOfNetwork();
        return mean / 62.0;
    }
    /**
     * Writes the network sizes their frequencies in a file (in ascending order).
     * @param dolphins  the array containing the dolphin nodes.
     * @param filename  the filename.
     * @throws IOException
     */
    public static void writeStatsToFile(Dolphin[] dolphins, String filename) throws IOException {
        Dolphin[] dolphins2 = new Dolphin[62];
        System.arraycopy(dolphins, 0, dolphins2, 0, 62);
        Arrays.sort(dolphins2);
        File output = new File(filename);
        FileWriter o = new FileWriter(output);
        PrintWriter out = new PrintWriter(o);
        int count = 0; int size = dolphins2[0].sizeOfNetwork();
        for (int i = 0; i < 62; i++) {
            if (dolphins2[i].sizeOfNetwork() == size) {
                count++;
            }
            else {
                out.println(size + "\t" + count);
                count = 1;
                size = dolphins[i].sizeOfNetwork();
            }
        }
        out.close();
    }
    /**
     * Just a small method to return the squaredDistance b/w two points.
     * @param x0  x coordinate of point 1.
     * @param y0  y coordinate of point 1.
     * @param x1  x coordinate of point 2.
     * @param y1  y coordinate of point 2.
     * @return The squared distance b/w two points.
     */
    public static double distSquared(double x0, double y0, double x1, double y1) {
        return (x0 - x1)*(x0 - x1) + (y0 - y1)*(y0 - y1);
    }
    /**
     * The overridden iterator() method.
     * @param d1 The dolphin d1.
     * @param d2 The dolphin d2.
     * @return An iterator for this dolphin's network.
     */
    public static double distSquared(Dolphin d1, Dolphin d2) {
        return distSquared(d1.x(), d1.y(), d2.x(), d2.y());
    }
    public static double dist(Dolphin d1, Dolphin d2) {
        return Math.sqrt((d1.x() - d2.x())*(d1.x() - d2.x()) + (d1.y() - d2.y())*(d1.y() - d2.y()));
    }
    @Override
    public Iterator<Integer> iterator() {
        return network.iterator();
    }
    /**
     * @return The velocity of the dolphin.
     */
    public Vector2D velocity() {
        return velocity;
    }
    /**
     * Quite the setter method for velocity.
     * @param velocity The updated velocity.
     */
    public void velocity(Vector2D velocity) {
        this.velocity = velocity;
    }
    /**
     * @return The acceleration of the dolphin.
     */
    public Vector2D accel() {
        return accel;
    }
    /**
     * Quite the setter method for acceleration as well!
     * @param accel The updated acceleration.
     */
    public void accel(Vector2D accel) {
        this.accel = accel;
    }
    /**
     * Updates position.
     * @param position The updated position.
     */
    public void position(Vector2D position) {
        this.position = position;
    }
    /**
     * @return The position of the dolphin.
     */
    public Vector2D position() {
        return position;
    }

    public void adjustPosition(double stepSize) {
        this.position.add(this.velocity.times(stepSize));
    }

    public void adjustVelocity(double stepSize) {
        this.velocity.add(this.accel.times(stepSize));
    }

    public void adjust(double stepSize) {
        adjustVelocity(stepSize);
        adjustPosition(stepSize);
    }
}
