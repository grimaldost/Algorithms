/******************************************************************************
 *  Author: Grimaldo Stanzani Junior
 *  Compilation:  javac PointSET.java
 *  Execution:    java PointSET
 *
 *  A mutable data type that represents a set of points in an
 *  unit square
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;


public class PointSET {
    private SET<Point2D> pointSet;      // Set of points in the unit square
    private int n;                      // Number of points in set

    /**
     * Construct a empty set of points.
     */
    public PointSET() {
        pointSet = new SET<>();
        n = 0;
    }

    /**
     * Return true if set is empty.
     *
     * @return true if set is empty
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Return the number of points in set.
     *
     * @return the number of points in set
     */
    public int size() {
        return n;
    }

    /**
     * Insert new point in set.
     *
     * @param p point to be inserted
     */
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();

        if (!pointSet.contains(p)) {
            pointSet.add(p);
            n++;
        }
    }

    /**
     * Return true if set contain point p.
     *
     * @param p point to be searched
     * @return true if set contain point p
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();

        return pointSet.contains(p);
    }

    /**
     * Draw all points contained in set.
     */
    public void draw() {
        for (Point2D p : pointSet)
            p.draw();
    }

    /**
     * Return a iterable of all points that are inside rectangle rect.
     *
     * @param rect query rectangle
     * @return  all points inside rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.IllegalArgumentException();

        Queue<Point2D> q = new Queue<>();

        for (Point2D p : pointSet)
            if (rect.contains(p))
                q.enqueue(p);

        return q;
    }

    /**
     * Return the nearest neighbor in the set to point p; null if set is empty.
     *
     * @param p point to be compared
     * @return the nearest point to p
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        if (n == 0) return null;

        Point2D nearest = null;
        double minDist = Double.POSITIVE_INFINITY;

        for (Point2D ps : pointSet) {
            if (p.distanceSquaredTo(ps) < minDist) {
                nearest = ps;
                minDist = p.distanceSquaredTo(ps);
            }
        }

        return nearest;
    }
}