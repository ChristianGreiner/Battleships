package ui;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {

    private JButton spBtn, mpBtn, creditsBtn, optBtn, quitBtn;


    public MainMenuPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(Box.createVerticalGlue());

        //TO DO make the size of the buttons the same as that of the images they display
        this.spBtn = new JButton();
        this.spBtn.setIcon(new ImageIcon("green.png"));
        this.spBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.spBtn.setContentAreaFilled(false);
        this.spBtn.setBorder(null);
        this.spBtn.setRolloverIcon(new ImageIcon("greenTilted.png"));
        this.spBtn.setRolloverEnabled(true);
        this.spBtn.addActionListener(
                (e) -> {
                    System.out.println("singleplayerbtn gedruekt");
                }
        );

        this.add(spBtn);
        this.add(Box.createVerticalStrut(20));

        this.mpBtn = new JButton();
        this.mpBtn.setIcon(new ImageIcon("green.png"));
        this.mpBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.mpBtn.setContentAreaFilled(false);
        this.mpBtn.setBorder(null);
        this.mpBtn.setRolloverIcon(new ImageIcon("greenTilted.png"));
        this.mpBtn.setRolloverEnabled(true);
        this.mpBtn.addActionListener(
                (e) -> {
                    System.out.println("multiplayerbtn gedrueckt");
                }
        );

        this.add(mpBtn);
        this.add(Box.createVerticalStrut(20));

        this.creditsBtn = new JButton();
        this.creditsBtn.setIcon(new ImageIcon("green.png"));
        this.creditsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.creditsBtn.setContentAreaFilled(false);
        this.creditsBtn.setBorder(null);
        this.creditsBtn.setRolloverIcon(new ImageIcon("greenTilted.png"));
        this.creditsBtn.setRolloverEnabled(true);
        this.creditsBtn.addActionListener(
                (e) -> {
                    System.out.println("creditsbtn gedruekt");
                }
        );

        this.add(creditsBtn);
        this.add(Box.createVerticalStrut(20));

        this.optBtn = new JButton();
        this.optBtn.setIcon(new ImageIcon("green.png"));
        this.optBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.optBtn.setContentAreaFilled(false);
        this.optBtn.setBorder(null);
        this.optBtn.setRolloverIcon(new ImageIcon("greenTilted.png"));
        this.optBtn.setRolloverEnabled(true);
        this.optBtn.addActionListener(
                (e) -> {
                    System.out.println("optbtn gedruekt");
                }
        );

        this.add(optBtn);
        this.add(Box.createVerticalStrut(20));

        this.quitBtn = new JButton();
        this.quitBtn.setIcon(new ImageIcon("green.png"));
        this.quitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.quitBtn.setContentAreaFilled(false);
        this.quitBtn.setBorder(null);
        this.quitBtn.setRolloverIcon(new ImageIcon("greenTilted.png"));
        this.quitBtn.setRolloverEnabled(true);
        this.quitBtn.addActionListener(
                (e) -> {
                    System.out.println("quitbtn gedruekt");
                }
        );

        this.add(quitBtn);
        this.add(Box.createVerticalGlue());

    }
}
