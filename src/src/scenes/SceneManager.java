package scenes;

import core.Drawable;
import core.Game;
import core.Updatable;
import ui.GuiScene;

import javax.swing.*;
import java.util.HashMap;

public class SceneManager {

    public Scene getActiveScene() {
        return activeScene;
    }

    /*public void setActiveScene(Scene scene) {

       /*if(scene instanceof MouseListener)
            this.game.getWindow().removeMouseListener((MouseListener) this.activeScene);

        if(scene instanceof KeyListener)
            this.game.getWindow().removeKeyListener((KeyListener) this.activeScene);

        if(scene instanceof GuiScene){
        this.game.getWindow().addGui(((GuiScene)scene).buildGui());
        }

        if(this.activeScene != null)
            this.activeScene.onRemove();

        this.activeScene = scene;
        this.activeScene.onAdded();

        if(scene instanceof MouseListener)
            this.game.getWindow().addMouseListener((MouseListener) scene);

        if(scene instanceof KeyListener)
            this.game.getWindow().addKeyListener((KeyListener) scene);
    }*/

    private Scene activeScene;
    private Game game;
    private JPanel activeGui;

    private HashMap<String, Scene> scenes = new HashMap<>();

    public SceneManager(Game game) {
        this.game = game;
    }

    public void addScene(Scene scene) {
        this.scenes.put(scene.getName(), scene);
    }

    public Scene getScene(String name) {
        return this.scenes.get(name);
    }

    public void setActiveScene(String name) {
        Scene scene = this.getScene(name);

        if (this.activeScene != null) {
            if (this.activeGui != null) {
                this.game.getWindow().removeGui(this.activeGui);
            }
            this.activeScene.onRemove();
            this.activeScene = null;
        }

        // new scene assigned
        this.activeScene = scene;

        if (this.activeScene instanceof GuiScene) {
            this.activeGui = ((GuiScene) scene).buildGui();
            this.game.getWindow().addGui(this.activeGui);
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
