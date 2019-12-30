package graphics;

import core.Renderer;
import game.Map;

import java.awt.*;


public class MapBuilderRenderer extends Renderer {

    public MapBuilderRenderer(Map map) {

    }

    @Override
    public void draw() {
        super.draw();

        Graphics g = this.begin();

        g.fillRect(20, 20, 20, 20);

        this.end();
    }
}
