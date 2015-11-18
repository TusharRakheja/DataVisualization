import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.Arrays;
/**
 * Class to make the ring-shaped graphs of the data. 
 * @author <i> Tushar </i>
 */
public class Ring {
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
        Arrays.sort(dolphins);
        Dolphin.setAngle(dolphins);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < 62; i++) {
            dolphins[i].drawNode(0.01, 0.01);
        }
        Color edgeColor = new Color(51, 102, 255);
        StdDraw.setPenColor(edgeColor);
        for (int i = 0; i < 62; i++) {
            dolphins[i].drawNetwork(0.0020, dolphins);
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < 62; i++) {
            dolphins[i].drawNodeOnly(0.01, StdDraw.BLACK);
        }
        //writeStatsToFile(dolphins, "Output.txt");
        StdDraw.setPenRadius(0.002);
        StdDraw.rectangle(60, 652, 50, 10);
        double tolerance = 65;
        while (true) {
            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            for (int i = 0; i < 62; i++) {
                if (Dolphin.distSquared(dolphins[i].x(), dolphins[i].y(), x, y) <= tolerance) {
                    dolphins[i].drawNetworkEdges(0.0020, StdDraw.RED, dolphins);
                    dolphins[i].drawNodeOnly(0.01, StdDraw.RED);
                    dolphins[i].drawNetworkNodes(0.01, StdDraw.RED, dolphins);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    String current = "Connections: " + dolphins[i].sizeOfNetwork();
                    StdDraw.text(60, 650, current);
                    while (Dolphin.distSquared(dolphins[i].x(), dolphins[i].y(), x, y) <= tolerance) {
                        x = StdDraw.mouseX();
                        y = StdDraw.mouseY();
                    }
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledRectangle(60, 650, 49, 7);
                    dolphins[i].drawNetworkEdges(0.0020, StdDraw.WHITE, dolphins);  // Doing this
                    dolphins[i].drawNetworkEdges(0.0020, StdDraw.WHITE, dolphins);  // twice will
                    dolphins[i].drawNetworkEdges(0.0020, edgeColor, dolphins);      // delay the 
                    dolphins[i].drawNetworkEdges(0.0020, edgeColor, dolphins);      // purple shade.
                    dolphins[i].drawNodeOnly(0.01, StdDraw.BLACK);
                    dolphins[i].drawNetworkNodes(0.01, StdDraw.BLACK, dolphins);
                }
            }
        }
    }
}
