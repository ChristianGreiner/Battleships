package scenes;

import core.Drawable;
import core.Game;
import core.Updatable;
import ui.GuiScene;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.lang.reflect.Type;
import java.util.HashMap;

public class SceneManager {

    private Scene activeScene;
    private Game game;
    private JPanel activeGui;

    private HashMap<Type, Scene> scenes = new HashMap<>();

    public Scene getActiveScene() {
        return activeScene;
    }

    public SceneManager(Game game) {
        this.game = game;
    }

    public void addScene(Scene scene) {
        this.scenes.put(scene.getClass(), scene);
    }

    public Scene getScene(Type type) {
        return this.scenes.get(type);
    }

    public void setActiveScene(Type type) {
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
            JPanel panel = new JPanel();
            panel.setSize(this.game.getWindow().getWidth(), this.game.getWindow().getHeight());
            this.activeGui = ((GuiScene) scene).buildGui(this.game.getWindow(), panel);
            this.game.getWindow().addGui(this.activeGui);

            this.activeGui.repaint();
            this.activeGui.validate();

            this.game.getWindow().repaint();
            this.game.getWindow().validate();
            this.game.getWindow().pack();
        }

        if (this.activeScene instanceof KeyListener) {
            this.game.getWindow().addKeyListener((KeyListener) this.activeScene);
        }

        this.activeScene.onAdded();
    }

    public void update(double deltaTime) {

        if (this.activeScene != null) {
            if (this.activeScene instanceof Updatable) {
                ((Updatable)this.activeScene).update(deltaTime);
            }
        }
    }

    public void draw() {
        if (this.activeScene != null) {
            if (this.activeScene instanceof Drawable) {
                ((Drawable) this.activeScene).draw();
            }
        }
    }
}
