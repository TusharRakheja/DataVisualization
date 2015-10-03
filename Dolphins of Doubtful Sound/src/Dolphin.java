import java.awt.*;
import java.util.*;    
import java.io.*;

/**
 * Class to represent a dolphin, and methods to draw one on the canvas. 
 * @author <i> Tushar </i>
 */
public class Dolphin implements Comparable<Dolphin> {
    private static final double cX = 400.0, cY = 330.0;  // Coordinates of the canvas' center. 
    private static double currentAngle = 0;              // The first dolphin will be along theta = 0 degrees.
    private static final double increment = 5.8064;      // Each subsequent dolphin's angle in polar coordinates (degrees).
    private static double radius = 300.0;                // Radius in polar coordinates (set according to canvas).
    private static double delta = 2.0;                   // Controls separation between a dolphin node and its name (label).
    
    /** The alternate compare mechanism. Compares by angle. */
    private static final Comparator<Dolphin> By_Angle = new Compare();
    
    /** The class that implements the compare() method for the By_Angle comparator. */
    private static final class Compare implements Comparator<Dolphin> {
        @Override
        public int compare(Dolphin o1, Dolphin o2) {
            if (o1.theta < o2.theta) return -1;
            else if (o1.theta > o2.theta) return 1;
            else return 0;
        }
    }
   
    private int id;                                      // Signifies array index. Also used in edge connection.
    private String label;                                // The dolphin's name.
    private double x, y, theta;                          // Cartesian coordinates.
    private ArrayList<Dolphin> network;                  // The dolphins that this one communicated with.
    
    /**
     * This method is used to set the radius for the visualization.
     * 
     * @param r  the new radius of the peripheral ring for the dolphin graph.
     */
    public static void setRadius(double r) {
        Dolphin.radius = r;
    }
    
    /**
     * Adjusts the distance of the labeling text from the dolphin node.
     * 
     * @param delta  the new distance.
     */
    public static void setDelta(double delta) {
        Dolphin.delta = delta;
    }
    
    /**
     * Assigns id and label to a dolphin, and determines coordinates on canvas.
     * Also assigns currentAngle to theta and increments the former.
     * 
     * @param id  the ID of the dolphin.
     * @param label  the name of the dolphin.
     */
    public Dolphin(int id, String label) {
        this.id = id;
        this.label = label;
        this.theta = currentAngle;
        network = new ArrayList<>();
        this.setCoordinates();
        currentAngle += increment;
    }
    
    /**
     * This method determines the output coordinates of a dolphin based on the
     * currentAngle and radius.
     */
    private void setCoordinates() {
        x = cX + radius * Math.cos(theta * (Math.PI/180.0));
        y = cY + radius * Math.sin(theta * (Math.PI/180.0));
    }
    
    /**
     * The compareTo() method to compare this to another dolphin 'that'
     * Compares based on networkSize().
     * 
     * @param that  the other dolphin.
     * @return  -1 if this < that, 1 if this > that, and 0 otherwise.
     */
    @Override
    public int compareTo(Dolphin that) {
        if (this.sizeOfNetwork() < that.sizeOfNetwork()) return -1;
        else if (this.sizeOfNetwork() > that.sizeOfNetwork()) return 1;
        else return 0;
    }
    
    /**
     * Returns the x-coordinate of this dolphin.
     * @return 
     */
    public double x() {
        return x;
    }
    
    /**
     * Returns the y-coordinate of this dolphin.
     * @return 
     */
    public double y() {
        return y;
    }
    
    /**
     * Draws the dolphin (node) as a point on the canvas, along with the label.
     * 
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
     * 
     * @param rEdge  the radius of the pen when drawing the edge.
     */
    public void drawNetwork(double rEdge) {
        StdDraw.setPenRadius(rEdge);
        for (Dolphin that : this.network) {
            StdDraw.line(this.x, this.y, that.x(), that.y());
        }
    }
    
    /**
     * Draws the edges from this dolphin to all the other dolphins in the network.
     * 
     * @param rEdge  the radius of the pen when drawing the edge.
     * @param color  the color of the edges.
     */
    public void drawNetworkEdges(double rEdge, Color color) {
        StdDraw.setPenRadius(rEdge);
        StdDraw.setPenColor(color);
        for (Dolphin that : this.network) {
            StdDraw.line(this.x, this.y, that.x(), that.y());
        }
    }
    
    /**
     * Draws the dolphins in this dolphin's network.
     * 
     * @param rEdge
     * @param color 
     */
    public void drawNetworkNodes(double rEdge, Color color) {
        StdDraw.setPenRadius(rEdge);
        StdDraw.setPenColor(color);
        for (Dolphin that : this.network) {
            that.drawNodeOnly(rEdge, color);
        }
    }
    
    /**
     * Adds a new dolphin (that) to this one's network and vice versa. 
     * This and that communicated with each other.
     * 
     * @param that  the dolphin this communicated with. 
     */
    public void addToNetwork(Dolphin that) {
        if (!network.contains(that)) { 
            network.add(that);
            that.network.add(this); 
        }
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
     * 
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
     * 
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
     * 
     * @param x0  x coordinate of point 1.
     * @param y0  y coordinate of point 1.
     * @param x1  x coordinate of point 2.
     * @param y1  y coordinate of point 2.
     * @return 
     */
    public static double distSquared(double x0, double y0, double x1, double y1) {
        return (x0 - x1)*(x0 - x1) + (y0 - y1)*(y0 - y1);
    }
}