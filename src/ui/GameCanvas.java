

package ui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Point;
import objects.*;

public class GameCanvas extends Canvas implements Runnable, KeyListener {

    public static boolean GAME_OVER = false;
    public static int LIVES = 3;
    
    private Sun sun;
    private Planet[] planets;
    private Gem[] gems;
    private Spaceship sp;
    private Point[] stars;
    private Thread mainThread;
    private boolean justCrashed;
    private Polygon spShape;

    public GameCanvas() {  }
    
    public void initialize() {
        
        justCrashed = false;
        
        sun = new Sun(getHeight() / 2, getWidth() / 2, 0.8, 40, Color.orange);

        planets = new Planet[6];
        for (int i = 0; i < 6; i++) {
            int radius = i * 50 + 90;
            double angle = Math.random() * 2 * Math.PI;
            planets[i] = new Planet((int)(radius * Math.sin(angle)), (int)(radius * Math.cos(angle)), 
                    0.4, (int)(Math.random() * 15) + 10, new Color((float)Math.random(), 
                    (float)Math.random(), (float)Math.random()), Math.random() * Planet.MAX_SPEED,
                    (int)Math.pow(-1, (int)(Math.random() * 2)), new Point(sun.getPosition().x, sun.getPosition().y));
        }
        
        gems = new Gem[5];
        for(int j = 0; j < 5; j++) {
            int x, y, distance = 0;
            do {
                x = (int) (Math.random() * 600 + 100);
                y = (int) (Math.random() * 600 + 100);
                Point difference = new Point(sun.getPosition().x - x, sun.getPosition().y - y); 
                distance = difference.x * difference.x + difference.y * difference.y;
            } while (distance <= sun.getRadius() + 20);
            gems[j] = new Gem(x, y, Color.CYAN);
        }
        
        stars = new Point[200];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Point((int)(Math.random() * getWidth()),
                    (int)(Math.random() * getHeight())); 
        }
        
        sp = new Spaceship(50, getHeight() - 100, Math.toRadians(90), Color.LIGHT_GRAY);
        spShape = sp.getPolygon();
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
        
        
        g.setColor(new Color(1.0f, 0.0f, 0.0f, 0.2f));
        g.fillOval(sun.getPosition().x - sun.getGravityRadius(), sun.getPosition().y - sun.getGravityRadius(),
                sun.getGravityRadius() * 2, sun.getGravityRadius() * 2);

        g.setColor(sun.getColor());
        g.fillOval(sun.getPosition().x - sun.getRadius(), sun.getPosition().y - sun.getRadius(),
                sun.getRadius() * 2, sun.getRadius() * 2);

        for (Planet planet : planets) {
            
            g.setColor(new Color(1.0f, 0.0f, 0.0f, 0.2f));
            g.fillOval(planet.getPosition().x - planet.getGravityRadius(), planet.getPosition().y - planet.getGravityRadius(),
                    planet.getGravityRadius() * 2, planet.getGravityRadius() * 2);
            
            g.setColor(planet.getColor());
            g.fillOval(planet.getPosition().x - planet.getRadius(), planet.getPosition().y - planet.getRadius(),
                    planet.getRadius() * 2, planet.getRadius() * 2);
        }
        
        for (Gem gem : gems) {
            g.setColor(gem.getColor());
            g.fillPolygon(gem.getPolygon());
        }
        
        g.setColor(sp.getColor());
        g.fillPolygon(sp.getPolygon());
        
        for (int i = 0; i < GameCanvas.LIVES; i++) {
            Point p = new Point(i * 40 + 40, 40);
            g.drawPolygon(sp.getGeneralShape(p));
        }
        
        if (GameCanvas.GAME_OVER)
            paintGameOver(g);
        
    }
    
    public void paintCrash(Graphics g) {
        g.setColor(new Color(255, 0, 0, 100));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    
    public void paintGameOver(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(Color.red);
        g.setFont(new Font("Times New Roman", Font.BOLD, 80));
        g.drawString("Game Over", getWidth() / 2 - 170, getHeight() / 2 - 60);
        
        g.setColor(Color.white);        
        g.setFont(new Font("Times New Roman", Font.BOLD, 42));
        g.drawString("Press Enter to restart", getWidth() / 2 - 160, getHeight() / 2 + 60);
    }
    
    public void checkCollisions() {
        Polygon p = sp.getPolygon();
        Point difference;
        double distance;
        if (!justCrashed) {
            for(Planet planet : planets) {
                for(int i=0; i < p.npoints; i++) {
                    difference = planet.getPosition().substract(new Point(p.xpoints[i], p.ypoints[i]));
                    distance = difference.x * difference.x + difference.y * difference.y;
                    if (distance < Math.pow(planet.getRadius(), 2)) {
                        if (GameCanvas.LIVES < 1) {
                            GameCanvas.GAME_OVER = true;
                        } else {
                            difference = planet.getPosition().substract(sp.getPosition());
                            double direction = Math.atan2(difference.y, difference.x);
                            sp.resetAcceleration();
                            sp.accelerate(-direction, Spaceship.MAX_ACC);
                            GameCanvas.LIVES--;
                        }
                        paintCrash(getGraphics());
                        beginTemproraryInvulnerability();
                    }
                }
            }
        }
        for(int i=0; i < p.npoints; i++) {
            difference = new Point(sun.getPosition().x - p.xpoints[i], sun.getPosition().y - p.ypoints[i]);
            distance = difference.x * difference.x + difference.y * difference.y;
            if (distance < Math.pow(sun.getRadius(),2)) {
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
    
    public void calculateGravity(SpaceObject so) {
        Point difference = so.getPosition().substract(sp.getPosition());
        double distance = difference.x * difference.x + difference.y * difference.y;
        double r2 = Math.pow(so.getGravityRadius(), 2);
        if (distance < r2) {
            double direction;
            direction = Math.atan2(difference.y, difference.x);
            sp.accelerate(0.015, direction);
        }
    }
    
    public void beginTemproraryInvulnerability() {
        new Thread() {
            @Override
            public void run() {
                justCrashed = true;
                try {
                    sleep(400);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameCanvas.class.getName()).log(Level.SEVERE, null, ex);
                }
                justCrashed = false;
            }
        }.start();
    }
    
    public void updateWorld() {
        calculateGravity(sun);         
        for (Planet planet : planets) {
            planet.move();
            calculateGravity(planet);            
        }
    }
    
    public void restart() {
        GameCanvas.GAME_OVER = false;
        GameCanvas.LIVES = 3;
        initialize();
        run();
    }

    
    @Override
    public void run() {
        mainThread = new Thread() {
            @Override
            public void run() {
                while (!GameCanvas.GAME_OVER) {
                    if (!justCrashed) updateWorld();
                    repaint();
                    checkCollisions();
                    try {
                        sleep(20);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameCanvas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        mainThread.start();
        sp.run();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()) {
            case (KeyEvent.VK_UP): 
                sp.accelerate(-0.3);
                break;
            case (KeyEvent.VK_DOWN):
                sp.accelerate(0.3);
                break;
            case (KeyEvent.VK_RIGHT):
                sp.rotate(Math.toRadians(30));
                break;
            case (KeyEvent.VK_LEFT):
                sp.rotate(-Math.toRadians(30));
                break;
            case (KeyEvent.VK_ENTER):
                if (GameCanvas.GAME_OVER) restart();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }

}
