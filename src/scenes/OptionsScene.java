package scenes;

import ai.AiSpeed;
import core.Game;
import core.GameWindow;
import game.Assets;
import ui.GuiScene;
import ui.OptionsPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class for the options scene.
 */
public class OptionsScene extends Scene implements GuiScene, KeyListener {

    /**
     * Constructor for the options scene.
     */
    public OptionsScene() {
        super("OptionsScene");
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public void onSwitched() {
        super.onSwitched();
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        OptionsPanel options = new OptionsPanel().create();

        options.getSfxVolumeSlider().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                Game.getInstance().getOptions().setSfxVolume(options.getSfxVolumeSlider().getValue() * 0.01f);
            }
        });

        options.getMusicVolumeSlider().addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                Game.getInstance().getOptions().setMusicVolume(options.getMusicVolumeSlider().getValue() * 0.01f);
                Game.getInstance().getSoundManager().getBackgroundPlayer().setVolume(options.getMusicVolumeSlider().getValue() * 0.01f);
            }
        });

        options.getSaveBtn().addActionListener((e) -> {
            Game.getInstance().getOptions().setFps(Integer.parseInt(options.getFpsCBox().getSelectedItem().toString()));
            Game.getInstance().getOptions().setAiSpeed(AiSpeed.valueOf(options.getAiSeedCBox().getSelectedItem().toString()));
            Game.getInstance().setTargetFps(Game.getInstance().getOptions().getFps());

            Game.getInstance().getGameFileHandler().writeObject(Game.getInstance().getOptions(), Assets.Paths.OPTIONS);

            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        });

        return options;
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

    /**
     * Returns to the main menu scene when ESC was pressed.
     * @param e the keyEvent.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Game.getInstance().getGameFileHandler().writeObject(Game.getInstance().getOptions(), Assets.Paths.OPTIONS);
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        }
    }
}
