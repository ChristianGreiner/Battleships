package core;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame implements Runnable {

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

    public GameWindow(String title, Point size) {

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
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
