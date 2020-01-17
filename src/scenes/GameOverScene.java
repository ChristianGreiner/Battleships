package scenes;

import core.Game;
import core.GameWindow;
import game.GameSession;
import game.GameSessionData;
import game.PlayerType;
import ui.GameOverPanel;
import ui.GuiScene;

import javax.swing.*;

public class GameOverScene extends Scene implements GuiScene, GameSession {

    private GameSessionData gameSessionData;

    public void setWinner(PlayerType winner) {
        this.winner = winner;
    }

    private PlayerType winner;

    public GameOverScene() {
        super("GameOverScene");
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        String message;

        if(this.winner == PlayerType.Player)
            message = "YOU WIN";
        else
            message = "YOU LOSE";

        GameOverPanel panel = new GameOverPanel().create(message);

        panel.getBtnRestart().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(SinglePlayerSettingsScene.class);
        });

        panel.getBtnExit().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        });

        panel.invalidate();
        panel.repaint();

        return panel;
    }

    @Override
    public void sizeUpdated() {

    }

    @Override
    public void initializeGameSession(GameSessionData data) {
        this.gameSessionData = data;
    }
}
