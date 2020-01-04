package graphics;

import core.Renderer;
import game.Map;

import java.awt.*;


public class MapBuilderRenderer extends MapRenderer {

    public MapBuilderRenderer(Map map) {
        super(map);
    }

    @Override
    public void draw() {
        Graphics g = super.beginRenderingBegin();

        g.setColor(Color.BLACK);
        g.fillRect(300, 20, 20, 20);

        super.endRendering();
    }
}
