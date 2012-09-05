
package objects;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;


public class Planet extends SpaceObject implements Runnable {

    private int speed;
    private double orbitRadius;
    private double orbitAngle;

    public Planet(int positionX, int positionY, double gravity, int radius, Color color, int speed) {
        super(positionX, positionY, gravity, radius, color);
        this.speed = speed;
        orbitRadius = Math.sqrt(positionX * positionX + positionY * positionY);
        orbitAngle = Math.toDegrees((Math.asin(positionY / orbitRadius)));
    }

    public void run() {
        new Timer(true).scheduleAtFixedRate(
                new TimerTask() {

                    public void run() {
                        orbitAngle = (orbitAngle + 2) % 360;
                        positionX = (int)(orbitRadius * Math.cos(Math.toRadians(orbitAngle)));
                        positionY = (int)(orbitRadius * Math.sin(Math.toRadians(orbitAngle)));
                    }
                }, 0, 250 - speed * 20);
    }

}
