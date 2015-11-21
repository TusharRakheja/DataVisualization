import java.awt.Font;
import java.net.URL;
import java.io.IOException;
import java.util.Scanner;
import java.net.MalformedURLException;
/**
 * Class to make a force-directed graph of the data.
 * Ideas: <ul>
 *        <li> Every edge acts like a spring.</li>
 *        <li> Every node has a mass which is given by the number of edges.</li>
 *        <li> Every node feels gravity from other needs. </li>
 *        <li> As a result, the system has a potential energy associated with it.</li>
 *        <li> Iteratively step through time, until the potential energy is below a certain threshold.</li>
 *        </ul> <!-- I should like writing HTML some more. //-->
 * @author <i> Tushar </i>
 */
public class ForceDirected {
    public Dolphin[] dolphins = new Dolphin[62];         // The array to contain all the dolphins.
    private double K = 0.0001;                                    // The spring constant.
    private double G = 1;                            // The gravitational constant.
    private double eqL = 500;                                 // Equilibrium length.
    /**
     * Returns the total potential potential energy of the system in its current state.
     * @param set The array of dolphins.
     * @return The potential energy of the system.
     */
    public double PE(Dolphin[] set) {
       return ePE(set) + gPE(set);
    }
    /**
     * Calculates the elastic potential energy of the system in its current state.
     * @param set The array of dolphins.
     * @return The elastic potential energy of the system in its current state.
     */
    public double ePE(Dolphin[] set) {
        double energy = 0;
        for (Dolphin d : set) {
            for (Integer i : d) {
                energy += 0.5*K*(eqL - Dolphin.dist(d, set[i]))*(eqL - Dolphin.dist(d, set[i]));
            }
        }
        return energy;
    }
    /**
     * Calculates the gravitational potential energy of the system in its current state.
     * @param set The array of dolphins.
     * @return
     */
    public  double gPE(Dolphin[] set) {
        double energy = 0;
        for (int i = 0; i < set.length - 1; i++) {
            for (int j = i + 1; j < set.length; j++) {
                energy += gPE(set[i], set[j]);
            }
        }
        return energy;
    }
    /**
     * The gravitational potential energy b/w two dolphins.
     * @param d1 Dolphin 1.
     * @param d2 Dolphin 2.
     * @return The potential energy b/w d1 and d2.
     */
    public double gPE(Dolphin d1, Dolphin d2) {
        return G*d1.mass()*d2.mass()/Math.sqrt(Dolphin.distSquared(d1, d2));
    }
    /**
     * Adjust the coordinates by 1 time-step.
     * @param timeStep The time-step magnitude.
     * Will probably kill my computer.
     */
    public void adjust(double timeStep) {
        for (Dolphin d : dolphins) {
            d.accel(new Vector2D(0, 0));
            for (Integer i : d) {
                double eLen = Dolphin.dist(d, dolphins[i]);
                /*d.accel(d.accel().plus(
                    (d.position().minus(dolphins[i].position())).unit()).times(K*(eqL - eLen)/d.mass())
                        ); // Horrible.*/
                d.accel().add(d.position().minus(dolphins[i].position()).unit().times(K*(eqL - eLen)/d.mass()));
            }/*
            for (Dolphin d2 : dolphins) {
                if (d2 != d) {
                    double eLenSQ = Dolphin.distSquared(d, d2);
                    System.out.println(eLenSQ);
                    Vector2D newVec = new Vector2D((d.position().minus(d2.position())).unit()).times((G*d.mass()*d2.mass())/eLenSQ);
                    newVec.reverse();
                    d.accel(d.acc;
                }
            }*/
            //System.out.println(d.accel().x() + " " + d.accel().y());
        }
        for (Dolphin d : dolphins) d.adjust(timeStep);
    }
    /**
     * Gets data from the online database and constructs the graph.
     * @throws java.net.MalformedURLException In case URL is incorrect/broken.
     * @throws java.io.IOException If URL.openStream() fails.
     */
    public void generateGraph() throws MalformedURLException, IOException {
        URL url = new URL("http://networkdata.ics.uci.edu/data/dolphins/dolphins.gml");
        Scanner read = new Scanner(url.openStream());
        String line;
        String[] temp;
        int dolph1, dolph2;                             // Used while reading edges.
        for (int i = 0; i < 7; i++) {                   // Get to the first label.
            read.nextLine();                            // Due to input format.
        }                                               // We're now going to read the labels.
        for (int i = 0; i < 62; i++) {
            line = read.nextLine();
            temp = line.split("\"");                    // The label is in quotes.
            temp = temp[1].split("\"");
            line = temp[0];
            dolphins[i] = new Dolphin(i, line);
            read.nextLine();                            // Get to the next label.
            if (i != 61) {                              // Due to input format.
                read.nextLine(); read.nextLine();
                read.nextLine();
            }
        }                                               // We're now going to read the edges.
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
            read.nextLine();                            // Get to the next edge.
        }
        Dolphin.setRandom(dolphins);                     // Initially set the nodes around a ring.
    }

    /**
     * The main method.
     * @param args Conventional.
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        ForceDirected fd = new ForceDirected();
        Dolphin.setRadius(270);
        Dolphin.setDelta(42);
        Font f = new Font("SansSerif", Font.PLAIN, 13);
        StdDraw.setCanvasSize(800, 670);
        StdDraw.setXscale(0, 800);
        StdDraw.setYscale(0, 670);
        StdDraw.setFont(f);
        fd.generateGraph();
        Scanner s = new Scanner(System.in);
        while (true) {
            if (StdDraw.mousePressed()) break; {
                //StdDraw.clear();
                for (int i = 0; i < 62; i++) {
                    fd.dolphins[i].drawNode2(0.01, 0.01);
                }
                fd.adjust(1);
            }
        }
    }
}
