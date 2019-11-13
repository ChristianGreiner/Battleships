package scenes;

import core.Drawable;
import core.GameWindow;
import core.Updatable;
import game.GameState;
import game.Map;
import game.PlayerType;
import graphics.MapRenderer;
import ui.GuiScene;
import ui.SinglePlayerPanel;

import javax.swing.*;

public class SinglePlayerScene extends Scene implements Updatable, Drawable, GuiScene {

    private Map playerMap;
    private Map aiMap;
    private PlayerType playerTurn = PlayerType.Player;
    private GameState gameState = GameState.Started;
    private MapRenderer playerMapRenderer;

    public SinglePlayerScene() {
        super("SinglePlayer");

        this.playerMap = new Map(20);

        this.playerMapRenderer = new MapRenderer(this.playerMap);
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void draw() {
        this.playerMapRenderer.draw();
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow, JPanel panel) {
        SinglePlayerPanel sp = new SinglePlayerPanel(this.playerMapRenderer);
        panel = sp.create(panel);

        return panel;
    }
}
