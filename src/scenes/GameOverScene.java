package scenes;

import core.Game;
import core.GameWindow;
import game.GameSession;
import game.GameSessionData;
import game.PlayerType;
import network.NetworkType;
import ui.GameOverPanel;
import ui.GuiScene;

import javax.swing.*;

public class GameOverScene extends Scene implements GuiScene, GameSession {

    private GameSessionData gameSessionData;

    public void setWinner(PlayerType winner) {
        this.panel.updateWinner(winner);
    }

    public void setWinner(NetworkType winner) {
        this.panel.updateWinner(winner);
    }

    private GameOverPanel panel;

    public GameOverScene() {
        super("GameOverScene");
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {


        panel = new GameOverPanel().create();

        panel.getBtnRestart().addActionListener((e) -> {
            if(this.gameSessionData != null) {
                SinglePlayerSettingsScene settingsScene = (SinglePlayerSettingsScene)Game.getInstance().getSceneManager().setActiveScene(SinglePlayerSettingsScene.class);
                settingsScene.initializeGameSession(this.gameSessionData);
            }
            else Game.getInstance().getSceneManager().setActiveScene(MultiplayerNetworkScene.class);

        });

        panel.getBtnExit().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        });

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
