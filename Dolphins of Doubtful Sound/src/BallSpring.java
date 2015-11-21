import java.awt.Font;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.net.MalformedURLException;
/**
 * Represent the Dolphin graph as a ball-and-spring model.
 * Two balls/nodes attract each other proportional to their degrees
 * and inversely proportional to distance squared.
 * They repel each other as a function of distance squared.
 * Consequently, each node (and the system) has associated potential energy.
 * The idea is to step through time and look for a minima of the PE function.
 * Then render the nodes at the positions which satisfy this minima.
 * This is the force-directed graph of the data.
 *
 * Adapted from Tushar Rakheja's ForceDirected.java
 * @author <i> arciel </i>
 */

public class BallSpring
{
    //FIXME ! -> Hardcoding dolphin # is a bad idea!
    public Node[] Dolphins = new Node[62];

    public final double K = 0.5 ; //Spring constant used to model edges as springs.
    public final double L = 0.5 ; //Natural length of the spring.
    public final double G = 0.5 ; //Gravitation factor.

    //FIXME : Rewrite this to use regex.
    public void generateGraph() throws IOException, MalformedURLException
    {
        //URL url = new URL("file://D:/dolphin.gml");
        Scanner read = new Scanner( new File("D:\\dolphin.gml"));
        String currLine;
        while( read.hasNext() )
        {
            currLine = read.nextLine();
            currLine = currLine.trim();
            if(currLine.startsWith("id"))
            {
                int id = Integer.parseInt( currLine.split(" ")[1] );
                currLine = read.nextLine(); currLine = currLine.trim();
                String label = currLine.split("\"")[1];
                Dolphins[id] = new Node(id,label);
                System.out.format("Added node %d %s\n",id,label);
            }
            if(currLine.startsWith("source"))
            {
                int id1, id2;
                id1 = Integer.parseInt( currLine.split(" ")[1] );
                currLine = read.nextLine(); currLine = currLine.trim();
                id2 = Integer.parseInt( currLine.split(" ")[1]);
                Dolphins[id1].network.addLast(Dolphins[id2]);
                System.out.format("Added edge %d -> %d \n",id1,id2);
            }
        }
    }

    public static void main(String[] args) throws IOException
    {
            System.out.println("Hi!");
            BallSpring bs = new BallSpring();
            bs.generateGraph();
    }


}
