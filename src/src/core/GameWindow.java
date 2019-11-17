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

    private boolean isFullscreen = false;
    private static final int VIRTUAL_WIDTH = 1280;
    private static final int VIRTUAL_HEIGHT = 720;
    private static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH / (float)VIRTUAL_HEIGHT;
    private JPanel rootPanel;

    public void addGui(JPanel panel) {
        this.add(panel);
        this.rootPanel = panel;
        this.rootPanel.setSize(Game.getInstance().getWindow().getWidth(), Game.getInstance().getWindow().getHeight());
        this.rootPanel.repaint();
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
                Point size = calcAspectRatio();
                if (rootPanel != null)
                    rootPanel.setSize(size.x, size.y);

                setSize(size.x, size.y);
            }
        });

        this.addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent e) {
                Point size = calcAspectRatio();
            }
        });
    }

    private Point calcAspectRatio() {

        if(isFullscreen) {
            return new Point(getWidth(), getHeight());
        }

        float aspectRatio = (float)getWidth() / (float)getHeight();
        float scale = 1f;
        Point crop = new Point(0, 0);

        if(aspectRatio > ASPECT_RATIO)
        {
            scale = (float)getHeight() / (float)VIRTUAL_HEIGHT;
            crop.x = (int)((getWidth() - VIRTUAL_WIDTH * scale) / 2f);
        }
        else if(aspectRatio < ASPECT_RATIO)
        {
            scale = (float)getWidth() / (float)VIRTUAL_WIDTH;
            crop.y = (int)((getHeight() - VIRTUAL_HEIGHT * scale) / 2f);
        }
        else
        {
            scale = (float)getWidth() / (float)VIRTUAL_WIDTH;
        }

        int w = (int)((float)VIRTUAL_WIDTH * scale);
        int h = (int)((float)VIRTUAL_HEIGHT * scale);

        return new Point(w, h);
    }

    public void setFullscreen() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        this.isFullscreen = true;
    }

    public void draw() {
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
    }
}
