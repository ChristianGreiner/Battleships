package core;

import game.Assets;
import io.AssetsLoader;
import io.FileHandler;
import scenes.*;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;

public class Game implements Runnable {

    private static Game instance;

    public final int TARGET_FPS = 30;
    final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

    private SceneManager sceneManager;
    private SoundManager soundManager;
    private NetworkManager networkManager;
    private Options options = new Options();
    private FileHandler fileHandler = new FileHandler();
    private GameWindow window;
    private boolean isRunning;
    private String title;
    private Point gameSize;

    public Game(String title, Point size) {
        instance = this;
        this.gameSize = size;
        this.title = title;

        // Initialize all Scenes
        this.sceneManager = new SceneManager(this);
        this.sceneManager.addScene(new SplashScene());
        this.sceneManager.addScene(new MainMenuScene());
        this.sceneManager.addScene(new CreditsScene());
        this.sceneManager.addScene(new OptionsScene());
        this.sceneManager.addScene(new GameScene());
        this.sceneManager.addScene(new SinglePlayerSettingsScene());
        this.sceneManager.addScene(new ShipsSelectionScene());
        this.sceneManager.addScene(new SinglePlayerScene());
        this.sceneManager.addScene(new MultiplayerScene());

        this.soundManager = new SoundManager();
        this.networkManager = new NetworkManager();
    }

    public static Game getInstance() {
        return instance;
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public GameWindow getWindow() {
        return window;
    }

    public Options getOptions() {
        return options;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public FileHandler getFileHandler() {
        return fileHandler;
    }

    public void start() {

        Instant start = Instant.now();

        // initialize fonts
        Assets.init();

        // load config
        this.options = (Options) this.fileHandler.loadObject(Assets.Paths.OPTIONS);

        if (this.options == null)
            this.options = new Options();

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println(AssetsLoader.getAssetsLoaded() + " Assets loaded in " + timeElapsed + " ms");

        SwingUtilities.invokeLater(this.window = new GameWindow(this.title, this.gameSize));

        this.sceneManager.setActiveScene(MainMenuScene.class);


        this.isRunning = true;
        this.run();
    }

    @Override
    public void run() {
        long lastLoopTime = System.nanoTime();

        while (isRunning) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double deltaTime = updateLength / ((double) OPTIMAL_TIME);

            this.update(deltaTime);

            this.sceneManager.draw();
            this.window.draw();

            this.lateUpdate(deltaTime);

            try {
                long timeout = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
                Thread.sleep(timeout >= 0 ? timeout : 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(double deltaTime) {
        this.networkManager.update(deltaTime);
        this.sceneManager.update(deltaTime);
    }


    public void lateUpdate(double deltaTime) {
        this.sceneManager.lateUpdate(deltaTime);
    }
}
