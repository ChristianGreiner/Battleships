package scenes;

import ai.AiDifficulty;
import core.Game;
import core.GameWindow;
import game.Assets;
import game.Savegame;
import ui.GuiScene;
import ui.SingePlayerSettingsPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SinglePlayerSettingsScene extends Scene implements GuiScene, KeyListener {

    public SinglePlayerSettingsScene() {
        super("SinglePlayerSettingsScene");
    }

    @Override
    void onAdded() {
        super.onAdded();
    }


    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        SingePlayerSettingsPanel mapSelection = new SingePlayerSettingsPanel().create();

        mapSelection.getBackBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        });

        mapSelection.getNewGameBtn().addActionListener((e) -> {
            SinglePlayerScene scene = (SinglePlayerScene) Game.getInstance().getSceneManager().setActiveScene(SinglePlayerScene.class);

            int size = (int) mapSelection.getSizeSpinner().getValue();
            String difficulty = String.valueOf(mapSelection.getAiDifficultyCbox().getSelectedItem());
            difficulty = difficulty.replaceAll(" ", "");
            scene.initializeGame(size, AiDifficulty.valueOf(difficulty));
        });

        mapSelection.getLoadGameBtn().addActionListener((e) -> {
            Savegame savegame = Game.getInstance().getFileHandler().loadSavegame();
            SinglePlayerScene scene = (SinglePlayerScene) Game.getInstance().getSceneManager().setActiveScene(SinglePlayerScene.class);
            scene.initializeSavegame(savegame);
        });

        mapSelection.getSizeSpinner().addChangeListener(changeEvent -> {
            Game.getInstance().getSoundManager().playSfx(Assets.Sounds.BUTTON_HOVER);
        });

        mapSelection.getAiDifficultyCbox().addActionListener(actionEvent -> {
            Game.getInstance().getSoundManager().playSfx(Assets.Sounds.BUTTON_HOVER);
        });

        return mapSelection;
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
