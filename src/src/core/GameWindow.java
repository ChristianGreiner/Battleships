package core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public class GameWindow extends JFrame implements Runnable {

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private JPanel rootPanel;

    public void addGui(JPanel panel) {
        this.add(panel);
        this.rootPanel = panel;
        this.rootPanel.repaint();
        this.repaint();
    }

    public void removeGui(JPanel panel) {
        panel.removeAll();
        this.rootPanel.removeAll();
        this.remove(rootPanel);
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
        this.setMinimumSize(new Dimension(1280, 720));

        this.pack();
        this.setLocationRelativeTo(null);

        this.getRootPane().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                if (rootPanel != null)
                    rootPanel.setSize(getWidth(), getHeight());
            }
        });

        this.addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent e) {
                if (rootPanel != null)
                    rootPanel.setSize(getWidth(), getHeight());
            }
        });
    }

    public void fullscreen() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
    }
}
