package core;

import game.Assets;
import io.AssetsLoader;
import io.GameFileHandler;
import network.NetworkManager;
import scenes.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The main game class with gameloop.
 */
public class Game implements Runnable {

    private static Game instance;

    private int targetFps = 60;
    private long optimalTime = 1000000000 / targetFps;
    private SceneManager sceneManager;
    private SoundManager soundManager;
    private NetworkManager networkManager;
    private Options options = new Options();
    private GameFileHandler gameFileHandler = new GameFileHandler();
    private GameWindow window;
    private boolean isRunning;
    private String title;
    private Point gameSize;
    private Logger logger = Logger.getLogger("MyLog");
    private FileHandler fh;

    public Game(String title, Point size) {
        instance = this;
        this.gameSize = size;
        this.title = title;

        this.sceneManager = new SceneManager(this);
        this.soundManager = new SoundManager();
        this.networkManager = new NetworkManager();

        try {

            // This block configure the logger with handler and formatter
            this.fh = new FileHandler(System.getProperty("user.dir") + "/logs.txt");
            this.logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            this.fh.setFormatter(formatter);

            // the following statement is used to log any messages
            logger.info("Start Game");

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the game instance.
     *
     * @return The game instance.
     */
    public static Game getInstance() {
        return instance;
    }

    public int getTargetFps() {
        return targetFps;
    }

    public void setTargetFps(int targetFps) {
        this.targetFps = targetFps;
        this.optimalTime = 1000000000 / targetFps;
    }

    public Logger getLogger() {
        return logger;
    }

    /**
     * Gets the network manager
     *
     * @return The network manager.
     */
    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    /**
     * Gets the scene manager.
     *
     * @return The scene manager.
     */
    public SceneManager getSceneManager() {
        return sceneManager;
    }

    /**
     * Gets the game window container (JFrame) of swing.
     *
     * @return The game window
     */
    public GameWindow getWindow() {
        return window;
    }

    /**
     * Gets the options of the game.
     *
     * @return The options.
     */
    public Options getOptions() {
        return options;
    }

    /**
     * Gets the sound manager.
     *
     * @return The sound manager.
     */
    public SoundManager getSoundManager() {
        return soundManager;
    }

    /**
     * Gets the file handler.
     *
     * @return The file handler.
     */
    public GameFileHandler getGameFileHandler() {
        return gameFileHandler;
    }

    private void initializeScenes() {

        // Initialize all Scenes
        this.sceneManager.addScene(new MainMenuScene());
        this.sceneManager.addScene(new CreditsScene());
        this.sceneManager.addScene(new OptionsScene());
        this.sceneManager.addScene(new SinglePlayerSettingsScene());
        this.sceneManager.addScene(new ShipsSelectionScene());
        this.sceneManager.addScene(new SinglePlayerScene());
        this.sceneManager.addScene(new SinglePlayerAIScene());
        this.sceneManager.addScene(new MultiplayerNetworkScene());
        this.sceneManager.addScene(new MultiplayerHostSettingsScene());
        this.sceneManager.addScene(new WaitingForPlayerScene());
        this.sceneManager.addScene(new MultiplayerScene());
        this.sceneManager.addScene(new MultiplayerAIScene());
        this.sceneManager.addScene(new GameOverScene());
    }

    /**
     * Starts the game and the gameloop.
     */
    public void start() {

        Instant start = Instant.now();

        // initialize fonts
        Assets.init();

        // load config
        this.options = (Options) this.gameFileHandler.loadObject(Assets.Paths.OPTIONS);
        if (this.options == null) {
            Game.getInstance().getGameFileHandler().writeObject(Game.getInstance().getOptions(), Assets.Paths.OPTIONS);
        }

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
     * The main game loop. Updates and draws the game.
     */
    @Override
    public void run() {
        long lastLoopTime = System.nanoTime();

        while (isRunning) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double deltaTime = updateLength / ((double) this.optimalTime);

            this.update(deltaTime);

            this.sceneManager.draw();

            this.lateUpdate(deltaTime);

            try {
                long timeout = (lastLoopTime - System.nanoTime() + this.optimalTime) / 1000000;
                Thread.sleep(timeout >= 0 ? timeout : 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the game.
     *
     * @param deltaTime
     */
    public void update(double deltaTime) {
        this.sceneManager.update(deltaTime);
    }


    /**
     * Updates the game lately. Gets called after the regular update method.
     *
     * @param deltaTime
     */
    public void lateUpdate(double deltaTime) {
        this.sceneManager.lateUpdate(deltaTime);
    }
}
