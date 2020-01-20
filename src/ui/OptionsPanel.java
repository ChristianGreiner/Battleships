package ui;

import core.Game;
import game.Assets;

import javax.swing.*;
import java.awt.*;

public class OptionsPanel extends JPanel {

    private JSlider sfxSlider;
    private JSlider musicSlider;
    private JButton btnSave;
    private JComboBox fpsCBox;

    public OptionsPanel() {
    }

    public JSlider getSfxVolumeSlider() {
        return sfxSlider;
    }

    public JSlider getMusicVolumeSlider() {
        return musicSlider;
    }

    public JButton getSaveBtn() {
        return btnSave;
    }

    public JComboBox getFpsCBox() {
        return fpsCBox;
    }

    public OptionsPanel create() {

        OptionsPanel mainContainer = this;
        mainContainer.setBackground(UiBuilder.TRANSPARENT);
        mainContainer.setLayout(new GridBagLayout());

        JLabel titleLbl = new JLabel();
        titleLbl.setHorizontalAlignment(0);
        titleLbl.setText("OPTIONS");
        titleLbl.setFont(Assets.Fonts.TITLE_36);
        titleLbl.setHorizontalAlignment(0);
        titleLbl.setHorizontalTextPosition(0);
        titleLbl.setVerticalAlignment(1);
        titleLbl.setVerticalTextPosition(1);
        titleLbl.setForeground(Color.WHITE);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 10, 5);
        mainContainer.add(titleLbl, gbc);

        JPanel optionsContainer = new JPanel();
        optionsContainer.setLayout(new GridBagLayout());
        optionsContainer.setBackground(UiBuilder.BLACK_ALPHA);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainContainer.add(optionsContainer, gbc);

        JPanel sfxContainer = new JPanel();
        sfxContainer.setBackground(UiBuilder.TRANSPARENT);
        sfxContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        optionsContainer.add(sfxContainer, gbc);

        JLabel sfxLbl = new JLabel();
        sfxLbl.setText("Effects Volume:");
        sfxLbl.setBackground(UiBuilder.TRANSPARENT);
        sfxLbl.setPreferredSize(new Dimension(136, 24));
        sfxLbl.setFont(Assets.Fonts.DEFAULT);
        sfxLbl.setForeground(Color.WHITE);
        sfxContainer.add(sfxLbl);

        this.sfxSlider = new JSlider();
        this.sfxSlider.setValue((int) (Game.getInstance().getOptions().getSfxVolume() * 100f));
        this.sfxSlider.setPreferredSize(new Dimension(180, 18));
        sfxContainer.add(this.sfxSlider);

        JPanel musicContainer = new JPanel();
        musicContainer.setBackground(UiBuilder.TRANSPARENT);
        musicContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        optionsContainer.add(musicContainer, gbc);

        JLabel musicLbl = new JLabel();
        musicLbl.setBackground(UiBuilder.TRANSPARENT);
        musicLbl.setText("Music Volume:");
        musicLbl.setPreferredSize(new Dimension(136, 24));
        musicLbl.setFont(Assets.Fonts.DEFAULT);
        musicLbl.setForeground(Color.WHITE);
        musicContainer.add(musicLbl);

        this.musicSlider = new JSlider();
        this.musicSlider.setValue((int) (Game.getInstance().getOptions().getMusicVolume() * 100f));
        this.musicSlider.setPreferredSize(new Dimension(180, 18));
        musicContainer.add(this.musicSlider);

        JPanel fpsContainer = new JPanel();
        fpsContainer.setBackground(UiBuilder.TRANSPARENT);
        fpsContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        optionsContainer.add(fpsContainer, gbc);

        JLabel fpsLbl = new JLabel();
        fpsLbl.setBackground(UiBuilder.TRANSPARENT);
        fpsLbl.setText("FPS:");
        fpsLbl.setPreferredSize(new Dimension(136, 24));
        fpsLbl.setFont(Assets.Fonts.DEFAULT);
        fpsLbl.setForeground(Color.WHITE);
        fpsContainer.add(fpsLbl);

        this.fpsCBox = new JComboBox();
        this.fpsCBox.setPreferredSize(new Dimension(180, 24));
        this.fpsCBox.setFont(Assets.Fonts.DEFAULT);

        final DefaultComboBoxModel aiComboboxModel = new DefaultComboBoxModel();
        aiComboboxModel.addElement("30");
        aiComboboxModel.addElement("60");
        fpsCBox.setModel(aiComboboxModel);

        this.fpsCBox.setSelectedItem(Integer.toString(Game.getInstance().getOptions().getFps()));

        fpsContainer.add(this.fpsCBox);

        JPanel btnContainer = new JPanel();
        btnContainer.setBackground(UiBuilder.TRANSPARENT);
        btnContainer.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        mainContainer.add(btnContainer, gbc);

        this.btnSave = UiBuilder.createButton("SAVE", new Dimension(180, 32));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        btnContainer.add(this.btnSave, gbc);

        return mainContainer;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(Assets.Images.BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}
