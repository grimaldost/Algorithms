/******************************************************************************
 *  Author: Grimaldo Stanzani Junior
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints input.txt
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Iterator;

public class FastCollinearPoints {
    private int segmentsNum = 0;            // Number of segments
    private Stack<Point> startPoint;        // Stack of start point of segments
    private Stack<Point> endPoint;          // Stack of end point of segments
    //private Stack<Double> slopes;            // Stack of segment's slope


    /**
     * Find all line segments containing 4 or more points
     *
     * @param points array of points to be examined
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException();

        int n = points.length;
        Point[] pointsArray = new Point[n];
        startPoint = new Stack<>();
        endPoint = new Stack<>();
        //slopes = new Stack<>();

        for (int i = 0; i < n; i++) {
            if (points[i] == null) throw new java.lang.IllegalArgumentException();
            pointsArray[i] = points[i];
        }
        Arrays.sort(pointsArray);

        for (int i = 1; i < n; i++)
            if (pointsArray[i].compareTo(pointsArray[i-1]) == 0) throw new java.lang.IllegalArgumentException();

        for (int i = 0; i < n-3; i++) { // do not need loop through last 3 elements
            Arrays.sort(pointsArray, i, n);
            Point p0 = pointsArray[i];
            Arrays.sort(pointsArray, i+1, n, p0.slopeOrder());

            int j = i + 1;
            double slope = p0.slopeTo(pointsArray[j]);
            int count = 0;

            while (true) {
                double nextSlope = p0.slopeTo(pointsArray[++j]);
                if (nextSlope == slope)
                    count++;
                else {
                    if (count >= 2) {
                        startPoint.push(p0);
                        endPoint.push(pointsArray[j-1]);
                        //slopes.push(slope);
                        segmentsNum++;
                    }
                    count = 0;
                    slope = nextSlope;
                }
                if (j >= n-1) {
                    if (count >= 2) {
                        startPoint.push(p0);
                        endPoint.push(pointsArray[j]);
                        //slopes.push(slope);
                        segmentsNum++;
                    }
                    break;
                }
            }
        }

    }

    /**
     * Return the number of line segments
     *
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return segmentsNum;
    }

    /**
     * Return a array of line segments
     *
     * @return array of line segments
     */
    public LineSegment[] segments() {
        LineSegment[] segmentsArray = new LineSegment[segmentsNum];
        int index = 0;

        Iterator<Point> startIt = startPoint.iterator();
        Iterator<Point> endIt = endPoint.iterator();

        while (startIt.hasNext() && endIt.hasNext()) {
            segmentsArray[index++] = new LineSegment(startIt.next(),endIt.next());
        }

        return segmentsArray;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}