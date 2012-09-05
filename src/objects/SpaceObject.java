

package objects;

import java.awt.Color;


public class SpaceObject {

    protected int positionX;
    protected int positionY;
    protected double gravity;
    protected int radius;
    protected int gravityRadius;
    protected Color color;

    public SpaceObject() {}

    public SpaceObject(int positionX, int positionY, double gravity, int radius, Color color) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.gravity = gravity;
        this.radius = radius;
        this.gravityRadius = radius * 2;
        this.color = color;
    }

    public double getGravity() {
        return gravity;
    }

    public int getGravityRadius() {
        return gravityRadius;
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

}
