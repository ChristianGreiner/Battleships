package core;

import scenes.SceneManager;
import scenes.TestScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JFrame implements KeyListener {

    final int TARGET_FPS = 30;
    final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

    public static Game getInstance(){
        return instance;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    // Managers
    private SceneManager sceneManager;

    private static Game instance;

    private Image doubleBufferImage = null;
    private Graphics doubleBufferGraphics = null;
    private boolean isRunning;
    private int counter = 0;

    public Game(String title, Point size)
    {
        instance = this;

        this.sceneManager = new SceneManager(this);

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(size.x, size.y));
        this.setBackground(Color.white);
        this.setTitle(title);
        this.setSize(size.x, size.y);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.pack();
        this.setLocationRelativeTo(null);

        this.sceneManager.setActiveScene(new TestScene());
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        System.out.println("Key typed");
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    public void run() {
        this.isRunning = true;
        this.setVisible(true);
        this.gameLoop();
    }

    public void update(double deltaTime) {
        this.sceneManager.update(deltaTime);
    }

    @Override
    public void paint(final Graphics g) {

        //super.paint(g);

        if (doubleBufferImage == null) {
            doubleBufferImage = createImage(getWidth(), getHeight());
        }
        doubleBufferGraphics = doubleBufferImage.getGraphics();
        doubleBufferGraphics.setColor(Color.black);
        doubleBufferGraphics.fillRect(0, 0, getWidth(), getHeight()) ;

        // render game
        draw(doubleBufferImage.getGraphics());

        this.sceneManager.draw(doubleBufferImage.getGraphics());

        // render double buffer
        g.drawImage(doubleBufferImage, 0, 0, null);
    }

    public void draw(final Graphics g) {

        g.setColor(Color.white);
        g.drawString("Counter:" + counter, 100, 100);

        counter++;
    }

    private void gameLoop() {
        long lastLoopTime = System.nanoTime();

        while (isRunning)
        {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double deltaTime = updateLength / ((double)OPTIMAL_TIME);

            this.update(deltaTime);
            this.repaint();

            try {
                Thread.sleep( (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
