package graphics;

import core.Helper;
import core.Renderer;
import game.Assets;
import game.Credit;

import java.awt.*;
import java.util.ArrayList;

public class CreditsRenderer extends Renderer {

    private ArrayList<Credit> credits;
    private Point startPos;
    private Point canvasSize;
    private int currentY;
    private int alpha = 255;

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

        g.drawImage(Assets.Images.BACKGROUND, 0, 0, canvasSize.x, canvasSize.y, this);
        g.setColor(new Color(0, 0, 0, alpha));
        g.fillRect(0, 0, canvasSize.x, canvasSize.y);

        g.setColor(Color.white);

        for (int i = 0; i < this.credits.size(); i++) {
            Credit c = this.credits.get(i);
            Rectangle rec = new Rectangle(0, currentY + (i * 48), canvasSize.x, c.getFont().getSize());
            Helper.drawCenteredString(g, c.getText(), rec, c.getFont());
        }

        if (alpha > 0)
            alpha -= 5;

        currentY -= 2;

        this.end();
    }
}
