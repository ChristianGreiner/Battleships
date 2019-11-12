package scenes;

import core.Renderer;
import core.*;
import game.Map;
import game.ships.Ship;
import ui.GuiScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TestScene extends Scene implements Updatable, Drawable, GuiScene, MouseListener {

    private Point mousePos = new Point();
    private Map playerMap;
    private Map enemyMap;
    private String name;

    private Ship selectedShip;

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

        g.drawLine(0, 0, mousePos.x, mousePos.y);

        this.renderer.end();
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow, JPanel panel) {

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
        this.renderer.addMouseListener(this);
        panel.add(this.renderer);

        panel.add(btn, BorderLayout.CENTER);

        return panel;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        System.out.println("Clicked at: " + this.renderer.getMousePosition());
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
