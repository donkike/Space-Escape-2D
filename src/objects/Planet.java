
package objects;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;


public class Planet extends SpaceObject {

    private int speed;
    private double orbitRadius;
    private double orbitAngle;
    public int direction;
    private Timer timer;

    public Planet(int positionX, int positionY, double gravity, int radius, Color color, int speed, int direction) {
        super(positionX, positionY, gravity, radius, color);
        this.speed = speed;
        this.direction = direction;
        orbitRadius = Math.sqrt(positionX * positionX + positionY * positionY);
        orbitAngle = Math.toDegrees((Math.asin(positionY / orbitRadius)));
        timer = new Timer();
        startOrbit();
    }

    public void startOrbit() {
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        if (direction == 1){
                            orbitAngle = (orbitAngle - 2) % 360;
                        } else {
                            orbitAngle = (orbitAngle + 2) % 360;
                        }
                        positionX = (int)(orbitRadius * Math.cos(Math.toRadians(orbitAngle)));
                        positionY = (int)(orbitRadius * Math.sin(Math.toRadians(orbitAngle)));
                    }
                }, 0, 200 - speed * 20);
    }
    
}
