package graphics;

import core.Renderer;
import game.Map;

import java.awt.*;

public class MapRenderer extends Renderer {

    private Map map;

    public MapRenderer(Map map) {
        this.map = map;
    }

    @Override
    public void draw() {
        super.draw();
        Graphics g = this.begin();

        g.fillRect(20, 20, 20, 20);

        this.end();
    }
}
