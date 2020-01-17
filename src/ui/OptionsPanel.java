package ui;

import core.Game;
import game.Assets;

import javax.swing.*;
import java.awt.*;

public class OptionsPanel extends JPanel {

    private JSlider sfxVolumeSlider;
    private JSlider musicVolumeSlider;
    private JButton saveBtn;

    public OptionsPanel() {
    }

    public JSlider getSfxVolumeSlider() {
        return sfxVolumeSlider;
    }

    public JSlider getMusicVolumeSlider() {
        return musicVolumeSlider;
    }

    public JButton getSaveBtn() {
        return saveBtn;
    }

    public OptionsPanel create() {

        OptionsPanel panel = this;

        panel.setLayout(new GridBagLayout());
        final JPanel sfxVolumeContainer = new JPanel();
        sfxVolumeContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        sfxVolumeContainer.setBackground(new Color(0, 0, 0, 155));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(sfxVolumeContainer, gbc);

        final JLabel sfxVolumeLbl = new JLabel("Sfx Volume:");
        sfxVolumeLbl.setPreferredSize(new Dimension(128, 24));
        sfxVolumeLbl.setFont(Assets.Fonts.DEFAULT);
        sfxVolumeLbl.setForeground(Color.WHITE);
        sfxVolumeContainer.add(sfxVolumeLbl);

        sfxVolumeSlider = new JSlider();
        sfxVolumeSlider.setValue((int) (Game.getInstance().getOptions().getSfxVolume() * 100f));
        sfxVolumeSlider.setPreferredSize(new Dimension(160, 18));
        sfxVolumeContainer.add(sfxVolumeSlider);

        final JLabel titleLbl = new JLabel("OPTIONS");
        titleLbl.setFont(Assets.Fonts.TITLE_36);
        titleLbl.setHorizontalAlignment(0);
        titleLbl.setHorizontalTextPosition(0);
        titleLbl.setVerticalAlignment(1);
        titleLbl.setVerticalTextPosition(1);
        titleLbl.setForeground(Color.WHITE);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(titleLbl, gbc);

        final JPanel musicVolumeContainer = new JPanel();
        musicVolumeContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        musicVolumeContainer.setBackground(new Color(0, 0, 0, 155));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(musicVolumeContainer, gbc);

        final JLabel musicVolumeLbl = new JLabel("Music Volume:");
        musicVolumeLbl.setPreferredSize(new Dimension(128, 24));
        musicVolumeLbl.setForeground(Color.WHITE);
        musicVolumeLbl.setFont(Assets.Fonts.DEFAULT);
        musicVolumeContainer.add(musicVolumeLbl);

        musicVolumeSlider = new JSlider();
        musicVolumeSlider.setPreferredSize(new Dimension(160, 18));
        musicVolumeSlider.setValue((int) (Game.getInstance().getOptions().getMusicVolume() * 100f));
        musicVolumeContainer.add(musicVolumeSlider);

        /*final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(panel2, gbc);

        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(panel3, gbc);

        final JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(panel4, gbc);
        */

        final JPanel spacer = new JPanel();
        spacer.setBackground(new Color(0, 0, 0, 155));
        spacer.setPreferredSize(new Dimension(120, 10));
        spacer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(spacer, gbc);

        final JPanel btnMainContainer = new JPanel();
        btnMainContainer.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.BOTH;

        panel.add(btnMainContainer, gbc);

        final JPanel btnContainer = new JPanel();
        btnContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        btnContainer.setBackground(UiBuilder.BLACK_ALPHA);
        btnMainContainer.add(btnContainer, BorderLayout.CENTER);

        saveBtn = UiBuilder.createButton("SAVE", new Dimension(120, UiBuilder.BUTTON_HEIGHT));

        btnContainer.add(saveBtn);

        return panel;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(Assets.Images.BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}
