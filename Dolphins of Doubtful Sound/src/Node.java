import java.awt.*;
import java.util.*;
import java.io.*;

public class Node// implements Comparable<Node>, Iterable<Node>
{
    /**
    *   FIXME : OOP-ify this, add getters and setters and check for
    *           bad arguments in them.
    *   Until then, YOLO.
    */
    public int id;
    public String label;
    public LinkedList<Node> network = new LinkedList<Node>();

    public Vector2D position;
    public Vector2D velocity;
    public Vector2D acceleration;

    private final int radius = 64;

    /**
    *   Draw a blob centered around the Node's position.
    *   Write it's name below.
    */

    public Node(int id, String label)
    {
        this.id = id;
        this.label = label;
        this.position = new Vector2D(Math.random() * 800, Math.random() * 600);
    }

    public static double getDistance(Node n1, Node n2)
    {
        return n1.position.minus(n2.position).length();
    }

    public static double getPairGPE(Node n1, Node n2, double G)
    {
        /*
            We define the mass of a node to be equal to the number of
            its neighbours.
            GPE = - m_1 * m_2 / r
        */
        int m1 = n1.network.size();
        int m2 = n2.network.size();
        double r = Node.getDistance(n1, n2);

        return -1 * G * m1 * m2 / r;
    }

    public static double getPairEPE(Node n1, Node n2, double K, double L)
    {
        /*
            Elastic PE = ½ * K * (L-r)²
        */
        double r = Node.getDistance(n1, n2);
        return 0.5 * K * (L - r) * (L - r);
    }

    public void drawMe()
    {
        System.out.format("Network size is %d\n",this.network.size());
        StdDraw.setPenColor(StdDraw.BLACK);
        double node_radius = radius * network.size();
        StdDraw.setPenRadius(.05);
        StdDraw.filledCircle(position.x(), position.y(), node_radius);

        StdDraw.setPenRadius();
        StdDraw.text(position.x() - node_radius, position.y() - node_radius - 5, label, 0);
    }
    /**
    * FIXME : This gets called twice per pair.
    */

    public void drawEdges()
    {
        for( Node friend : network)
        {
            StdDraw.line( position.x(), position.y(), friend.position.x(), friend.position.y() );
        }
    }
}
