package scenes;

import core.Drawable;
import core.Game;
import core.Renderer;
import core.Updatable;
import game.Map;
import ui.GuiScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestScene extends Scene implements Updatable, Drawable, GuiScene {

    private Point mousePos = new Point();
    private Map playerMap;
    private Map enemyMap;
    private String name;

    private Renderer renderer = new Renderer();

    public TestScene() {
        super("TestScene");

        this.playerMap = new Map(10);
        this.enemyMap = new Map(10);
    }

    @Override
    public void update(double deltaTime) {
    }

    public void draw() {
        Point pos = this.renderer.getMousePosition();

        if(pos != null)
            this.mousePos = pos;

        Graphics g = this.renderer.begin();

        g.fillOval(mousePos.x, mousePos.y, 5, 5);
        g.fillRect(20, 20, 20, 20);

        this.renderer.end();
    }

    @Override
    public JPanel buildGui() {

        JPanel panel = new JPanel();

        JButton btn = new JButton("Zu Scene 2 wechseln");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.getInstance().getSceneManager().setActiveScene("TestScene2");
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
