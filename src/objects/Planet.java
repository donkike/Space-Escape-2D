
package objects;

import java.awt.Color;


public class Planet extends SpaceObject {
    
    public static final int MAX_SPEED = 2;

    private double speed;
    private double orbitRadius;
    private double orbitAngle;
    private int direction;
    private Point orbitCenter;

    public Planet(int positionX, int positionY, double gravity, int radius, Color color, double speed, int direction, Point orbitCenter) {
        super(positionX, positionY, gravity, radius, color);
        this.speed = speed;
        this.direction = direction;
        orbitRadius = Math.sqrt(positionX * positionX + positionY * positionY);
        orbitAngle = Math.toDegrees((Math.asin(positionY / orbitRadius)));
        this.orbitCenter = orbitCenter;
    }
    
    public void move() {
        orbitAngle = (orbitAngle + (speed * direction)) % 360;
        position.x = orbitCenter.x + (int)(orbitRadius * Math.cos(Math.toRadians(orbitAngle)));
        position.y = orbitCenter.y + (int)(orbitRadius * Math.sin(Math.toRadians(orbitAngle)));
    }
    
}
