package scenes;

public abstract class Scene {

    private String name;
    private boolean updatePaused;

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

    public void onRemove() {
    }

    public void onSwitched() {
    }
}
