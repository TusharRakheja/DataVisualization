/**
 * Contains a class to represent a dolphin, and methods to draw one on the 
 * canvas. 
 * 
 * @author Tushar
 */

import java.awt.*;
import java.util.*;         
import java.net.*;
import java.io.*;

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
     * @return  the number other dolphins in this one's network (network size).
     */
    public int sizeOfNetwork() {
        return network.size();
    }
    
    /**
     * Reads the labels for each dolphin and the edges from a file, and prints
     * the entire graph to the canvas.
     * 
     * @param args  conventional argument
     * @throws IOException  if URL is inaccessible.
     */
    public static void main(String[] args) throws IOException {
        Dolphin.setRadius(270);
        Dolphin.setDelta(42);
        Font f = new Font("SansSerif", Font.PLAIN, 13);
        StdDraw.setCanvasSize(800, 670);
        StdDraw.setXscale(0, 800);
        StdDraw.setYscale(0, 670);
        StdDraw.setFont(f);
        URL url = new URL("http://networkdata.ics.uci.edu/data/dolphins/dolphins.gml");
        Scanner read = new Scanner(url.openStream());
        Dolphin[] dolphins = new Dolphin[62];
        String line;
        String[] temp;
        int dolph1, dolph2;                        // Used while reading edges.
        for (int i = 0; i < 7; i++) {                // Get to the first label.
            read.nextLine();                            // Due to input format.
        }                                // We're now going to read the labels.
        for (int i = 0; i < 62; i++) {
            line = read.nextLine();
            temp = line.split("\"");                 // The label is in quotes.
            temp = temp[1].split("\"");
            line = temp[0];
            dolphins[i] = new Dolphin(i, line);
            read.nextLine();                          // Get to the next label.
            if (i != 61) {                              // Due to input format.  
                read.nextLine(); read.nextLine();
                read.nextLine();
            }
        }                                 // We're now going to read the edges.
        while(read.hasNext()) {                       
            read.nextLine(); 
            if (read.hasNext()) read.nextLine();        // Due to input format.
            else break;
            line = read.nextLine();
            line = line.split("source ")[1];
            dolph1 = Integer.parseInt(line);
            line = read.nextLine();
            line = line.split("target ")[1];
            dolph2 = Integer.parseInt(line);
            dolphins[dolph1].addToNetwork(dolphins[dolph2]);
            read.nextLine();   // Get to the next edge.
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < 62; i++) {
            dolphins[i].drawNode(0.01, 0.01);
        }
        Color edgeColor = new Color(51, 102, 255);
        StdDraw.setPenColor(edgeColor);
        for (int i = 0; i < 62; i++) {
            dolphins[i].drawNetwork(0.0020);
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < 62; i++) {
            dolphins[i].drawNode(0.01, 0.01);
        }
        double tolerance = 65;
        while (true) {
            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            for (int i = 0; i < 62; i++) {
                if (distSquared(dolphins[i].x(), dolphins[i].y(), x, y) <= tolerance) {
                    dolphins[i].drawNetworkEdges(0.0020, StdDraw.RED);
                    dolphins[i].drawNodeOnly(0.01, StdDraw.RED);
                    dolphins[i].drawNetworkNodes(0.01, StdDraw.RED);
                    while (distSquared(dolphins[i].x(), dolphins[i].y(), x, y) <= tolerance) {
                        x = StdDraw.mouseX();
                        y = StdDraw.mouseY();
                    }
                    dolphins[i].drawNetworkEdges(0.0020, StdDraw.WHITE);  // Doing this
                    dolphins[i].drawNetworkEdges(0.0020, StdDraw.WHITE);  // twice will
                    dolphins[i].drawNetworkEdges(0.0020, edgeColor);      // delay the 
                    dolphins[i].drawNetworkEdges(0.0020, edgeColor);      // purple shade.
                    dolphins[i].drawNodeOnly(0.01, StdDraw.BLACK);
                    dolphins[i].drawNetworkNodes(0.01, StdDraw.BLACK);
                }
            }
        }
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