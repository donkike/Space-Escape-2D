
package objects;

import java.awt.Color;
import java.awt.Polygon;
import util.Util;
import util.transformations.RotationTransformer;
import util.transformations.TranslationTransformer;

public class Spaceship {

    private int positionX;
    private int positionY;
    private double direction;
    private Color color;
    private Polygon polygon;
    
    public Spaceship(int positionX, int positionY, double direction, Color color) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.direction = direction;
        this.color = color;
        polygon = new Polygon();
        polygon.addPoint(positionX, positionY - 30);
        polygon.addPoint(positionX + 10, positionY);
        polygon.addPoint(positionX + 8, positionY + 10);
        polygon.addPoint(positionX - 8, positionY + 10);
        polygon.addPoint(positionX - 10, positionY);
    }
    
    public void rotate(double angle) {
        System.out.println(angle);
        direction += angle;
        TranslationTransformer tt = new TranslationTransformer();
        RotationTransformer rt = new RotationTransformer();
        double[][] transformationMatrix = tt.apply(-positionX, -positionY);
        transformationMatrix = Util.multiply(rt.apply(angle), transformationMatrix);
        transformationMatrix = Util.multiply(tt.apply(positionX, positionY), transformationMatrix);
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
    
}
