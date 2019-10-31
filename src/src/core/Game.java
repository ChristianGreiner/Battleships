package core;

import scenes.*;

import javax.swing.*;
import java.awt.*;

public class Game implements Runnable {

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

    public GameWindow getWindow() {
        return window;
    }

    private GameWindow window;

    private boolean isRunning;
    private String title;
    private Point gameSize;

    public Game(String title, Point size)
    {
        instance = this;
        this.gameSize = size;
        this.title = title;

        this.sceneManager = new SceneManager(this);
        this.sceneManager.addScene(new TestScene());
        this.sceneManager.addScene(new TestScene2());
        this.sceneManager.addScene(new GameScene());
        this.sceneManager.addScene(new TestMenu());

    }

    public void start() {
        SwingUtilities.invokeLater(this.window = new GameWindow(this.title, this.gameSize));

        this.sceneManager.setActiveScene("TestScene");

        this.isRunning = true;
        this.run();
    }

    @Override
    public void run() {
        long lastLoopTime = System.nanoTime();

        while (isRunning)
        {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double deltaTime = updateLength / ((double)OPTIMAL_TIME);

            this.update(deltaTime);
            this.sceneManager.draw();
            this.window.draw();

            try {
                long timeout = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
                Thread.sleep(timeout >= 0 ? timeout : 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(double deltaTime) {
        this.sceneManager.update(deltaTime);
    }
}
