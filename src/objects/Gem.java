
package objects;

import java.awt.Color;
import java.awt.Polygon;

public class Gem {
    
    private int positionX;
    private int positionY;
    private Color color;
    private int radius;

    public Gem(int positionX, int positionY, Color color) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.color = color;
        radius = 10;
    }
    
    public Polygon getPolygon() {
        return getPolygon(new Point(positionX, positionY));
    }
    
    public Polygon getPolygon(Point pt) {
        Polygon p = new Polygon();
        for(int i = 0; i < 5; i++) {
            p.addPoint((int) (pt.x + radius * Math.cos(i*2 * Math.PI / 5)), 
            (int) (pt.y + radius * Math.sin(i*2 * Math.PI /5)));
        }
        return p;
    }
    
    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getRadius() {
        return radius;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setRadius(int radius) {
        this.radius = radius;
    }
}
