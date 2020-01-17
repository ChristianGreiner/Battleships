package scenes;

import core.Game;
import core.GameWindow;
import game.GameSessionData;
import ui.GameSettingsPanel;
import ui.GuiScene;

import javax.swing.*;

public class MultiplayerHostSettingsScene extends Scene implements GuiScene {

    public MultiplayerHostSettingsScene() {
        super("MultiplayerHostSettingsScene");
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        GameSettingsPanel settings = new GameSettingsPanel().create(true);

        settings.getNewGameBtn().addActionListener((e) -> {
            Game.getInstance().getNetworkManager().startServer(5555);
            WaitingForPlayerScene scene = (WaitingForPlayerScene)Game.getInstance().getSceneManager().setActiveScene(WaitingForPlayerScene.class);

            int size = (int) settings.getSizeSpinner().getValue();
            scene.initializeGameSession(new GameSessionData(null, size, null));
        });

        settings.getLoadGameBtn().addActionListener((e) -> {

        });

        settings.getBackBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(MultiplayerNetworkScene.class);
        });

        /*settings.getSizeSpinner().addChangeListener(changeEvent -> {
            Game.getInstance().getSoundManager().playSfx(Assets.Sounds.BUTTON_HOVER);
        });

        settings.getAiDifficultyCbox().addActionListener(actionEvent -> {
            Game.getInstance().getSoundManager().playSfx(Assets.Sounds.BUTTON_HOVER);
        });*/

        return settings;
    }

    @Override
    public void sizeUpdated() {

    }
}
