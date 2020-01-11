package core;

import ui.GuiScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

/**
 * The drawable game window.
 */
public class GameWindow extends JFrame implements Runnable {

    private static final int VIRTUAL_WIDTH = 1280;
    private static final int VIRTUAL_HEIGHT = 720;
    private static final float ASPECT_RATIO = (float) VIRTUAL_WIDTH / (float) VIRTUAL_HEIGHT;
    private boolean isFullscreen = false;
    private JPanel rootPanel;
    private GuiScene guiScene;
    private Dimension lastWindowSize;

    public GameWindow(String title, Point size) {

        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setTitle(title);
        this.setSize(size.x, size.y);
        this.setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.setMinimumSize(new Dimension(1280, 720));

        this.lastWindowSize = this.getSize();

        this.pack();
        this.setLocationRelativeTo(null);

        this.getRootPane().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                lastWindowSize = getSize();
                Dimension size = getSize();//calcAspectRatio();

                if (rootPanel != null) {
                    rootPanel.setSize(size);
                    rootPanel.repaint();

                    if (guiScene instanceof GuiScene)
                        guiScene.sizeUpdated();
                }
            }
        });

        this.addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent e) {
                //Dimension size = calcAspectRatio();
                lastWindowSize = getSize();

                if (rootPanel != null) {
                    rootPanel.setSize(lastWindowSize);
                    rootPanel.repaint();
                }
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    /**
     * Adds a gui element to the game window.
     * @param panel The gui panel.
     * @param guiScene The gui scene.
     */
    public void addGui(JPanel panel, GuiScene guiScene) {
        this.add(panel);
        this.rootPanel = panel;
        this.guiScene = guiScene;
        this.rootPanel.setSize(getSize());
        this.rootPanel.repaint();
        this.setPreferredSize(this.lastWindowSize);
    }

    /**
     * Removes a gui element from the game window.
     * @param panel The gui panel.
     */
    public void removeGui(JPanel panel) {
        this.guiScene = null;
        panel.removeAll();
        this.rootPanel.removeAll();
        this.remove(rootPanel);
        this.repaint();
    }

    @Override
    public void run() {
        this.setVisible(true);
    }

    private Dimension calcAspectRatio() {

        if (isFullscreen) {
            return getSize();
        }

        float aspectRatio = (float) getWidth() / (float) getHeight();
        float scale = 1f;
        Point crop = new Point(0, 0);

        if (aspectRatio > ASPECT_RATIO) {
            scale = (float) getHeight() / (float) VIRTUAL_HEIGHT;
            crop.x = (int) ((getWidth() - VIRTUAL_WIDTH * scale) / 2f);
        } else if (aspectRatio < ASPECT_RATIO) {
            scale = (float) getWidth() / (float) VIRTUAL_WIDTH;
            crop.y = (int) ((getHeight() - VIRTUAL_HEIGHT * scale) / 2f);
        } else {
            scale = (float) getWidth() / (float) VIRTUAL_WIDTH;
        }

        int w = (int) ((float) VIRTUAL_WIDTH * scale);
        int h = (int) ((float) VIRTUAL_HEIGHT * scale);

        return new Dimension(w, h);
    }

    /**
     * Set the game window to fullscreen.
     * @param state
     */
    public void setFullscreen(boolean state) {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        this.isFullscreen = state;
    }

    /**
     * Draws the game window.
     */
    public void draw() {
    }

    /**
     * Paints the game window.
     * @param graphics
     */
    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
    }
}
