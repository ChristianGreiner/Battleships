package core;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame implements Runnable {

    final int TARGET_FPS = 60;
    final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

    private Renderer renderer = null;
    private Game game;

    private int lastWindowWidth = 0;

    public void addGui(JPanel panel) {

        if (panel == null)
            return;

        panel.setSize(this.getWidth(), this.getHeight());
        this.add(panel);

        panel.repaint();
        this.repaint();
    }

    public void removeGui(JPanel panel) {
        panel.removeAll();
        this.remove(panel);
        this.repaint();
    }

    @Override
    public void run() {
        this.setVisible(true);
    }

    public void draw() {
    }

    private int lastWindowHeight = 0;

    public GameWindow(Game game, String title, Point size) {
        this.game = game;
        this.renderer = new Renderer();

        this.setLayout(null);
        this.setPreferredSize(new Dimension(size.x, size.y));
        this.setBackground(Color.WHITE);
        this.setTitle(title);
        this.setSize(size.x, size.y);
        this.setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);

        this.pack();
        this.setLocationRelativeTo(null);

        this.lastWindowHeight = this.getHeight();
        this.lastWindowWidth = this.getWidth();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
