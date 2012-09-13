
package objects;

import java.awt.Polygon;


public class Point extends java.awt.Point {
    
    public Point(int x, int y) {
        super(x, y);
    }
    
    public Point(double[][] matrix) {
        super((int)Math.round(matrix[0][0]), (int)Math.round(matrix[1][0]));
    }

    public double[][] toMatrix() {
        double[][] matrix = {{x}, {y}, {1}};
        return matrix;
    }

    public Point add(Point p) {
        return new Point(x + p.x, y + p.y);
    }

    public Point substract(Point p) {
        return new Point(x - p.x, y - p.y);
    }
    
    public static Point getCentroid(Point[] points) {
        int sumX = 0, sumY = 0;
        for (int i = 0; i < points.length; i++) {
            sumX += points[i].x;
            sumY += points[i].y;
        }
        Point p = new Point(sumX / points.length, sumY / points.length);
        return p;
    }
    
    public static Point getCentroid(Polygon polygon) {
        int sumX = 0, sumY = 0;
        for (int i = 0; i < polygon.npoints; i++) {
            sumX += polygon.xpoints[i];
            sumY += polygon.ypoints[i];
        }
        Point p = new Point(sumX / polygon.npoints, sumY / polygon.npoints);
        return p;
    }
    
}
