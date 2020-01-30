package scenes;

import ai.AiDifficulty;
import core.Game;
import core.GameWindow;
import game.GameSessionData;
import game.Savegame;
import ui.GameSettingsPanel;
import ui.GuiScene;

import javax.swing.*;

public class MultiplayerHostSettingsScene extends Scene implements GuiScene {

    public MultiplayerHostSettingsScene() {
        super("MultiplayerHostSettingsScene");
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        GameSettingsPanel settings = new GameSettingsPanel().create();

        settings.getNewGameBtn().addActionListener((e) -> {
            WaitingForPlayerScene scene = (WaitingForPlayerScene)Game.getInstance().getSceneManager().setActiveScene(WaitingForPlayerScene.class);

            int size = (int) settings.getSizeSpinner().getValue();
            scene.initializeGameSession(new GameSessionData(null, size, null));
        });

        settings.getNewAIGameBtn().addActionListener((e) -> {
            WaitingForPlayerScene scene = (WaitingForPlayerScene)Game.getInstance().getSceneManager().setActiveScene(WaitingForPlayerScene.class);
            int size = (int) settings.getSizeSpinner().getValue();

            String difficulty = String.valueOf(settings.getAiDifficultyCbox().getSelectedItem());
            difficulty = difficulty.replaceAll(" ", "");
            scene.initializeGameSession(new GameSessionData(null, size, AiDifficulty.valueOf(difficulty), true));
        });


        settings.getLoadGameBtn().addActionListener((e) -> {
            Savegame savegame = Game.getInstance().getGameFileHandler().loadSavegame();
            if(savegame != null) {
                WaitingForPlayerScene scene = (WaitingForPlayerScene)Game.getInstance().getSceneManager().setActiveScene(WaitingForPlayerScene.class);
                scene.initializeGameSession(new GameSessionData(savegame));
            }
        });

        settings.getBackBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(MultiplayerNetworkScene.class);
        });

        return settings;
    }

    @Override
    public void sizeUpdated() {

    }
}
