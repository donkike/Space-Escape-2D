package util;

import java.awt.Polygon;
import objects.Point;


public class Util {

    public static double[][] multiply(double[][] A, double[][] B) {
        if (A[0].length != B.length)
            return null;
        int x = A.length, y = B[0].length, n = A[0].length;
        double[][] R = new double[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                double acum = 0;
                for (int k = 0; k < n; k++)
                    acum += A[i][k] * B[k][j];
                R[i][j] = acum;
            }
        }
        return R;
    }
    
    public static Polygon applyTransformation(Polygon polygon, double[][] transformationMatrix) {
        Point p;
        Polygon newPolygon = new Polygon();
        for (int i = 0; i < polygon.npoints; i++) {
            p = new Point(polygon.xpoints[i], polygon.ypoints[i]);
            double[][] appliedPoint = multiply(transformationMatrix, p.toMatrix());
            p = new Point(appliedPoint);
            newPolygon.addPoint(p.x, p.y);
        }
        return newPolygon;
    }

    public static Point[] applyTransformation(Point[] points, double[][] transformationMatrix) {
        Point[] newPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            double[][] appliedPoint = multiply(transformationMatrix, points[i].toMatrix());
            newPoints[i] = new Point(appliedPoint);
        }
        return newPoints;
    }

    public static void printArray(Object[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println("[" + i + "] = " + arr[i]);
        }
    }

}
