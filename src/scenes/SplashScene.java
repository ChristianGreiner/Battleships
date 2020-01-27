package scenes;

import core.Game;
import core.GameWindow;
import core.Updatable;
import game.Assets;
import ui.GuiScene;

import javax.swing.*;
import java.awt.*;

public class SplashScene extends Scene implements GuiScene, Updatable {

    private int timeCounter;

    private int maxCount = 100;

    public SplashScene() {
        super("SplashScene");
    }

    @Override
    public void onAdded() {
        super.onAdded();

        Game.getInstance().getSoundManager().playSfx(Assets.Sounds.SEATBELT_SFX);
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(Box.createVerticalGlue());

        JLabel title = new JLabel("BITTE LEGEN SIE IHREN ANSCHNALLGURT AN!");
        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(Assets.Fonts.TITLE);
        title.setForeground(Color.black);
        title.setPreferredSize(new Dimension(panel.getWidth(), 32));
        title.setMaximumSize(new Dimension(panel.getWidth(), 32));
        panel.add(title, BorderLayout.CENTER);

        panel.add(Box.createVerticalGlue());


        return panel;
    }

    @Override
    public void sizeUpdated() {

    }

    @Override
    public void update(double deltaTime) {

        if (this.timeCounter >= this.maxCount) {
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        }

        this.timeCounter++;
    }

    @Override
    public void lateUpdate(double deltaTime) {

    }
}

