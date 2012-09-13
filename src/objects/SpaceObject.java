

package objects;

import java.awt.Color;


public class SpaceObject {

    protected Point position;
    protected double gravity;
    protected int radius;
    protected int gravityRadius;
    protected Color color;

    public SpaceObject() {}

    public SpaceObject(int positionX, int positionY, double gravity, int radius, Color color) {
        this.position = new Point(positionX, positionY);
        this.gravity = gravity;
        this.radius = radius;
        this.gravityRadius = radius * 6;
        this.color = color;
    }
    
    public double getGravity() {
        return gravity;
    }

    public int getGravityRadius() {
        return gravityRadius;
    }

    public Point getPosition() {
        return position;
    }

    public int getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }

}
