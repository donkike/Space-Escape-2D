
package objects;

import java.awt.Color;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;


public class Planet extends SpaceObject implements Runnable {

    private int speed;
    private double orbitRadius;
    private double orbitAngle;
    private int direction;
    private Timer timer;
    private Point orbitCenter;

    public Planet(int positionX, int positionY, double gravity, int radius, Color color, int speed, int direction, Point orbitCenter) {
        super(positionX, positionY, gravity, radius, color);
        this.speed = speed;
        this.direction = direction;
        orbitRadius = Math.sqrt(positionX * positionX + positionY * positionY);
        orbitAngle = Math.toDegrees((Math.asin(positionY / orbitRadius)));
        this.orbitCenter = orbitCenter;
        timer = new Timer();
    }

    @Override
    public void run() {
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        orbitAngle = (orbitAngle + (2 * direction)) % 360;
                        positionX = orbitCenter.x + (int)(orbitRadius * Math.cos(Math.toRadians(orbitAngle)));
                        positionY = orbitCenter.y + (int)(orbitRadius * Math.sin(Math.toRadians(orbitAngle)));
                    }
                }, 0, 60 - speed * 10);
    }
    
}
