package ui;

import core.Game;
import game.Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UiBuilder {

    public final static int BUTTON_HEIGHT = 38;

    public final static Color TRANSPARENT = new Color(0, 0, 0, 0);
    public final static Color BLACK_ALPHA = new Color(0, 0, 0, 155);
    public final static Color TURN_GREEN = new Color(46, 204, 113);
    public final static Color NOTURN_RED = new Color(214, 48, 49);

    /**
     * Creates a new ui button-
     *
     * @param name The button name
     * @param size The size
     * @return Returns new button.
     */
    public static JButton createButton(String name, Dimension size) {
        JButton btn = new JButton(name);
        btn.setBackground(new Color(44, 62, 80));
        btn.setForeground(Color.WHITE);
        btn.setFont(Assets.Fonts.DEFAULT);
        btn.setPreferredSize(size);
        btn.setMaximumSize(size);
        btn.setBorder(null);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(new Color(52, 73, 94));
                Game.getInstance().getSoundManager().playSfx(Assets.Sounds.BUTTON_HOVER);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                super.mousePressed(mouseEvent);
                Game.getInstance().getSoundManager().playSfx(Assets.Sounds.BUTTON_CLICK);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(new Color(44, 62, 80));
            }
        });

        return btn;
    }
}
