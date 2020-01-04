package ui;

import core.Game;
import core.Helper;
import game.Assets;

import javax.swing.*;
import java.awt.*;

public class MultiplayerSettingsPanel extends JPanel {

    private static MultiplayerSettingsPanel instance;
    private final int paddingSize = 12;
    private JButton joinBtn, hostBtn, backBtn;
    private JTextField hostnameField;

    public JTextField getHostnameField() {
        return hostnameField;
    }

    public JButton getJoinBtn() {
        return joinBtn;
    }

    public JButton getHostBtn() {
        return hostBtn;
    }

    public JButton getBackBtn() {
        return backBtn;
    }

    public MultiplayerSettingsPanel() {
        instance = this;
    }

    public static MultiplayerSettingsPanel getInstance() {
        return instance;
    }

    public MultiplayerSettingsPanel create() {
        MultiplayerSettingsPanel panel = this;

        panel.setOpaque(true);

        panel.setSize(Game.getInstance().getWindow().getWidth(), Game.getInstance().getWindow().getHeight());
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;

        final JLabel title = new JLabel("MULTIPLAYER");
        title.setFont(Assets.Fonts.TITLE);
        title.setHorizontalAlignment(0);
        title.setHorizontalTextPosition(0);
        title.setForeground(Color.white);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(title, gbc);

        addSpace(panel, 2, null);

        // textbox
        hostnameField = new JTextField();
        hostnameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        hostnameField.setFont(Assets.Fonts.DEFAULT);
        hostnameField.getDocument().addDocumentListener((TextFieldDocumentListener) e -> {
            if(e.getDocument().getLength() <= 0) {
                this.joinBtn.setEnabled(false);
            } else {
                if(Helper.validateIp(hostnameField.getText())) {
                    this.joinBtn.setEnabled(true);
                } else {
                    this.joinBtn.setEnabled(false);
                }
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(hostnameField, gbc);

        this.joinBtn = addButton("JOIN GAME", panel, 4);

        addSpace(panel, 5, null);

        this.hostBtn = addButton("HOST GAME", panel, 6);

        addSpace(panel, 7, null);

        this.backBtn = addButton("BACK", panel, 8);

        // set joinBtn disabled by default
        this.joinBtn.setEnabled(false);

        return panel;
    }

    private JButton addButton(String title, JPanel container, int y) {
        JButton btn = UiBuilder.createButton(title, new Dimension(320, UiBuilder.BUTTON_HEIGHT));
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
        spacer.setOpaque(false);
        container.add(spacer, gbc);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(Assets.Images.BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}
