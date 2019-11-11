package graphics;

import core.Helper;
import core.Renderer;

import java.awt.*;

public class CreditsRenderer extends Renderer {

    private String[] lines;
    private Point startPos;
    private Point canvasSize;
    private int currentY;

    public CreditsRenderer(String[] lines, Point canvasSize) {
        this.lines = lines;
        this.startPos = new Point(canvasSize.x / 2, canvasSize.y);
        this.currentY = startPos.y;
        this.canvasSize = canvasSize;
    }

    @Override
    public void draw() {
        super.draw();

        this.canvasSize = new Point(this.getWidth(), this.getHeight());

        Graphics g = this.begin();

        for (int i = 0; i < this.lines.length; i++) {
            Rectangle rec = new Rectangle(0, currentY + i * 20, canvasSize.x, 32);
            Helper.drawCenteredString(g, this.lines[i], rec, new Font("TimesRoman", Font.PLAIN, 26));
        }

        currentY--;

        this.end();
    }
}
