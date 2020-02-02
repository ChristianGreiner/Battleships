package scenes;

import ai.AiDifficulty;
import core.Game;
import core.GameWindow;
import game.*;
import ui.GameSettingsPanel;
import ui.GuiScene;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SinglePlayerSettingsScene extends Scene implements GuiScene, KeyListener, GameSession {

    private GameSessionData gameSessionData;
    private GameSettingsPanel settingsPanel;

    public SinglePlayerSettingsScene() {
        super("SinglePlayerSettingsScene");
    }

    @Override
    public void onSwitched() {
        super.onSwitched();
    }

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

            scene.initializeGameSession(new GameSessionData(null, size, AiDifficulty.valueOf(difficulty), SavegameType.Singeplayer));
        });

        settingsPanel.getNewAIGameBtn().addActionListener((e) -> {
            ShipsSelectionScene scene = (ShipsSelectionScene) Game.getInstance().getSceneManager().setActiveScene(ShipsSelectionScene.class);

            int size = (int) settingsPanel.getSizeSpinner().getValue();
            String difficulty = String.valueOf(settingsPanel.getAiDifficultyCbox().getSelectedItem());
            difficulty = difficulty.replaceAll(" ", "");

            scene.initializeGameSession(new GameSessionData(null, size, AiDifficulty.valueOf(difficulty), SavegameType.SingeplayerAi));
        });

        settingsPanel.getLoadGameBtn().addActionListener((e) -> {
            try {
                Savegame savegame = Game.getInstance().getGameFileHandler().loadSavegame();
                if (savegame.getSavegameType() == SavegameType.Singeplayer) {
                    SinglePlayerScene scene = (SinglePlayerScene) Game.getInstance().getSceneManager().setActiveScene(SinglePlayerScene.class);
                    scene.initializeSavegame((SingleplayerSavegame) savegame);
                } else if (savegame.getSavegameType() == SavegameType.SingeplayerAi) {
                    SinglePlayerAIScene scene = (SinglePlayerAIScene) Game.getInstance().getSceneManager().setActiveScene(SinglePlayerAIScene.class);
                    scene.initializeSavegame((SingleplayerAiSavegame) savegame);
                }
                /*
                if (!savegame.isNetworkGame()) {
                    if (savegame.getAi() != null && savegame.getPlayerAi() != null) {

                    } else {
                        SinglePlayerScene scene = (SinglePlayerScene) Game.getInstance().getSceneManager().setActiveScene(SinglePlayerScene.class);
                        scene.initializeSavegame(savegame);
                    }
                } else
                    JOptionPane.showMessageDialog(Game.getInstance().getWindow(), "This save game is a multiplayer savegame.", "Can't load savegame.", JOptionPane.ERROR_MESSAGE);*/

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(Game.getInstance().getWindow(), "This file seems to be corrupted", "Can't load savegame.", JOptionPane.ERROR_MESSAGE);
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
        if (this.settingsPanel != null && this.gameSessionData != null) {
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
