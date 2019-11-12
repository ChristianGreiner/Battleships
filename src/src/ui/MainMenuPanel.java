package ui;

import core.Fonts;

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

    private JButton addButton(String title, JPanel container, int y) {
        JButton btn = new JButton();
        btn.setText(title);
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setFont(Fonts.DEFAULT);
        btn.setPreferredSize(new Dimension(320, 32));
        btn.setMaximumSize(new Dimension(320, 32));
        btn.setBorder(null);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        container.add(btn, gbc);

        return btn;
    }

    private void addSpace(JPanel container, int y, Insets insets) {
        final JPanel spacer = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.VERTICAL;
        if (insets != null)
            gbc.insets = insets;
        spacer.setBackground(Color.white);
        container.add(spacer, gbc);
    }

    public JPanel create(JPanel panel) {

        //panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;

        final JLabel title = new JLabel("BATTLESHIPS");
        title.setFont(Fonts.TITLE);
        title.setHorizontalAlignment(0);
        title.setHorizontalTextPosition(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(title, gbc);

        addSpace(panel, 2, null);

        this.singleplayerBtn = addButton("SINGLEPLAYER", panel, 3);

        addSpace(panel, 4, null);

        this.multiplayerBtn = addButton("MULTIPLAYER", panel, 5);

        addSpace(panel, 6, null);

        this.optionsBtn = addButton("OPTIONS", panel, 7);

        addSpace(panel, 8, null);

        this.creditsBtn = addButton("CREDITS", panel, 9);

        addSpace(panel, 10, null);

        this.exitBtn = addButton("EXIT", panel, 11);

        /*

        panel.add(Box.createVerticalGlue());

        JLabel title = new JLabel("BATTLESHIPS");
        title.setBackground(Color.RED);
        title.setHorizontalAlignment(JLabel.LEFT);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title, BorderLayout.WEST);
        panel.add(Box.createVerticalStrut(this.paddingSize));


        // -----------------------------------
        this.singleplayerBtn = addButton("SINGLEPLAYER");
        panel.add(this.singleplayerBtn, BorderLayout.CENTER);
        // -----------------------------------

        panel.add(Box.createVerticalStrut(this.paddingSize));

        // -----------------------------------
        this.multiplayerBtn = addButton("MULTIPLAYER");
        panel.add(this.multiplayerBtn, BorderLayout.CENTER);
        // -----------------------------------

        panel.add(Box.createVerticalStrut(this.paddingSize));

        // -----------------------------------
        this.creditsBtn = addButton("CREDITS");
        panel.add(this.creditsBtn, BorderLayout.CENTER);
        // -----------------------------------

        panel.add(Box.createVerticalStrut(this.paddingSize));

        // -----------------------------------
        this.optionsBtn = addButton("OPTIONS");
        panel.add(this.optionsBtn, BorderLayout.CENTER);
        // -----------------------------------

        panel.add(Box.createVerticalStrut(this.paddingSize));

        // -----------------------------------
        this.exitBtn = addButton("Exit");
        panel.add(this.exitBtn, BorderLayout.CENTER);
        // -----------------------------------

        panel.add(Box.createVerticalGlue());
*/
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
        btn.setFont(Fonts.DEFAULT);
        return btn;
    }
}
