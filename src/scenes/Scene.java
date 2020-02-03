package scenes;

/**
 * Abstract class for scenes.
 */
public abstract class Scene {

    private String name;
    private boolean updatePaused;

    /**
     * Constructor for scene.
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the scene.
     * @return Returns the name of the scene.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns whether or not the update is paused.
     * @return Returns whether or not the update is paused.
     */
    public boolean isUpdatePaused() {
        return updatePaused;
    }

    /**
     * Sets update paused
     * @param updatePaused Whether or not the update needs to be paused.
     */
    public void setUpdatePaused(boolean updatePaused) {
        this.updatePaused = updatePaused;
    }

    /**
     * Event handler for the removal of a scene.
     */
    public void onRemove() {
    }

    /**
     * Event handler for the switch event.
     */
    public void onSwitched() {
    }
}
