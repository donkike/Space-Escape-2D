

package ui;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

    private GameCanvas canvas;

    public MainFrame() {
        setTitle("Space Escape!");
        setSize(800, 800);

        canvas = new GameCanvas();
        add(canvas);

        canvas.run();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
