/******************************************************************************
 *  Author: Grimaldo Stanzani Junior
 *  Compilation:  javac KdTree.java
 *  Execution:    java KDTree
 *
 *  A mutable data type that represents a set of points in an
 *  unit square
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
// import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private static final int VERTICAL = 0;
    // private static final int HORIZONTAL = 1;

    private Node root;      // root of 2d-tree
    private int n;          // Number of points in set
    
    // Helper class
    private static class Node {
        private final Point2D point;  // the point
        private final RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private final int lineDir;    // vertical or horizontal

        public Node(Point2D p, RectHV rect, int lineDir) {
            this.point = p;
            this.rect = rect;
            lb = null;
            rt = null;
            this.lineDir = lineDir;
        }
    }

    /**
     * Construct a empty set of points.
     */
    public KdTree() {
        root = null;
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

        root = insert(root, p, VERTICAL,0, 0, 1, 1);
    }

    private Node insert(Node x, Point2D p, int compare, double xMin, double yMin, double xMax, double yMax) {
        if (x == null) {
            n++;
            return new Node(p, new RectHV(xMin, yMin, xMax, yMax), compare);
        }

        if (equals(x.point, p))
            return x;

        double cmd = compareTo(p, x.point, compare);

        if (cmd < 0) {
            if (compare == VERTICAL) xMax = x.point.x();
            else yMax = x.point.y();
            x.lb = insert(x.lb, p, (compare + 1) % 2, xMin, yMin, xMax, yMax);
        }
        if (cmd > 0 || cmd == 0) {
            if (compare == VERTICAL) xMin = x.point.x();
            else yMin = x.point.y();
            x.rt = insert(x.rt, p, (compare + 1) % 2, xMin, yMin, xMax, yMax);
        }

        return x;
    }

    /**
     * Return true if set contain point point.
     *
     * @param p point to be searched
     * @return true if set contain point point
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();

        return contains(root, p, VERTICAL);
    }

    private boolean contains(Node x, Point2D p, int compare) {
        if (x == null) return false;

        if (equals(x.point, p))
            return true;

        double cmd = compareTo(p, x.point, compare);

        if (cmd >= 0)    return contains(x.rt, p, (compare + 1) % 2);
        if (cmd < 0)     return contains(x.lb, p, (compare + 1) % 2);
        return false;
    }

    /**
     * Draw all points contained in set.
     */
    public void draw() {
        for (Node node : allNodes()) {
            if (node.lineDir == VERTICAL) {
                StdDraw.setPenRadius();
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(node.point.x(), node.rect.ymax(), node.point.x(), node.rect.ymin());
            }
            else {
                StdDraw.setPenRadius();
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(node.rect.xmax(), node.point.y(), node.rect.xmin(), node.point.y());
            }

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            node.point.draw();
        }
    }



    /**
     * Return a iterable of all points that are inside rectangle rect.
     *
     * @param rect query rectangle
     * @return  all points inside rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.IllegalArgumentException();
        Queue<Point2D> queue = new Queue<>();
        range(root, queue, rect);
        return queue;
    }

    private void range(Node x, Queue<Point2D> q, RectHV rect) {
        if (x == null) return;
        if (rect.contains(x.point))
            q.enqueue(x.point);
        if (x.lb != null && rect.intersects(x.lb.rect))
            range(x.lb, q, rect);
        if (x.rt != null && rect.intersects(x.rt.rect))
            range(x.rt, q, rect);
    }


    /**
     * Return the nearest neighbor in the set to point point; null if set is empty.
     *
     * @param p point to be compared
     * @return the nearest point to point
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        if (n == 0) return null;

        return nearest(root, p);
    }

    private Point2D nearest(Node x, Point2D p) {
        if (x.lb == null && x.rt == null)
            return x.point;

        double distX = p.distanceSquaredTo(x.point);

        if (x.lb == null) {
            Point2D right = nearest(x.rt, p);
            if (p.distanceSquaredTo(right) < distX)
                return right;
            else
                return x.point;
        }
        else if (x.rt == null) {
            Point2D left = nearest(x.lb, p);
            if (p.distanceSquaredTo(left) < distX)
                return left;
            else
                return x.point;
        }
        else {
            if (x.lb.rect.contains(p)) {
                Point2D nearest = nearest(x.lb, p);
                if (p.distanceSquaredTo(nearest) > distX)
                    nearest = x.point;
                if (p.distanceSquaredTo(nearest) > x.rt.rect.distanceSquaredTo(p)) {
                    Point2D right = nearest(x.rt, p);
                    if (p.distanceSquaredTo(nearest) > p.distanceSquaredTo(right))
                        return right;
                }
                return nearest;

            }
            else {
                Point2D nearest = nearest(x.rt, p);
                if (p.distanceSquaredTo(nearest) > distX)
                    nearest = x.point;
                if (p.distanceSquaredTo(nearest) > x.lb.rect.distanceSquaredTo(p)) {
                    Point2D left = nearest(x.lb, p);
                    if (p.distanceSquaredTo(nearest) > p.distanceSquaredTo(left))
                        return left;
                }
                return nearest;
            }
        }
    }

    /***********************************
     * Helper functions
     ***********************************/
    private double compareTo(Point2D p1, Point2D p2, int compare) {
        if (compare == VERTICAL) {
            return p1.x() - p2.x();
        }
        else {
            return p1.y() - p2.y();
        }
    }

    private boolean equals(Point2D p1, Point2D p2) {
        if (p1.x() == p2.x() && p1.y() == p2.y())
            return true;
        return false;
    }

    private Iterable<Node> allNodes() {
        Queue<Node> nodes = new Queue<>();
        Queue<Node> queue = new Queue<>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            nodes.enqueue(x);
            queue.enqueue(x.lb);
            queue.enqueue(x.rt);
        }
        return nodes;
    }

    public static void main(String[] args) {
        KdTree t = new KdTree();

        t.insert(new Point2D(0.206107, 0.095492));
        t.insert(new Point2D(0.975528, 0.654508));
        t.insert(new Point2D(0.024472, 0.345492));
        t.insert(new Point2D(0.793893, 0.095492));
        t.insert(new Point2D(0.793893, 0.904508));
        t.insert(new Point2D(0.975528, 0.345492));
        t.insert(new Point2D(0.206107, 0.904508));
        t.insert(new Point2D(0.500000, 0.000000));
        t.insert(new Point2D(0.024472, 0.654508));
        t.insert(new Point2D(0.500000, 1.000000));


        // StdOut.println(t.contains(new Point2D(0.2, 0.7)));


        t.draw();


    }
}
