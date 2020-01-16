package core;

import game.Assets;
import io.AssetsLoader;
import io.FileHandler;
import scenes.*;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;

/**
 * The main game class with gameloop.
 */
public class Game implements Runnable {

    private static Game instance;

    public final int TARGET_FPS = 25;
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

        this.sceneManager = new SceneManager(this);
        this.soundManager = new SoundManager();
        this.networkManager = new NetworkManager();
    }

    /**
     * Gets the game instance.
     * @return The game instance.
     */
    public static Game getInstance() {
        return instance;
    }

    /**
     * Gets the network manager
     * @return The network manager.
     */
    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    /**
     * Gets the scene manager.
     * @return The scene manager.
     */
    public SceneManager getSceneManager() {
        return sceneManager;
    }

    /**
     * Gets the game window container (JFrame) of swing.
     * @return The game window
     */
    public GameWindow getWindow() {
        return window;
    }

    /**
     * Gets the options of the game.
     * @return The options.
     */
    public Options getOptions() {
        return options;
    }

    /**
     * Gets the sound manager.
     * @return The sound manager.
     */
    public SoundManager getSoundManager() {
        return soundManager;
    }

    /**
     * Gets the file handler.
     * @return The file handler.
     */
    public FileHandler getFileHandler() {
        return fileHandler;
    }

    private void initializeScenes() {

        // Initialize all Scenes
        this.sceneManager.addScene(new SplashScene());
        this.sceneManager.addScene(new MainMenuScene());
        this.sceneManager.addScene(new CreditsScene());
        this.sceneManager.addScene(new OptionsScene());
        this.sceneManager.addScene(new SinglePlayerSettingsScene());
        this.sceneManager.addScene(new ShipsSelectionScene());
        this.sceneManager.addScene(new SingePlayerScene());
        this.sceneManager.addScene(new MultiplayerNetworkScene());
        this.sceneManager.addScene(new MultiplayerHostSettingsScene());
        this.sceneManager.addScene(new WaitingForPlayerScene());
        this.sceneManager.addScene(new MultiplayerScene());

    }

    /**
     * Starts the game and the gameloop.
     */
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

        initializeScenes();

        this.sceneManager.setActiveScene(MainMenuScene.class);


        this.isRunning = true;
        this.run();
    }

    /**
     * Gets called by the start method. Used by the thread.
     */
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

    /**
     * Updates the game.
     * @param deltaTime
     */
    public void update(double deltaTime) {
        this.sceneManager.update(deltaTime);
    }


    /**
     * Updates the game lately. Gets called after the regular update method.
     * @param deltaTime
     */
    public void lateUpdate(double deltaTime) {
        this.sceneManager.lateUpdate(deltaTime);
    }
}
