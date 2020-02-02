package ui;

import core.Game;
import game.Assets;

import javax.swing.*;
import java.awt.*;

public class MultiplayerNetworkPanel extends JPanel {

    private static MultiplayerNetworkPanel instance;
    private JButton joinBtn;
    private JButton hostBtn;
    private JButton backBtn;
    private JButton joinAiBtn;
    private JTextField hostnameField;

    public MultiplayerNetworkPanel() {
        instance = this;
    }

    /**
     * Gets the instance of the panel.
     *
     * @return The Panel.
     */
    public static MultiplayerNetworkPanel getInstance() {
        return instance;
    }

    /**
     * The hostname text field.
     * @return The text field.
     */
    public JTextField getHostnameField() {
        return hostnameField;
    }

    /**
     * Gets the join button.
     * @return The button.
     */
    public JButton getJoinBtn() {
        return joinBtn;
    }

    /**
     * Gets the host button.
     * @return The button.
     */
    public JButton getHostBtn() {
        return hostBtn;
    }

    /**
     * Gets the back button.
     * @return The button.
     */
    public JButton getBackBtn() {
        return backBtn;
    }

    /**
     * Gets the join ai button.
     * @return The button.
     */
    public JButton getJoinAiBtn() {
        return joinAiBtn;
    }

    /**
     * Creates a new panel.
     * @return The panel.
     */
    public MultiplayerNetworkPanel create() {
        MultiplayerNetworkPanel panel = this;

        panel.setOpaque(true);

        panel.setSize(Game.getInstance().getWindow().getWidth(), Game.getInstance().getWindow().getHeight());
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;

        final JLabel title = new JLabel("MULTIPLAYER");
        title.setFont(Assets.Fonts.TITLE_36);
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
        hostnameField = new JTextField("localhost");
        hostnameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        hostnameField.setFont(Assets.Fonts.DEFAULT);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(hostnameField, gbc);

        this.joinBtn = addButton("JOIN GAME", panel, 4);

        addSpace(panel, 5, null);

        this.joinAiBtn = addButton("JOIN GAME AS AI", panel, 6);

        addSpace(panel, 7, null);

        this.hostBtn = addButton("HOST GAME", panel, 8);

        addSpace(panel, 9, null);

        this.backBtn = addButton("BACK", panel, 10);

        // set joinBtn disabled by default
        this.joinBtn.setEnabled(true);

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
