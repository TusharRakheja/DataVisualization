import java.awt.Font;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.net.MalformedURLException;

/**
 * Represent the Dolphin graph as a ball and spring model.
 * Adapted from Tushar Rakheja's ForceDirected.java
 * @author <i> arciel </i>
 */

public class BallSpring
{
    //FIXME ! -> Hardcoding dolphin # is a bad idea!
    public static Node[] Dolphins = new Node[62];

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
                id2 = Integer.parseInt( currLine.split(" ")[1] );
                Dolphins[id1].network.addLast(Dolphins[id2]);
                System.out.format("Added edge %d -> %d \n",id1,id2);
            }
        }
    }
    /**
    * Each node in the graph experiences 2 forces.
    * The spring foce works to attract directly connected nodes.
    * The coulombic force works locally to repel nodes close to each other
    * regardless of whether they are connected or not.
    *
    * Given that nodes are initially placed randomly, we have to find a
    * stable state for the system in which all forces are balanced out.
    *
    * Idea : Use gradient descent on the PE of the system.
    */

    public double sysPE();

    public void iterateSoln()
    {

    }

    public static void main(String[] args) throws IOException
    {
            System.out.println("Hi!");
            BallSpring bs = new BallSpring();
            bs.generateGraph();

            Font f = new Font("SansSerif", Font.PLAIN, 13);
            StdDraw.setCanvasSize(800, 670);
            StdDraw.setXscale(0, 800);
            StdDraw.setYscale(0, 670);
            StdDraw.setFont(f);

            Dolphins[0].DrawMe();

    }


}
