package ui;

import game.Assets;

import javax.swing.*;
import java.awt.*;

public class SingePlayerSettingsPanel extends JPanel {

    private JSpinner sizeSpn;
    private JButton loadGameBtn;
    private JButton newGameBtn;

    public JButton getLoadGameBtn() {
        return loadGameBtn;
    }
    public JButton getNewGameBtn() {
        return newGameBtn;
    }

    public JComboBox getAiDifficultyCbox() {
        return aiDifficultyCbox;
    }

    private JComboBox aiDifficultyCbox;

    public JSpinner getSizeSpinner() {
        return sizeSpn;
    }

    public JButton getBackBtn() {
        return backBtn;
    }

    private JButton backBtn;

    public SingePlayerSettingsPanel create() {

        SingePlayerSettingsPanel panel = this;

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
        titleLbl.setText("SINGLEPLAYER");
        titleLbl.setForeground(Color.WHITE);
        titleLbl.setFont(Assets.Fonts.TITLE);

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
        sizeSpn.setPreferredSize(new Dimension(128, 32));
        spinnerContainer.add(sizeSpn);

        final JLabel crossLbl = new JLabel();
        crossLbl.setText("x");
        crossLbl.setForeground(Color.WHITE);
        crossLbl.setFont(Assets.Fonts.DEFAULT_24);
        spinnerContainer.add(crossLbl);

        final JSpinner sizeCopySpn = new JSpinner(spinnerModel);
        sizeCopySpn.setPreferredSize(new Dimension(128, 28));
        sizeCopySpn.setFont(Assets.Fonts.DEFAULT);
        spinnerContainer.add(sizeCopySpn);

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
        aiDifficultyCbox.setPreferredSize(new Dimension(220, 28));
        aiDifficultyCbox.setFont(Assets.Fonts.DEFAULT);
        final DefaultComboBoxModel aiComboboxModel = new DefaultComboBoxModel();
        aiComboboxModel.addElement("Full Retard");
        aiComboboxModel.addElement("Easy");
        aiComboboxModel.addElement("Medium");
        aiComboboxModel.addElement("Hard");
        aiComboboxModel.addElement("Extreme");
        aiDifficultyCbox.setModel(aiComboboxModel);
        aiSelectionContainer.add(aiDifficultyCbox);

        final JPanel spacer = new JPanel();
        spacer.setBackground(new Color(0, 0, 0, 155));
        spacer.setPreferredSize(new Dimension(360, 10));
        spacer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(spacer, gbc);

        final JPanel btnContainer = new JPanel();
        btnContainer.setLayout(new GridBagLayout());
        btnContainer.setBackground(new Color(0, 0, 0, 155));
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

        newGameBtn = UiBuilder.createButton("NEW GAME", new Dimension(360, UiBuilder.BUTTON_HEIGHT));
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

        loadGameBtn = UiBuilder.createButton("LOAD GAME", new Dimension(360, UiBuilder.BUTTON_HEIGHT));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        btnContainer.add(loadGameBtn, gbc);

        final JPanel spacer6 = new JPanel();
        spacer6.setOpaque(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        btnContainer.add(spacer6, gbc);

        backBtn = UiBuilder.createButton("BACK", new Dimension(360, UiBuilder.BUTTON_HEIGHT));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        btnContainer.add(backBtn, gbc);


        /*final JPanel btnContainer = new JPanel();
        btnContainer.setBackground(new Color(0, 0, 0, 155));
        btnContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(btnContainer, gbc);

        backBtn = UiBuilder.createButton("BACK", new Dimension(160, UiBuilder.BUTTON_HEIGHT));
        btnContainer.add(backBtn);

        newGameBtn = UiBuilder.createButton("CONTINUE", new Dimension(160, UiBuilder.BUTTON_HEIGHT));
        btnContainer.add(newGameBtn);*/

        return panel;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(Assets.Images.BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}
