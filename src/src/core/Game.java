package core;

import game.GameWindow;
import scenes.SceneManager;
import scenes.TestScene;

import java.awt.*;

public class Game implements Runnable {

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

    public GameWindow getWindow() {
        return window;
    }

    private GameWindow window;

    private Renderer renderer = null;
    private boolean isRunning;

    public Game(String title, Point size)
    {
        instance = this;

        this.window = new GameWindow(this, title, size);
        this.sceneManager = new SceneManager(this);

        this.sceneManager.setActiveScene(new TestScene());
    }

    public void start() {
        this.isRunning = true;
        this.window.setVisible(true);
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

            Graphics g = this.renderer.begin();
            if(g != null) {
                this.sceneManager.draw(this.renderer);
                this.getWindow().paint(g);
                //this.renderer.end();
            }

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
