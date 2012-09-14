

package ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objects.*;

public class GameCanvas extends Canvas implements Runnable, KeyListener {

    public static boolean GAME_OVER = false;
    
    private Sun sun;
    private Planet[] planets;
    private Gem[] gems;
    private Spaceship sp;
    private Point[] stars;
    private Thread mainThread;

    public GameCanvas() {  }
    
    public void initialize() {
        sun = new Sun(getHeight() / 2, getWidth() / 2, 0.8, 40, Color.orange);

        planets = new Planet[6];
        for (int i = 0; i < 6; i++) {
            int radius = i * 50 + 80;
            double angle = Math.random() * 2 * Math.PI;
            planets[i] = new Planet((int)(radius * Math.sin(angle)), (int)(radius * Math.cos(angle)), 
                    0.4, (int)(Math.random() * 15) + 5, new Color((float)Math.random(), 
                    (float)Math.random(), (float)Math.random()), Math.random() * Planet.MAX_SPEED,
                    (int)Math.pow(-1, (int)(Math.random() * 2)), new Point(sun.getPositionX(), sun.getPositionY()));
        }
        
        gems = new Gem[5];
        for(int j = 0; j < 5; j++) {
            gems[j] = new Gem((int) (Math.random() * 600 + 100), (int) (Math.random() * 600 + 100), 
                    Color.CYAN);
        }
        
        stars = new Point[200];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Point((int)(Math.random() * getWidth()),
                    (int)(Math.random() * getHeight())); 
        }
        
        sp = new Spaceship(50, getHeight() - 100, Math.toRadians(90), Color.LIGHT_GRAY);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.white);
        for (Point star : stars) {            
            int size = (int)(Math.random() * 4);          
            star.x = (star.x + 2) % getWidth();
            star.y = (star.y + 1) % getHeight();
            g.fillOval(star.x, star.y, size, size);
        }

        g.setColor(sun.getColor());
        g.fillOval(sun.getPositionX() - sun.getRadius(), sun.getPositionY() - sun.getRadius(),
                sun.getRadius() * 2, sun.getRadius() * 2);

        for (Planet planet : planets) {
            g.setColor(planet.getColor());
            g.fillOval(planet.getPositionX() - planet.getRadius(), planet.getPositionY() - planet.getRadius(),
                    planet.getRadius() * 2, planet.getRadius() * 2);
        }
        
        for (Gem gem : gems) {
            g.setColor(gem.getColor());
            g.fillPolygon(gem.getPolygon());
        }
        
        g.setColor(sp.getColor());
        g.fillPolygon(sp.getPolygon());
        
    }
    
    public void checkCollisions() {
        Polygon p = sp.getPolygon();
        double point;
        for(Planet planet : planets) {
            for(int i=0; i < p.npoints; i++) {
                point = Math.pow(p.xpoints[i]-planet.getPositionX(), 2) + Math.pow(p.ypoints[i]-planet.getPositionY(),2);
                if (point < Math.pow(planet.getRadius(),2)) {
                    GameCanvas.GAME_OVER = true;
                }
            }
        }
        for(int i=0; i < p.npoints; i++) {
            point = Math.pow(p.xpoints[i]-sun.getPositionX(),2) + Math.pow(p.ypoints[i]-sun.getPositionY(),2);
            if (point < Math.pow(sun.getRadius(),2)) {
                GameCanvas.GAME_OVER = true;
            }
        }
        Polygon g;
        for(Gem gem : gems) {
            g = gem.getPolygon();
            for(int i=0; i < g.npoints; i++) {
                if (p.contains(g.xpoints[i], g.ypoints[i])) {
                    gem.collect();
                }
            }
        }
    }
    
    public void updateWorld() {        
        for (Planet planet : planets)
            planet.move();
    }
    
    @Override
    public void run() {
        mainThread = new Thread() {
            @Override
            public void run() {
                while (!GameCanvas.GAME_OVER) {
                    updateWorld();
                    repaint();
                    checkCollisions();
                    try {
                        sleep(20);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameCanvas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                JOptionPane.showMessageDialog(null, "Game Over!");
            }
        };
        mainThread.start();
        sp.run();
    }
    
    public void stop() {
        GameCanvas.GAME_OVER = true;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()) {
            case (KeyEvent.VK_UP): 
                sp.accelerate(-0.2);
                break;
            case (KeyEvent.VK_DOWN):
                sp.accelerate(0.2);
                break;
            case (KeyEvent.VK_RIGHT):
                sp.rotate(Math.toRadians(30));
                break;
            case (KeyEvent.VK_LEFT):
                sp.rotate(-Math.toRadians(30));
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }

}
