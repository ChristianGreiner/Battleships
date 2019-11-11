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

    public GameWindow getWindow() {
        return window;
    }

    private static Game instance;

    // Managers
    private SceneManager sceneManager;

    public SoundManager getSoundManager() {
        return soundManager;
    }

    private SoundManager soundManager;

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
        this.sceneManager.addScene(new MainMenuScene());
        this.sceneManager.addScene(new CreditsScene());
        this.sceneManager.addScene(new GameScene());
        this.sceneManager.addScene(new TestScene());

        this.soundManager = new SoundManager();
    }

    public void start() {
        SwingUtilities.invokeLater(this.window = new GameWindow(this.title, this.gameSize));

        this.sceneManager.setActiveScene("MainMenuScene");

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
