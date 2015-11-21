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

    private final int radius = 2;

    /**
    *   Draw a blob centered around the Node's position.
    *   Write it's name below.
    */

    public Node(int id, String label)
    {
        this.id = id;
        this.label = label;
    }

    public void DrawMe()
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        double node_radius = radius * (network.size()/64);
        StdDraw.setPenRadius(node_radius);
        StdDraw.point(position.x(),position.y());
        StdDraw.setPenRadius();
        StdDraw.text(position.x() - node_radius, position.y() - node_radius - 5, label, 0);
    }
    /**
    * FIXME : This gets called twice per pair.
    */

    public void DrawEdges()
    {
        for( Node friend : network)
        {
            StdDraw.line( position.x(), position.y(), friend.position.x(), friend.position.y() );
        }
    }
}
