/******************************************************************************
 *  Author: Grimaldo Stanzani Junior
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    java BruteCollinearPoints input.txt
 *
 *   Examines 4 points at a time and checks whether they all lie on the
 *   same line segment, returning all such line segments.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class BruteCollinearPoints {
    private int segmentsNum = 0;            // Number of segments
    private Stack<LineSegment> segStack;    // Array of segments

    /**
     * Find all line segments containing 4 points
     *
     * @param points array of points to be examined
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException();

        int n = points.length;
        Point[] pointsArray = new Point[n];
        segStack = new Stack<>();

        for (int i = 0; i < n; i++) {
            if (points[i] == null) throw new java.lang.IllegalArgumentException();
            pointsArray[i] = points[i];
        }

        Arrays.sort(pointsArray);
        for (int i = 0; i < n; i++) {
            if (i > 0 && pointsArray[i].compareTo(pointsArray[i-1]) == 0) throw new java.lang.IllegalArgumentException();
            Point p0 = pointsArray[i];
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    double slopeToJ = p0.slopeTo(pointsArray[j]);
                    double slopeToK = p0.slopeTo(pointsArray[k]);
                    if (slopeToJ != slopeToK)
                        continue;
                    for (int h = k + 1; h < n; h++) {
                        double slopeToL = p0.slopeTo(pointsArray[h]);
                        if (slopeToJ == slopeToL) {
                            segStack.push(new LineSegment(p0, pointsArray[h]));
                            segmentsNum++;
                        }

                    }
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

        for (LineSegment seg : segStack)
            segmentsArray[index++] = seg;

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
