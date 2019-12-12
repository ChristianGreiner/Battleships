package graphics;

import core.Helper;
import core.Renderer;
import game.Assets;
import game.Map;
import game.MapTile;
import game.ships.Battleship;
import game.ships.Carrier;
import game.ships.Destroyer;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MapRenderer extends Renderer implements MouseListener, MouseWheelListener, KeyListener {

    private boolean editorMode = false;
    private boolean enemyMap = false;
    private Map map;
    private Point tileSize;
    private ArrayList<MapTile> selectedShipTiles;
    private MapTile hoveredMapTile;
    private Point mouseShipOffset;
    private boolean selected;
    private boolean rotated;
    private boolean pressed;
    private boolean hovered;
    private ArrayList<MapRendererListener> listener = new ArrayList<>();

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    private boolean disabled = false;

    // WIP
    private ArrayList<BufferedImage> explosionFrames = new ArrayList<BufferedImage>();
    private Sprite explosionSprite;
    private Animation explosionAnim;
    private Point explosionAnimPos;

    public MapRenderer(Map map) {
        this.map = map;
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
        this.addKeyListener(this);

    }

    public boolean isEditorMode() {
        return this.editorMode;
    }

    public void setEditorMode(boolean editorMode) {
        this.editorMode = editorMode;
    }

    public boolean isEnemyMap() {
        return this.enemyMap;
    }

    public void setEnemyMap(boolean enemyMap) {
        this.enemyMap = enemyMap;
    }

    public void addMapRendererListener(MapRendererListener mrl) {
        this.listener.add(mrl);
    }

    public void setMap(Map map) {
        this.map = map;
        this.explosionSprite = new Sprite(Assets.Images.EXPLOSION, 48);
        if (this.explosionSprite.getSpriteSheet() != null) {
            for (int i = 0; i < 12; i++)
                this.explosionFrames.add(explosionSprite.getSprite(i, 0));

            this.explosionAnim = new Animation(explosionFrames.toArray(new BufferedImage[explosionFrames.size()]), 2);
        }
    }

    public void playExplosion(Point pos) {
        this.explosionAnimPos = pos;
        this.explosionAnim.reset();
        this.explosionAnim.start();
    }

    @Override
    public void draw() {
        super.draw();

        if (this.map == null) {
            return;
        }

        if (this.explosionAnim != null) {
            this.explosionAnim.update();
        }

        Graphics g = this.begin();

        this.tileSize = new Point(this.getWidth() / (this.map.getSize() + 1), this.getHeight() / (this.map.getSize() + 1));

        this.drawShips(g, tileSize);

        this.hovered = false;

        //draw grid
        this.drawGrid(g, tileSize);

        try {
            if (this.getMousePosition() != null && !disabled) {
                if (this.tileSize != null) {
                    if (this.getMousePosition().x >= tileSize.x && this.getMousePosition().y >= tileSize.y && this.getMousePosition().x <= this.getWidth() && this.getMousePosition().y <= this.getHeight()) {
                        if (this.editorMode) {
                            this.highlightShip(g);
                        } else {
                            this.drawHighlightTile(g);
                        }
                    }
                }
            }
        } catch (Exception ex) {

        }

        this.drawNumbers(g, tileSize);
        this.drawLetters(g, tileSize);

        if (this.explosionAnim != null && this.explosionAnimPos != null) {
            if (!this.explosionAnim.isStopped()) {
                Point explPos = new Point(this.explosionAnimPos.x * tileSize.x + this.explosionSprite.getTileSize(), this.explosionAnimPos.y * tileSize.y + this.explosionSprite.getTileSize());
                this.explosionAnim.draw(g, explPos, tileSize);
            }
        }

        this.end();

    }

    private void drawGrid(Graphics g, Point tileSize) {
        g.setColor(Color.BLACK);
        for (int i = 0; i < map.getSize() + 1; i++) {
            //vertical
            g.drawLine(i * tileSize.x, 0, i * tileSize.x, this.getHeight());
            //horizontal
            g.drawLine(0, i * tileSize.y, this.getWidth(), i * tileSize.y);
        }
    }

    private void drawShips(Graphics g, Point tileSize) {
        for (int y = 0; y < this.map.getSize(); y++) {
            for (int x = 0; x < this.map.getSize(); x++) {
                MapTile tile = this.map.getTile(new Point(x, y));

                if (tile.isBlocked()) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);
                } else if (tile.hasShip()) {

                    if (tile.getShip() instanceof Battleship) {
                        g.setColor(Color.DARK_GRAY);
                    } else if (tile.getShip() instanceof Carrier) {
                        g.setColor(Color.LIGHT_GRAY);
                    } else if (tile.getShip() instanceof Destroyer) {
                        g.setColor(Color.GRAY);
                    } else {
                        g.setColor(Color.WHITE);
                    }

                    if (selectedShipTiles != null && selectedShipTiles.contains(tile) && selected) {
                        g.setColor(Color.BLUE);
                    }

                    g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);

                    if (tile.isHit()) {
                        g.setColor(Color.ORANGE);
                        g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);
                    }

                } else if (tile.isHit() && !tile.isBlocked()) {
                    g.setColor(Color.RED);
                    g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);
                } else {
                    g.setColor(Color.BLUE);
                    g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);
                }
            }
        }
    }

    private void highlightShip(Graphics g) {
        //ship selection using mouse

        if (this.getMousePosition() == null || tileSize == null)
            return;

        Point tempPoint = new Point((this.getMousePosition().x / tileSize.x) - 1, (this.getMousePosition().y / tileSize.y) - 1);
        // System.out.println(((this.getMousePosition().x / tileSize.x) - 1) + " " + ((this.getMousePosition().y / tileSize.y) - 1));

        this.hoveredMapTile = this.map.getTile(tempPoint);
        if (this.hoveredMapTile != null) {
            if (this.hoveredMapTile.hasShip() && !this.selected) {

                this.selectedShipTiles = this.hoveredMapTile.getShip().getTiles();
                this.hovered = true;
                if (!this.pressed) {
                    g.setColor(Color.RED);
                    if (this.selectedShipTiles.get(0).getShip().isRotated()) {
                        g.drawRect(this.selectedShipTiles.get(0).getPos().x * tileSize.x + tileSize.x, this.selectedShipTiles.get(0).getPos().y * tileSize.y + tileSize.y, tileSize.x * selectedShipTiles.get(0).getShip().getSpace(), tileSize.y);
                    } else {
                        g.drawRect(this.selectedShipTiles.get(0).getPos().x * tileSize.x + tileSize.x, this.selectedShipTiles.get(0).getPos().y * tileSize.y + tileSize.y, tileSize.x, tileSize.x * selectedShipTiles.get(0).getShip().getSpace());
                    }
                }
            } else if (!this.selected) {
                this.drawHighlightTile(g);
            }
        }

        //picking up ship
        if (this.selected) {
            g.setColor(new Color(124, 252, 0, 200));
            // Point floatingShipPos = new Point( this.getMousePosition().x - this.mouseShipOffset.x, this.getMousePosition().y - this.mouseShipOffset.y);
            Point floatingShipPos = new Point((this.getMousePosition().x / tileSize.x * tileSize.x) - (this.mouseShipOffset.x / tileSize.x * tileSize.x), (this.getMousePosition().y / tileSize.y * tileSize.y) - (this.mouseShipOffset.y / tileSize.y * tileSize.y));
            boolean quickfix_rotate;
            if (this.selectedShipTiles.get(0).getShip().isRotated()) {
                if (!this.rotated) {
                    g.fillRect(floatingShipPos.x, floatingShipPos.y, tileSize.x * selectedShipTiles.get(0).getShip().getSpace(), tileSize.y);
                    quickfix_rotate = true;
                } else {
                    g.fillRect(floatingShipPos.x, floatingShipPos.y, tileSize.x, tileSize.x * selectedShipTiles.get(0).getShip().getSpace());
                    quickfix_rotate = false;
                }
            } else {
                if (!this.rotated) {
                    g.fillRect(floatingShipPos.x, floatingShipPos.y, tileSize.x, tileSize.x * selectedShipTiles.get(0).getShip().getSpace());
                    quickfix_rotate = false;
                } else {
                    g.fillRect(floatingShipPos.x, floatingShipPos.y, tileSize.x * selectedShipTiles.get(0).getShip().getSpace(), tileSize.y);
                    quickfix_rotate = true;
                }
            }

            //dropped
            if (!this.pressed) {
                this.selected = false;

                floatingShipPos.x = tempPoint.x - (this.mouseShipOffset.x / tileSize.x - 1) - 1;
                floatingShipPos.y = tempPoint.y - (this.mouseShipOffset.y / tileSize.y - 1) - 1;

                for (MapRendererListener mouseListener : listener) {
                    if (mouseListener != null) {
                        mouseListener.OnShipDropped(this.map, this.selectedShipTiles.get(0).getShip(), floatingShipPos, quickfix_rotate);
                    }
                }
                this.rotated = false;
                //System.out.println("DROPPED " + floatingShipPos.x + " " + floatingShipPos.y);
                //System.out.println(tempPoint);
            }
        }
    }

    void drawHighlightTile(Graphics g) {
        Point highlightPoint = new Point(((this.getMousePosition().x / (tileSize.x)) * tileSize.x), ((this.getMousePosition().y / (tileSize.y)) * tileSize.y));
        g.setColor(Color.GREEN);
        g.drawRect(highlightPoint.x, highlightPoint.y, tileSize.x, tileSize.y);
    }

    void drawLetters(Graphics g, Point tileSize) {
        // draw letters next to board
        int asciiCode = 65;
        for (int num = 1; num <= map.getSize(); num++) {
            Helper.drawCenteredString(g, Character.toString((char) asciiCode), new Rectangle(0, num * tileSize.y, tileSize.x, tileSize.y), Assets.Fonts.DEFAULT_BOLD);
            asciiCode++;
        }
    }

    void drawNumbers(Graphics g, Point tileSize) {
        g.setColor(Color.BLACK);
        g.setFont(Assets.Fonts.DEFAULT);
        // draw numbers above board
        for (Integer num = 1; num <= map.getSize(); num++) {
            Helper.drawCenteredString(g, num.toString(), new Rectangle(num * tileSize.x, 0, tileSize.x, tileSize.y), Assets.Fonts.DEFAULT_BOLD);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.getMousePosition() != null && !disabled) {
            if (this.getMousePosition() != null && this.getMousePosition().x >= tileSize.x && this.getMousePosition().y >= tileSize.y && this.getMousePosition().x <= this.getWidth() && this.getMousePosition().y <= this.getHeight()) {
                if (!this.editorMode && this.enemyMap) {
                    Point tempPoint = new Point(this.getMousePosition().x / tileSize.x - 1, this.getMousePosition().y / tileSize.y - 1);

                    for (MapRendererListener mouseListener : listener) {
                        if (mouseListener != null) {
                            if (this.map.isInMap(tempPoint)) {
                                mouseListener.OnShotFired(this.map, tempPoint);
                            }
                        }
                    }
                    System.out.println("FIRED");
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        this.pressed = true;

        if (this.hoveredMapTile != null && !disabled) {

            if (this.hoveredMapTile.hasShip() && !this.selected) {

                this.selectedShipTiles = this.hoveredMapTile.getShip().getTiles();

                this.hovered = true;

                this.selected = true;

                Point shipTopPos = new Point(selectedShipTiles.get(0).getPos().x * tileSize.x + tileSize.x, selectedShipTiles.get(0).getPos().y * tileSize.y + tileSize.y);
                Point mousePos = new Point(this.getMousePosition().x, this.getMousePosition().y);

                this.mouseShipOffset = new Point(mousePos.x - shipTopPos.x, mousePos.y - shipTopPos.y);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.pressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        if (!this.editorMode)
            return;

       /* if(this.hoveredMapTile != null) {
            if(this.hoveredMapTile.hasShip()) {
                for (MapRendererListener mouseListener : listener) {
                    if (mouseListener != null) {
                        mouseListener.OnRotated(this.map, this.hoveredMapTile.getShip());
                    }
                }
            }
        } else {*/
        if (this.selected)
            this.rotated = !this.rotated;
        //}

        System.out.println("rotated");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (!this.editorMode)
            return;

        if (e.getKeyCode() == KeyEvent.VK_R) {

            this.rotated = !this.rotated;


            System.out.println("rotated");
        }
        System.out.println("rotated");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("rotated");
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}