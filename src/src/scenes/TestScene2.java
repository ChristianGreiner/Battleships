package scenes;

import core.Drawable;
import core.Game;
import core.Renderer;
import core.Updatable;
import ui.GuiScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestScene2 extends Scene implements Updatable, Drawable, GuiScene {

    private Renderer renderer = new Renderer();

    public TestScene2() {
        super("TestScene2");
    }

    @Override
    public void update(double deltaTime) {
    }

    public void draw() {
        Graphics g = this.renderer.begin();
        g.fillRect(320, 320, 20, 20);
        this.renderer.end();
    }

    @Override
    public JPanel buildGui() {

        JPanel panel = new JPanel();

        JButton btn = new JButton("Zu Scene 1 wechseln");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.getInstance().getSceneManager().setActiveScene("TestScene");
            }
        });
        btn.setSize(200, 200);

        this.renderer.setBackground(Color.black);
        this.renderer.setLocation(300, 300);
        this.renderer.setSize(360, 360);
        panel.add(this.renderer);
        panel.add(btn, BorderLayout.CENTER);

        return panel;
    }
}
