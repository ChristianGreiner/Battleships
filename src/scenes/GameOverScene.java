package scenes;

import core.Game;
import core.GameWindow;
import game.GameSession;
import game.GameSessionData;
import game.PlayerType;
import game.SavegameType;
import network.NetworkType;
import ui.GameOverPanel;
import ui.GuiScene;

import javax.swing.*;

public class GameOverScene extends Scene implements GuiScene, GameSession {

    private GameSessionData gameSessionData;
    private GameOverPanel panel;

    public GameOverScene() {
        super("GameOverScene");
    }

    public void setWinner(PlayerType winner) {
        this.panel.updateWinner(winner);
    }

    public void setWinner(NetworkType winner) {
        this.panel.updateWinner(winner);
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {


        panel = new GameOverPanel().create();

        panel.getBtnRestart().addActionListener((e) -> {
            if (this.gameSessionData != null) {
                if (this.gameSessionData.getGameType() == SavegameType.Singeplayer || this.gameSessionData.getGameType() == SavegameType.SingeplayerAi) {
                    SinglePlayerSettingsScene settingsScene = (SinglePlayerSettingsScene) Game.getInstance().getSceneManager().setActiveScene(SinglePlayerSettingsScene.class);
                    settingsScene.initializeGameSession(this.gameSessionData);
                } else if (this.gameSessionData.getGameType() == SavegameType.Multiplayer || this.gameSessionData.getGameType() == SavegameType.MultiplayerAi) {
                    Game.getInstance().getSceneManager().setActiveScene(MultiplayerNetworkScene.class);
                    Game.getInstance().getNetworkManager().stopServer();
                } else {
                    Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
                }
            } else {
                Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
            }
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
