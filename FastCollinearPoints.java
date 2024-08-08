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

public class FastCollinearPoints {

    private Point[] points;

    private int size;

    private ArrayList<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {

        if (points == null) {

            throw new IllegalArgumentException();
        }

        this.points = new Point[points.length];
        this.segments = new ArrayList<>();

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

        int n = points.length;

        Point[] sortedPoints = Arrays.copyOf(points, n);

        ArrayList<Point> collinerPoints = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            Point origin = points[i];

            Arrays.sort(sortedPoints, origin.slopeOrder());

            int j = 1;
            while (j < n) {

                double slopeQ1 = origin.slopeTo(sortedPoints[j]);

                while (j < n && Double.compare(slopeQ1, origin.slopeTo(sortedPoints[j])) == 0) {
                    collinerPoints.add(sortedPoints[j++]);
                }

                if (collinerPoints.size() >= 3) {
                    collinerPoints.add(origin);
                    collinerPoints.sort(null);
                    if (collinerPoints.get(0).compareTo(origin) == 0) {
                        Point last = collinerPoints.get(collinerPoints.size() - 1);
                        segments.add(new LineSegment(origin, last));
                    }
                }
                collinerPoints.clear();
            }
        }
    }

    private void checkForDuplicates() {

        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("Duplicates");
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
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

        FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points);
        LineSegment[] segments = fastCollinearPoints.segments();
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
