package core;

/**
 * A interface used for update stuff like scenes.
 */
public interface Updatable {

    /**
     * Updates the object.
     *
     * @param deltaTime The delta time.
     */
    void update(double deltaTime);

    /**
     * Updates the object after the draw call.
     *
     * @param deltaTime The delta time.
     */
    void lateUpdate(double deltaTime);
}
