package core;

/**
 * A interface used for update stuff like scenes.
 */
public interface Updatable {
    void update(double deltaTime);

    void lateUpdate(double deltaTime);
}
