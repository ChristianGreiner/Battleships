package scenes;

import ai.AiDifficulty;
import core.Game;
import core.GameWindow;
import game.Assets;
import game.GameSession;
import game.GameSessionData;
import game.Savegame;
import ui.GameSettingsPanel;
import ui.GuiScene;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SinglePlayerSettingsScene extends Scene implements GuiScene, KeyListener, GameSession {

    public SinglePlayerSettingsScene() {
        super("SinglePlayerSettingsScene");
    }

    @Override
    public void onAdded() {
        super.onAdded();
    }

    private GameSessionData gameSessionData;
    private GameSettingsPanel settingsPanel;


    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        settingsPanel = new GameSettingsPanel().create();

        settingsPanel.getBackBtn().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        });

        settingsPanel.getNewGameBtn().addActionListener((e) -> {
            ShipsSelectionScene scene = (ShipsSelectionScene) Game.getInstance().getSceneManager().setActiveScene(ShipsSelectionScene.class);

            int size = (int) settingsPanel.getSizeSpinner().getValue();
            String difficulty = String.valueOf(settingsPanel.getAiDifficultyCbox().getSelectedItem());
            difficulty = difficulty.replaceAll(" ", "");

            scene.initializeGameSession(new GameSessionData(null, size, AiDifficulty.valueOf(difficulty)));
        });

        settingsPanel.getNewAIGameBtn().addActionListener((e) -> {
            ShipsSelectionScene scene = (ShipsSelectionScene) Game.getInstance().getSceneManager().setActiveScene(ShipsSelectionScene.class);

            int size = (int) settingsPanel.getSizeSpinner().getValue();
            String difficulty = String.valueOf(settingsPanel.getAiDifficultyCbox().getSelectedItem());
            difficulty = difficulty.replaceAll(" ", "");

            scene.initializeGameSession(new GameSessionData(null, size, AiDifficulty.valueOf(difficulty), true));
        });

        settingsPanel.getLoadGameBtn().addActionListener((e) -> {
            Savegame savegame = Game.getInstance().getGameFileHandler().loadSavegame();
            if(savegame != null) {
                SinglePlayerScene scene = (SinglePlayerScene) Game.getInstance().getSceneManager().setActiveScene(SinglePlayerScene.class);
                scene.initializeSavegame(savegame);
            }
        });

        settingsPanel.getSizeSpinner().addChangeListener(changeEvent -> {
            Game.getInstance().getSoundManager().playSfx(Assets.Sounds.BUTTON_HOVER);
        });

        settingsPanel.getAiDifficultyCbox().addActionListener(actionEvent -> {
            Game.getInstance().getSoundManager().playSfx(Assets.Sounds.BUTTON_HOVER);
        });

        return settingsPanel;
    }

    private void updateUi() {
        if(this.settingsPanel != null && this.gameSessionData != null) {
            this.settingsPanel.getSizeSpinner().setValue(this.gameSessionData.getMapSize());
            this.settingsPanel.getAiDifficultyCbox().setSelectedItem(this.gameSessionData.getAiDifficulty().toString().toUpperCase());

            AiDifficulty difficulty = this.gameSessionData.getAiDifficulty();

            this.settingsPanel.getAiDifficultyCbox().setSelectedIndex(difficulty.ordinal());
        }
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

    @Override
    public void initializeGameSession(GameSessionData data) {
        this.gameSessionData = data;
        updateUi();
    }
}
