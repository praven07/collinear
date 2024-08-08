/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private Point[] points;

    private ArrayList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {

        if (points == null) {
            throw new IllegalArgumentException();
        }

        this.points = new Point[points.length];
        segments = new ArrayList<>();

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }

            this.points[i] = points[i];
        }

        compute();
    }

    private void compute() {

        Arrays.sort(points);

        checkForDuplicates();

        if (points.length < 4) {
            return;
        }

        int n = points.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int m = k + 1; m < n; m++) {
                        double p = points[i].slopeTo(points[j]);
                        double q = points[i].slopeTo(points[k]);
                        double r = points[i].slopeTo(points[m]);

                        if (p == q && p == r) {
                            segments.add(new LineSegment(points[i], points[m]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    private void checkForDuplicates() {

        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("Duplicates");
            }
        }
    }

    public static void main(String[] args) {

        In in = new In(args[0]);

        int n = in.readInt();
        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.setPenRadius(0.01);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        StdDraw.setPenColor(Color.CYAN);

        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);
        LineSegment[] segments = bruteCollinearPoints.segments();
        for (LineSegment segment : segments) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

        for (Point p : points) {
            StdDraw.setPenColor(Color.BLUE);
            p.draw();
        }
        StdDraw.show();
    }
}
