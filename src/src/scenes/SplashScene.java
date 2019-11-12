package scenes;

import core.Fonts;
import core.Game;
import core.GameWindow;
import core.Updatable;
import ui.GuiScene;

import javax.swing.*;
import java.awt.*;

public class SplashScene extends Scene implements GuiScene, Updatable {

    private int timeCounter;

    public SplashScene() {
        super("SplashScene");
    }

    @Override
    void onAdded() {
        super.onAdded();
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow, JPanel panel) {

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(Box.createVerticalGlue());

        JLabel title = new JLabel("BITTE LEGEN SIE IHREN ANSCHNALLGURT AN!");
        title.setBackground(Color.RED);
        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(Fonts.TITLE);
        title.setPreferredSize(new Dimension(panel.getWidth(), 32));
        title.setMaximumSize(new Dimension(panel.getWidth(), 32));
        panel.add(title, BorderLayout.CENTER);

        panel.add(Box.createVerticalGlue());


        return panel;
    }

    @Override
    public void update(double deltaTime) {

        if (this.timeCounter >= 100) {
            Game.getInstance().getSceneManager().setActiveScene("MainMenuScene");
        }

        System.out.println(timeCounter);
        this.timeCounter++;
    }
}

