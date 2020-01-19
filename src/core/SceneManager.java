package core;

import scenes.Scene;
import ui.GuiScene;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Manages all game scenes.
 */
public class SceneManager {

    private Scene activeScene;
    private Game game;
    private JPanel activeGui;

    private HashMap<Type, Scene> scenes = new HashMap<>();

    public SceneManager(Game game) {
        this.game = game;
    }

    /**
     * Gets the active running scene.
     * @return The active scene.
     */
    public Scene getActiveScene() {
        return activeScene;
    }

    /**
     * Adds a new scene to the game.
     * @param scene The scene.
     */
    public void addScene(Scene scene) {
        this.scenes.put(scene.getClass(), scene);
    }

    /**
     * Gets a scene by its type.
     * @param type The type of the scene.
     * @return The scene.
     */
    public Scene getScene(Type type) {
        return this.scenes.get(type);
    }

    /**
     * Sets a scene active. The scene has to be added first.
     * If not, an exception gehts thrown.
     * @param type The type of the scene.
     * @return The activated scene.
     */
    public Scene setActiveScene(Type type) {
        if (!this.scenes.containsKey(type)) {
            try {
                throw new Exception("Scene not found");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Scene scene = this.getScene(type);

        if (this.activeScene != null) {
            if (this.activeScene instanceof KeyListener) {
                this.game.getWindow().removeKeyListener((KeyListener) this.activeScene);
            }

            if (this.activeGui != null) {
                this.game.getWindow().removeGui(this.activeGui);
            }

            this.activeScene.onRemove();
            this.activeScene = null;
        }

        // new scene assigned
        this.activeScene = scene;

        if (this.activeScene instanceof GuiScene) {

            GuiScene guiScene = (GuiScene) this.activeScene;

            this.activeGui = guiScene.buildGui(this.game.getWindow());

            this.game.getWindow().addGui(this.activeGui, guiScene);
            this.game.getWindow().pack();
        }

        if (this.activeScene instanceof KeyListener) {
            this.game.getWindow().addKeyListener((KeyListener) this.activeScene);
        }

        this.activeScene.onAdded();

        return this.activeScene;
    }

    /**
     * Updates the active scenes if it uses the updateable interface.
     * @param deltaTime The current delta time.
     */
    public void update(double deltaTime) {
        if (this.activeScene != null) {
            if (this.activeScene instanceof Updatable && !this.activeScene.isUpdatePaused()) {
                ((Updatable) this.activeScene).update(deltaTime);
            }
        }
    }

    /**
     * Updates the active scene lately if it uses the updateable interface.
     * @param deltaTime The current delta time.
     */
    public void lateUpdate(double deltaTime) {
        if (this.activeScene != null) {
            if (this.activeScene instanceof Updatable && !this.activeScene.isUpdatePaused()) {
                ((Updatable) this.activeScene).lateUpdate(deltaTime);
            }
        }
    }

    /**
     * Draws the active scene if it uses the drawable interface.
     */
    public void draw() {
        if (this.activeScene != null) {
            if (this.activeScene instanceof Drawable) {
                ((Drawable) this.activeScene).draw();
            }
        }
    }
}
