package scenes;

public abstract class Scene {

    private String name;

    public Scene(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isUpdatePaused() {
        return updatePaused;
    }

    public void setUpdatePaused(boolean updatePaused) {
        this.updatePaused = updatePaused;
    }

    private boolean updatePaused;

    void onRemove() {
    }

    void onAdded() {
    }
}
