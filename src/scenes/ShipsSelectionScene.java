package scenes;

import core.Drawable;
import core.Game;
import core.GameWindow;
import core.Updatable;
import game.*;
import game.ships.Ship;
import graphics.MapBuilderRenderer;
import graphics.MapRendererListener;
import ui.GuiScene;
import ui.ShipSelectionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class for the ship selection scene.
 */
public class ShipsSelectionScene extends Scene implements Drawable, Updatable, GuiScene, KeyListener, MapRendererListener, MapListener, GameSession {

    private MapBuilderRenderer buildRenderer;
    private Map playerMap;
    private ShipSelectionPanel uiPanel;
    private MapGenerator mapGenerator;
    private GameSessionData gameSessionData;

    /**
     * Constructor for the ship selection scene.
     */
    public ShipsSelectionScene() {
        super("ShipsSelectionScene");

        this.buildRenderer = new MapBuilderRenderer(null);
        this.buildRenderer.addMapRendererListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSwitched() {
        super.onSwitched();

        this.playerMap = null;
        this.gameSessionData = null;

        this.mapGenerator = new MapGenerator();
        Game.getInstance().getSoundManager().playBackgroundMusic(Assets.Sounds.PLAYING_MUSIC, true);
        this.buildRenderer.setEditorMode(true);
        this.uiPanel.getBtnStartGame().setEnabled(false);

        this.sizeUpdated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeGameSession(GameSessionData data) {
        this.gameSessionData = data;
        this.playerMap = new Map(data.getMapSize());
        this.buildRenderer.init(this.playerMap);
        this.buildRenderer.setMap(this.playerMap);
        this.buildRenderer.addKeyListener(this);
        this.playerMap.addListener(this);

        Game.getInstance().getWindow().repaint();
        Game.getInstance().getWindow().revalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel buildGui(GameWindow gameWindow) {
        ShipSelectionPanel shipSelectionPanel = new ShipSelectionPanel(this.buildRenderer);

        shipSelectionPanel.create(new Dimension(777, 512));

        this.uiPanel = shipSelectionPanel;

        shipSelectionPanel.getBtnCancel().addActionListener((e) -> {
            Game.getInstance().getSceneManager().setActiveScene(SinglePlayerSettingsScene.class);
        });

        shipSelectionPanel.getBtnRandomMap().addActionListener((e) -> {
            this.playerMap = this.mapGenerator.generate(this.gameSessionData.getMapSize());
            this.buildRenderer.setMap(this.playerMap);
            this.OnMapUpdated();
            this.buildRenderer.requestFocus();
        });

        shipSelectionPanel.getBtnStartGame().addActionListener((e) -> {
            if (this.gameSessionData.getGameType() == SavegameType.Singleplayer) {
                SinglePlayerScene scene = (SinglePlayerScene) Game.getInstance().getSceneManager().setActiveScene(SinglePlayerScene.class);
                scene.initializeGameSession(new GameSessionData(this.playerMap, this.gameSessionData.getMapSize(), this.gameSessionData.getAiDifficulty(), SavegameType.Singleplayer));
            } else if (this.gameSessionData.getGameType() == SavegameType.SingleplayerAi) {
                SinglePlayerAIScene scene = (SinglePlayerAIScene) Game.getInstance().getSceneManager().setActiveScene(SinglePlayerAIScene.class);
                scene.initializeGameSession(new GameSessionData(this.playerMap, this.gameSessionData.getMapSize(), this.gameSessionData.getAiDifficulty(), SavegameType.SingleplayerAi));
            } else if (this.gameSessionData.getGameType() == SavegameType.Multiplayer) {
                MultiplayerScene scene = (MultiplayerScene) Game.getInstance().getSceneManager().setActiveScene(MultiplayerScene.class);
                scene.initializeGameSession(new GameSessionData(this.playerMap, this.gameSessionData.getMapSize(), null, SavegameType.Multiplayer));
                Game.getInstance().getNetworkManager().confirmSession();
            } else if (this.gameSessionData.getGameType() == SavegameType.MultiplayerAi) {
                MultiplayerAIScene scene = (MultiplayerAIScene) Game.getInstance().getSceneManager().setActiveScene(MultiplayerAIScene.class);
                scene.initializeGameSession(new GameSessionData(this.playerMap, this.gameSessionData.getMapSize(), this.gameSessionData.getAiDifficulty(), SavegameType.MultiplayerAi));
                Game.getInstance().getNetworkManager().confirmSession();
            }
        });

        return shipSelectionPanel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sizeUpdated() {
        this.uiPanel.updateMapSize(Game.getInstance().getWindow().getBuildMapRenderPanelSize());

        if (this.buildRenderer != null)
            this.buildRenderer.updateGridSize();

        Game.getInstance().getWindow().repaint();
        Game.getInstance().getWindow().revalidate();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
    }

    /**
     * Returns to the main menu if ESC was pressed and triggers the rotate event in MapBuilderRenderer if R was pressed.
     * @param e the keyEvent.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Game.getInstance().getSceneManager().setActiveScene(MainMenuScene.class);
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            this.buildRenderer.rotate();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw() {
        if (this.uiPanel != null && this.buildRenderer != null) {
            if (this.uiPanel.isVisible() && this.uiPanel.isValid()) {
                this.buildRenderer.draw();
            }

            if (this.playerMap != null)
                this.uiPanel.getBtnStartGame().setEnabled(this.playerMap.isCorrectFilled());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void OnShipDropped(Map map, Ship ship, Point pos, boolean rotated) {
        // ship not inserted  yet
        if (ship.getPosition() == null) {
            map.insert(ship, pos, rotated);
        } else {
            // ship already inserted
            if ((ship.isRotated() && !rotated) || (!ship.isRotated() && rotated)) {
                map.moveAndRotate(ship, pos);
            } else
                map.move(ship, pos);
        }

        this.OnMapUpdated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void OnShotFired(Map map, Point pos) {
        return;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void OnRotated(Map map, Ship ship) {
        map.rotate(ship);
    }

    @Override
    public void OnMapUpdated() {
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void update(double deltaTime) {
        if (this.buildRenderer != null)
            this.buildRenderer.update(deltaTime);
    }

    @Override
    public void lateUpdate(double deltaTime) {

    }
}