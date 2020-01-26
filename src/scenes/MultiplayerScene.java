package scenes;

import core.Drawable;
import core.Game;
import core.GameWindow;
import core.Updatable;
import game.*;
import game.ships.Ship;
import graphics.MapRenderer;
import graphics.MapRendererListener;
import network.NetworkListener;
import network.NetworkType;
import ui.GamePanel;
import ui.GuiScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MultiplayerScene extends Scene implements Updatable, GuiScene, Drawable, KeyListener, GameSession, NetworkListener, MapRendererListener {

    private Map playerMap;
    private Map enemyMap;
    private MapRenderer playerMapRenderer;
    private MapRenderer enemyMapRenderer;

    private GamePanel uiPanel;

    private NetworkType playerTurn = NetworkType.Client;
    private GameSessionData gameSessionData;
    private boolean gameStarted = false;
    private Point lastShot = null;

    public MultiplayerScene() {
        super("MultiplayerScene");

        this.playerMapRenderer = new MapRenderer(null);
        this.enemyMapRenderer = new MapRenderer(null);

        this.playerMapRenderer.setEditorMode(false);
        this.enemyMapRenderer.setEditorMode(false);
        this.enemyMapRenderer.setEnemyMap(true);
        this.enemyMapRenderer.addMapRendererListener(this);
    }

    @Override
    public void onAdded() {
        super.onAdded();
        Game.getInstance().getNetworkManager().addNetworkListener(this);
    }

    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        this.uiPanel = new GamePanel(this.playerMapRenderer, this.enemyMapRenderer);

        uiPanel = uiPanel.create(new Dimension(512, 512));

        return uiPanel;
    }

    @Override
    public void update(double deltaTime) {
        this.enemyMapRenderer.setDisabled(!gameStarted);
    }

    @Override
    public void lateUpdate(double deltaTime) {
    }

    @Override
    public void draw() {
        if (this.enemyMapRenderer != null && this.playerMapRenderer != null) {
            this.playerMapRenderer.draw();
            this.enemyMapRenderer.draw();
        }
    }

    @Override
    public void sizeUpdated() {
        this.uiPanel.updateMapSize(Game.getInstance().getWindow().getMapRenderPanelSize());
        Game.getInstance().getWindow().revalidate();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void initializeGameSession(GameSessionData data) {
        this.gameSessionData = data;

        this.playerMapRenderer.setMap(data.getMap());
        this.enemyMap = new Map(data.getMapSize());

        this.enemyMapRenderer.setMap(this.enemyMap);


        this.playerMap = data.getMap();
    }

    @Override
    public void OnPlayerConnected() {
    }

    @Override
    public void OnGameJoined(int mapSize) {
    }

    @Override
    public void OnGameStarted() {
        this.gameStarted = true;
    }

    @Override
    public void OnReceiveShot(Point pos) {
        if(this.playerMap.isInMap(pos)) {
            HitData hitData = this.playerMap.shot(pos);
            Game.getInstance().getNetworkManager().sendAnswer(hitData.getHitType());
        }
    }


    @Override
    public void OnReceiveAnswer(HitType type) {
        if(this.lastShot != null) {
            this.enemyMap.markTile(this.lastShot, type);
            if(type == HitType.Ship || type == HitType.ShipDestroyed)
                Game.getInstance().getNetworkManager().sendPass();
        }
    }

    @Override
    public void OnReceivePass() {
        System.out.println("Getting pass");
    }

    @Override
    public void OnShipDropped(Map map, Ship ship, Point pos, boolean rotated) {
    }

    @Override
    public void OnShotFired(Map map, Point pos) {
        if(map.isInMap(pos)) {
            Game.getInstance().getNetworkManager().sendShot(pos);
            this.lastShot = pos;
        }
    }

    @Override
    public void OnRotated(Map map, Ship ship) {

    }
}