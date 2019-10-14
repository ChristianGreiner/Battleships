package core;

import scenes.SceneManager;
import scenes.TestScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JFrame {

    final int TARGET_FPS = 60;
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

    public Renderer getRenderer() {
        return renderer;
    }

    private Renderer renderer = null;
    private boolean isRunning;

    public Game(String title, Point size)
    {
        instance = this;

        this.sceneManager = new SceneManager(this);
        this.renderer = new Renderer();

        this.setLayout(null);
        this.setPreferredSize(new Dimension(size.x, size.y));
        this.setBackground(Color.WHITE);
        this.setTitle(title);
        this.setSize(size.x, size.y);
        this.setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.renderer.setBackground(Color.black);
        this.renderer.setSize(320, 320);
        this.renderer.setLocation(200, 200);
        this.renderer.setPreferredSize(new Dimension(320, 320));
        this.add(this.renderer, BorderLayout.CENTER);

        this.pack();
        this.setLocationRelativeTo(null);

        this.sceneManager.setActiveScene(new TestScene());
    }

    public void run() {
        this.isRunning = true;
        this.setVisible(true);
        this.gameLoop();
    }

    public void update(double deltaTime) {
        this.sceneManager.update(deltaTime);
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

            Graphics g = this.renderer.begin();
            if(g != null) {
                this.sceneManager.draw(this.renderer);
                this.renderer.end();
            }

            try {
                long timeout = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
                Thread.sleep(timeout >= 0 ? timeout : 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
