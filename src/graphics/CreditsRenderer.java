package graphics;

import core.Game;
import core.Helper;
import core.Renderer;
import game.Assets;
import game.Credit;

import java.awt.*;
import java.util.ArrayList;

/**
 * Renders the credit scene.
 */
public class CreditsRenderer extends Renderer {

    private ArrayList<Credit> credits;
    private Point startPos;
    private Point canvasSize;
    private int currentY;

    /**
     * The constructor of the renderer.
     *
     * @param credits    A list of credits.
     * @param canvasSize The size of the canvas.
     */
    public CreditsRenderer(ArrayList<Credit> credits, Point canvasSize) {
        this.credits = credits;
        this.startPos = new Point(canvasSize.x / 2, canvasSize.y);
        this.currentY = startPos.y;
        this.canvasSize = canvasSize;
    }

    @Override
    public void draw() {
        super.draw();

        this.canvasSize = new Point(this.getWidth(), this.getHeight());

        Graphics g = this.begin();

        g.setColor(Color.white);
        g.drawImage(Assets.Images.BACKGROUND, 0, 0, canvasSize.x, canvasSize.y, this);

        for (int i = 0; i < this.credits.size(); i++) {
            Credit c = this.credits.get(i);
            Rectangle rec = new Rectangle(0, currentY + (i * 48), canvasSize.x, c.getFont().getSize());
            Helper.drawCenteredString(g, c.getText(), rec, c.getFont());
        }

        if (Game.getInstance().getTargetFps() == 60)
            currentY -= 1;
        else
            currentY -= 3;

        this.end();
    }
}
