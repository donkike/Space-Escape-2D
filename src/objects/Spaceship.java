
package objects;

import java.awt.Color;
import java.awt.Polygon;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.GameCanvas;
import util.Util;
import util.transformations.RotationTransformer;
import util.transformations.TranslationTransformer;

public class Spaceship implements Runnable {
    
    private double accX;
    private double accY;
    private double direction;
    private Color color;
    private Polygon polygon;
    private Thread thread;
    
    public Spaceship(int positionX, int positionY, double direction, Color color) {
        this.direction = direction;
        this.color = color;
        accX = accY = 0;
        polygon = new Polygon();
        polygon.addPoint(positionX, positionY - 30);
        polygon.addPoint(positionX + 10, positionY);
        polygon.addPoint(positionX + 8, positionY + 10);
        polygon.addPoint(positionX - 8, positionY + 10);
        polygon.addPoint(positionX - 10, positionY);
    }
    
    public void rotate(double angle) {
        direction += angle;
        TranslationTransformer tt = new TranslationTransformer();
        RotationTransformer rt = new RotationTransformer();
        Point centroid = getPosition();
        double[][] transformationMatrix = tt.apply(-centroid.x, -centroid.y);
        transformationMatrix = Util.multiply(rt.apply(angle), transformationMatrix);
        transformationMatrix = Util.multiply(tt.apply(centroid.x, centroid.y), transformationMatrix);
        polygon = Util.applyTransformation(polygon, transformationMatrix);
    }
    
    public void accelerate(double delta) {
        accelerate(delta, direction);
    }
    
    public void accelerate(double delta, double direction) {
        double newAccX = accX + delta * Math.cos(direction);
        double newAccY = accY + delta * Math.sin(direction);
        if (Math.abs(newAccX) < 1)
            accX = newAccX;
        if (Math.abs(newAccY) < 1)
            accY = newAccY;
    }
    
    public void move() {
        TranslationTransformer tt = new TranslationTransformer();
        double[][] transformationMatrix = tt.apply(accX * 5, accY * 5);
        polygon = Util.applyTransformation(polygon, transformationMatrix);
    }
    
    public Polygon getPolygon() {
        return polygon;
    }
    
    public Color getColor() {
        return color;
    }

    public double getDirection() {
        return direction;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }
    
    public double getAcceleration() {
        return Math.sqrt(accX * accX + accY * accY);
    }
    
    public Point getPosition() {
        return Point.getCentroid(polygon);
    }

    @Override
    public void run() {
        thread = new Thread() {
            @Override
            public void run() {
                while(!GameCanvas.GAME_OVER) {
                    move();
                    int movingX = Double.compare(accX, 0);
                    int movingY = Double.compare(accY, 0);                    
                    accX += 0.01 * -movingX;
                    accY += 0.01 * -movingY;
                    try {
                        sleep(60);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Spaceship.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        thread.start();
    }
    
}
