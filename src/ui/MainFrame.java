

package ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javax.swing.JFrame;
import javazoom.jl.player.Player;

public class MainFrame extends JFrame {

    private GameCanvas canvas;
    private Player player;
    
    public MainFrame() {
        setTitle("Space Escape!");
        setSize(800, 800);

        canvas = new GameCanvas();
        canvas.setSize(getSize());
        add(canvas);
        
        canvas.initialize();
        canvas.run();
        
        requestFocus();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        addKeyListener(canvas);
        
        startAudio();
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                stopAudio();
            }
        });
    }    

    public void startAudio() {
        String filename = "resources/audio/background.mp3";
        try {
            FileInputStream fis     = new FileInputStream(filename);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
        }
        catch (Exception e) {
            System.out.println("Problem playing file " + filename);
            System.out.println(e);
        }

        // run in new thread to play in background
        new Thread() {
            public void run() {
                try { player.play(); }
                catch (Exception e) { System.out.println(e); }
            }
        }.start();
    }
    
    public void stopAudio() {
        if (player != null)
            player.close();
    }
    
}
