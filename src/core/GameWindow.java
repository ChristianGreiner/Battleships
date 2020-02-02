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

    public static final int VIRTUAL_WIDTH = 1280;
    public static final int VIRTUAL_HEIGHT = 720;
    public static final int START_MAPSIZE = 384;
    private static final float ASPECT_RATIO = (float) VIRTUAL_WIDTH / (float) VIRTUAL_HEIGHT;
    private boolean isFullscreen = false;
    private JPanel rootPanel;
    private GuiScene guiScene;
    private Dimension lastWindowSize;
    private boolean isMaximized;

    public GameWindow(String title, Point size) {

        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setTitle(title);
        this.setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.setMinimumSize(new Dimension(640, 360));
        this.setPreferredSize(new Dimension(size.x, size.y));

        this.lastWindowSize = this.getSize();

        this.pack();
        this.setLocationRelativeTo(null);

        this.addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent event) {
                lastWindowSize = calcAspectRatio();

                isMaximized = isMaximized(event.getNewState());
                boolean wasMaximized = isMaximized(event.getOldState());

                if (isMaximized && !wasMaximized) {
                    isFullscreen = true;

                } else if (wasMaximized && !isMaximized) {
                    isFullscreen = false;
                }

                if (rootPanel != null) {
                    rootPanel.setSize(lastWindowSize);
                    rootPanel.repaint();
                }
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                lastWindowSize = calcAspectRatio();

                if (rootPanel != null) {
                    rootPanel.setSize(lastWindowSize);
                    rootPanel.repaint();
                    revalidate();

                    if (guiScene instanceof GuiScene) {
                        guiScene.sizeUpdated();
                        Game.getInstance().getWindow().repaint();
                        Game.getInstance().getWindow().revalidate();
                    }
                }

                setSize(lastWindowSize);
            }
        });
    }

    private static boolean isMaximized(int state) {
        return (state & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH;
    }

    /**
     * Adds a gui element to the game window.
     *
     * @param panel    The gui panel.
     * @param guiScene The gui scene.
     */
    public void addGui(JPanel panel, GuiScene guiScene) {
        this.add(panel);
        this.rootPanel = panel;
        this.guiScene = guiScene;
        this.rootPanel.setSize(getSize());
        this.rootPanel.repaint();
        this.setPreferredSize(this.lastWindowSize);
        this.revalidate();
    }

    /**
     * Removes a gui element from the game window.
     *
     * @param panel The gui panel.
     */
    public void removeGui(JPanel panel) {
        this.guiScene = null;
        panel.removeAll();
        this.rootPanel.removeAll();
        this.remove(rootPanel);
        this.repaint();
        this.revalidate();
    }

    @Override
    public void run() {
        this.setVisible(true);
    }

    /**
     * Calculate the aspect ratio for the window.
     *
     * @return The new size of the window.
     */
    public Dimension calcAspectRatio() {

        if (this.isFullscreen) {
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
     * Calcs the ratio / size for the map builder
     *
     * @return The new size.
     */
    public Dimension getBuildMapRenderPanelSize() {
        Dimension currentSize = Game.getInstance().getWindow().getSize();
        float ratio = (float) currentSize.width / VIRTUAL_WIDTH;
        return new Dimension((int) ((START_MAPSIZE * 1.5) * ratio), (int) (START_MAPSIZE * ratio));
    }

    /**
     * Calcs the ratio / size for the map.
     *
     * @return The size.
     */
    public Dimension getMapRenderPanelSize() {
        Dimension currentSize = Game.getInstance().getWindow().getSize();
        float ratio = (float) currentSize.width / VIRTUAL_WIDTH;
        return new Dimension((int) (START_MAPSIZE * ratio), (int) (START_MAPSIZE * ratio));
    }

    /**
     * Set the game window to fullscreen.
     *
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
     *
     * @param graphics
     */
    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
    }
}