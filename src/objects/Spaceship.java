
package objects;

import java.awt.Color;
import java.awt.Polygon;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Util;
import util.transformations.RotationTransformer;
import util.transformations.TranslationTransformer;

public class Spaceship implements Runnable {

    private int positionX;
    private int positionY;
    private double accX;
    private double accY;
    private double direction;
    private Color color;
    private Polygon polygon;
    private Timer timer;
    
    public Spaceship(int positionX, int positionY, double direction, Color color) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.direction = direction;
        this.color = color;
        timer = new Timer(true);
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
        double[][] transformationMatrix = tt.apply(-positionX, -positionY);
        transformationMatrix = Util.multiply(rt.apply(angle), transformationMatrix);
        transformationMatrix = Util.multiply(tt.apply(positionX, positionY), transformationMatrix);
        polygon = Util.applyTransformation(polygon, transformationMatrix);
    }
    
    public void accelerate(double delta) {
            double newAccX = accX + delta * Math.cos(direction);
            double newAccY = accY + delta * Math.sin(direction);
            if (Math.abs(newAccX) < 1)
                accX = newAccX;
            if (Math.abs(newAccY) < 1)
                accY = newAccY;        
    }
    
    public void move() {
        TranslationTransformer tt = new TranslationTransformer();
        double[][] transformationMatrix = tt.apply(accX * 5, -accY * 5);
        polygon = Util.applyTransformation(polygon, transformationMatrix);
        positionX += accX * 5;
        positionY -= accY * 5;
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

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
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

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void run() {
        new Thread() {
            @Override
            public void run() {
                while(true) {
                    move();
                    try {
                        sleep(20);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Spaceship.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
    }
    
}
