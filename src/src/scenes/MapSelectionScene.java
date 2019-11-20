package scenes;

import core.Game;
import core.GameWindow;
import game.Assets;
import game.Map;
import ui.GuiScene;
import ui.MapSelectionPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MapSelectionScene extends Scene implements GuiScene, KeyListener {

    public MapSelectionScene() {
        super("MapSelectionScene");
    }

    @Override
    void onAdded() {
        super.onAdded();
    }


    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        MapSelectionPanel mapSelection = new MapSelectionPanel().create();

        mapSelection.getBackBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        });

        mapSelection.getContinueBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(SinglePlayerScene.class, new Map((int)mapSelection.getSizeSpinner().getValue()));
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
