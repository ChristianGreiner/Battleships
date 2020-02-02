package ui;

import game.Assets;

import javax.swing.*;
import java.awt.*;

public class GameSettingsPanel extends JPanel {

    private JSpinner sizeSpn;
    private JButton loadGameBtn;
    private JButton newGameBtn;
    private JButton newAIGameBtn;
    private JComboBox aiDifficultyCbox;
    private JButton backBtn;

    /**
     * Gets the load button.
     *
     * @return The button.
     */
    public JButton getLoadGameBtn() {
        return loadGameBtn;
    }

    /**
     * Gets the new game button.
     * @return The button.
     */
    public JButton getNewGameBtn() {
        return newGameBtn;
    }

    /**
     * Gets the ai difficulty JComboBox.
     * @return The JComboBox.
     */
    public JComboBox getAiDifficultyCbox() {
        return aiDifficultyCbox;
    }

    /**
     * Gets the size spinner.
     * @return The spinner.
     */
    public JSpinner getSizeSpinner() {
        return sizeSpn;
    }

    /**
     * Gets the back button.
     * @return The button.
     */
    public JButton getBackBtn() {
        return backBtn;
    }

    /**
     * Gets the new ai game button.
     * @return The button.
     */
    public JButton getNewAIGameBtn() {
        return newAIGameBtn;
    }

    /**
     * Creates a new panel.
     * @return The panel.
     */
    public GameSettingsPanel create() {

        GameSettingsPanel panel = this;

        panel.setLayout(new GridBagLayout());
        final JPanel spacer1 = new JPanel();
        spacer1.setBackground(UiBuilder.TRANSPARENT);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(spacer1, gbc);

        final JPanel spacer2 = new JPanel();
        spacer2.setBackground(UiBuilder.TRANSPARENT);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(spacer2, gbc);

        final JPanel titleContainer = new JPanel();
        titleContainer.setBackground(UiBuilder.TRANSPARENT);
        titleContainer.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(titleContainer, gbc);

        JLabel titleLbl = new JLabel();
        titleLbl.setHorizontalAlignment(0);
        titleLbl.setHorizontalTextPosition(0);
        titleLbl.setText("GAME SETTINGS");
        titleLbl.setForeground(Color.WHITE);
        titleLbl.setFont(Assets.Fonts.TITLE_36);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        titleContainer.add(titleLbl, gbc);

        final JPanel sizeLblContainer = new JPanel();
        sizeLblContainer.setBackground(new Color(0, 0, 0, 155));
        sizeLblContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(sizeLblContainer, gbc);

        final JLabel sizeLbl = new JLabel();
        sizeLbl.setText("SIZE:");
        sizeLbl.setForeground(Color.white);
        sizeLbl.setFont(Assets.Fonts.DEFAULT);
        sizeLblContainer.add(sizeLbl);

        final JPanel spinnerContainer = new JPanel();
        spinnerContainer.setBackground(new Color(0, 0, 0, 155));
        spinnerContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(spinnerContainer, gbc);

        SpinnerModel spinnerModel = new SpinnerNumberModel(10, 5, 30, 1);
        sizeSpn = new JSpinner(spinnerModel);
        sizeSpn.setFont(Assets.Fonts.DEFAULT);
        sizeSpn.setPreferredSize(new Dimension(64, 32));
        spinnerContainer.add(sizeSpn);

        final JPanel aiDifficultLblContainer = new JPanel();
        aiDifficultLblContainer.setBackground(new Color(0, 0, 0, 155));
        aiDifficultLblContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(aiDifficultLblContainer, gbc);

        final JLabel aiDifficultLbl = new JLabel();
        aiDifficultLbl.setText("AI DIFFICULTY:");
        aiDifficultLbl.setForeground(Color.white);
        aiDifficultLbl.setFont(Assets.Fonts.DEFAULT);
        aiDifficultLblContainer.add(aiDifficultLbl);

        final JPanel aiSelectionContainer = new JPanel();
        aiSelectionContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        aiSelectionContainer.setBackground(new Color(0, 0, 0, 155));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(aiSelectionContainer, gbc);

        aiDifficultyCbox = new JComboBox();
        aiDifficultyCbox.setPreferredSize(new Dimension(128, 32));
        aiDifficultyCbox.setFont(Assets.Fonts.DEFAULT);
        final DefaultComboBoxModel aiComboboxModel = new DefaultComboBoxModel();
        aiComboboxModel.addElement("Full Retard");
        aiComboboxModel.addElement("Easy");
        aiComboboxModel.addElement("Medium");
        aiComboboxModel.addElement("Hard");
        aiComboboxModel.addElement("Extreme");
        aiDifficultyCbox.setModel(aiComboboxModel);
        aiSelectionContainer.add(aiDifficultyCbox);
        aiDifficultyCbox.setSelectedItem("Medium");

        final JPanel spacer = new JPanel();
        spacer.setBackground(new Color(0, 0, 0, 155));
        spacer.setPreferredSize(new Dimension(256, 10));
        spacer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(spacer, gbc);

        final JPanel btnContainer = new JPanel();
        btnContainer.setLayout(new GridBagLayout());
        btnContainer.setBackground(UiBuilder.TRANSPARENT);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(btnContainer, gbc);

        final JPanel spacer3 = new JPanel();
        spacer3.setOpaque(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        btnContainer.add(spacer3, gbc);

        newGameBtn = UiBuilder.createButton("NEW GAME", new Dimension(266, UiBuilder.BUTTON_HEIGHT));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        btnContainer.add(newGameBtn, gbc);

        final JPanel spacer5 = new JPanel();
        spacer5.setOpaque(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        btnContainer.add(spacer5, gbc);

        newAIGameBtn = UiBuilder.createButton("NEW AI GAME", new Dimension(266, UiBuilder.BUTTON_HEIGHT));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        btnContainer.add(newAIGameBtn, gbc);

        final JPanel spacer7 = new JPanel();
        spacer7.setOpaque(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        btnContainer.add(spacer7, gbc);

        loadGameBtn = UiBuilder.createButton("LOAD GAME", new Dimension(266, UiBuilder.BUTTON_HEIGHT));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        btnContainer.add(loadGameBtn, gbc);

        final JPanel spacer6 = new JPanel();
        spacer6.setOpaque(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.VERTICAL;
        btnContainer.add(spacer6, gbc);

        backBtn = UiBuilder.createButton("BACK", new Dimension(266, UiBuilder.BUTTON_HEIGHT));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        btnContainer.add(backBtn, gbc);

        return panel;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(Assets.Images.BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}
