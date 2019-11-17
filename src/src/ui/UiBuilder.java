package ui;

import core.Fonts;
import core.Game;
import game.Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UiBuilder {

    public static JButton createButton(String name, Dimension size) {
        JButton btn = new JButton(name);
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setFont(Fonts.DEFAULT);
        btn.setPreferredSize(size);
        btn.setMaximumSize(size);
        btn.setBorder(null);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(Color.DARK_GRAY);
                Game.getInstance().getSoundManager().playSfx(Assets.Button.HOVER_SFX);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                super.mousePressed(mouseEvent);
                Game.getInstance().getSoundManager().playSfx(Assets.Button.CLICK_SFX);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(Color.BLACK);
            }
        });

        return btn;
    }
}
