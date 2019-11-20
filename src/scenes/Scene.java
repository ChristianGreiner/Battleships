package scenes;

public abstract class Scene {

    private String name;

    public Scene(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void onRemove() {
    }

    void onAdded() {
    }
}
