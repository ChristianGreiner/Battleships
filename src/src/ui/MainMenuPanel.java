package ui;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {

    public JButton getSingleplayerBtn() {
        return singleplayerBtn;
    }

    public JButton getMultiplayerBtn() {
        return multiplayerBtn;
    }

    public JButton getCreditsBtn() {
        return creditsBtn;
    }

    public JButton getOptionsBtn() {
        return optionsBtn;
    }

    public JButton getExitBtn() {
        return exitBtn;
    }

    private JButton singleplayerBtn, multiplayerBtn, creditsBtn, optionsBtn, exitBtn;

    private final int paddingSize = 12;

    public JPanel create() {
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(Box.createVerticalGlue());

        // -----------------------------------
        this.singleplayerBtn = addButton("SINGLEPLAYER");
        panel.add(this.singleplayerBtn);
        // -----------------------------------

        panel.add(Box.createVerticalStrut(this.paddingSize));

        // -----------------------------------
        this.multiplayerBtn = addButton("MULTIPLAYER");
        panel.add(this.multiplayerBtn);
        // -----------------------------------

        panel.add(Box.createVerticalStrut(this.paddingSize));

        // -----------------------------------
        this.creditsBtn = addButton("CREDITS");
        panel.add(this.creditsBtn);
        // -----------------------------------

        panel.add(Box.createVerticalStrut(this.paddingSize));

        // -----------------------------------
        this.optionsBtn = addButton("OPTIONS");
        panel.add(this.optionsBtn);
        // -----------------------------------

        panel.add(Box.createVerticalStrut(this.paddingSize));

        // -----------------------------------
        this.exitBtn = addButton("Exit");
        panel.add(this.exitBtn);
        // -----------------------------------

        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JButton addButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setPreferredSize(new Dimension(320, 32));
        btn.setMaximumSize(new Dimension(320, 32));
        btn.setBorder(null);
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        return btn;
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
    }
}
