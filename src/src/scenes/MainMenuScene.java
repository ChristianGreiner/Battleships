package scenes;

import core.GameWindow;
import core.Updatable;
import ui.GuiScene;

import javax.swing.*;

import static java.awt.Component.CENTER_ALIGNMENT;

public class MainMenuScene extends Scene implements Updatable, GuiScene {

    public MainMenuScene() {
        super("MainMenuScene");
    }

    @Override
    public void update(double deltaTime) {
    }

    private JButton spBtn, mpBtn, creditsBtn, optBtn, quitBtn;

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());

        this.spBtn = new JButton();
        this.spBtn.setSize(32, 300);
        this.spBtn.setText("Test");
        this.spBtn.setAlignmentX(CENTER_ALIGNMENT);
        this.spBtn.setContentAreaFilled(false);
        this.spBtn.setBorder(null);
        this.spBtn.addActionListener(
                (e) -> {
                    System.out.println("singleplayerbtn gedruekt");
                }
        );

        panel.add(spBtn, CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(20));

        this.mpBtn = new JButton();
        this.mpBtn.setSize(32, 300);
        this.mpBtn.setText("Test");
        this.mpBtn.setAlignmentX(CENTER_ALIGNMENT);
        this.mpBtn.setContentAreaFilled(false);
        this.mpBtn.setBorder(null);
        this.mpBtn.addActionListener(
                (e) -> {
                    System.out.println("singleplayerbtn gedruekt");
                }
        );

        panel.add(mpBtn, CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(20));

        return panel;
    }
}