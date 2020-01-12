package scenes;

import ai.AiDifficulty;
import core.Game;
import core.GameWindow;
import game.Assets;
import game.Savegame;
import ui.GameSettingsPanel;
import ui.GuiScene;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SinglePlayerSettingsScene extends Scene implements GuiScene, KeyListener {

    public SinglePlayerSettingsScene() {
        super("SinglePlayerSettingsScene");
    }

    @Override
    public void onAdded() {
        super.onAdded();
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        GameSettingsPanel settings = new GameSettingsPanel().create(false);

        settings.getBackBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        });

        settings.getNewGameBtn().addActionListener((e) -> {
            ShipsSelectionScene scene = (ShipsSelectionScene) Game.getInstance().getSceneManager().setActiveScene(ShipsSelectionScene.class);

            int size = (int) settings.getSizeSpinner().getValue();
            String difficulty = String.valueOf(settings.getAiDifficultyCbox().getSelectedItem());
            difficulty = difficulty.replaceAll(" ", "");
            scene.initializeGame(size, AiDifficulty.valueOf(difficulty));
        });

        settings.getLoadGameBtn().addActionListener((e) -> {
            Savegame savegame = Game.getInstance().getFileHandler().loadSavegame();
            if(savegame != null) {
                SingePlayeScene scene = (SingePlayeScene) Game.getInstance().getSceneManager().setActiveScene(SingePlayeScene.class);
                scene.initializeSavegame(savegame);
            }
        });

        settings.getSizeSpinner().addChangeListener(changeEvent -> {
            Game.getInstance().getSoundManager().playSfx(Assets.Sounds.BUTTON_HOVER);
        });

        settings.getAiDifficultyCbox().addActionListener(actionEvent -> {
            Game.getInstance().getSoundManager().playSfx(Assets.Sounds.BUTTON_HOVER);
        });

        return settings;
    }

    @Override
    public void sizeUpdated() {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        }
    }
}
