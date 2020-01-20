package graphics;

import core.Game;
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
    private boolean shipsVisable = true;
    protected Map map;
    private Point gridSize;
    protected Point tileSize;
    protected ArrayList<MapTile> selectedShipTiles;
    protected MapTile hoveredMapTile;
    protected Point mouseShipOffset;
    protected boolean selected;
    protected boolean rotated;
    protected boolean pressed;
    protected Point tempPoint;
    protected Point highlightPoint;
    protected ArrayList<MapRendererListener> listener = new ArrayList<>();

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

    public boolean isShipsVisable() {
        return shipsVisable;
    }

    public void setShipsVisable(boolean shipsVisable) {
        this.shipsVisable = shipsVisable;
    }

    protected void setGridSize(Point size)
    {
        if(size != null && size.x > 0 && size.y > 0)
            this.gridSize = size;
    }

    private void setTileSize()
    {
        if(this.gridSize != null && this.map != null)
            this.tileSize = new Point(this.gridSize.x / (this.map.getSize() + 1), this.gridSize.y / (this.map.getSize() + 1));
    }

    public void resizeMapSize() {
        setGridSize(new Point(this.getWidth(), this.getHeight()));
        this.invalidateBuffer();
        Game.getInstance().getWindow().revalidate();
    }

    public Graphics beginRendering() {
        Graphics g = this.begin();

        if(this.gridSize == null)
            setGridSize(new Point(this.getWidth(), this.getHeight()));

        setTileSize();

        this.drawShips(g, tileSize);

        //draw grid
        this.drawGrid(g, tileSize);

        try {
            if (this.getMousePosition() != null && !disabled) {
                if (this.tileSize != null) {
                    if (this.getMousePosition().x >= tileSize.x && this.getMousePosition().y >= tileSize.y && this.getMousePosition().x <= this.getWidth() && this.getMousePosition().y <= this.getHeight()) {
                        if (this.editorMode) {
                            this.highlightShip(g);
                            this.dragAndDropShip(g);
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
                Point explosionPos = new Point(this.explosionAnimPos.x * tileSize.x + tileSize.x, this.explosionAnimPos.y * tileSize.y + tileSize.y);
                this.explosionAnim.draw(g, explosionPos, tileSize);
            }
        }

        try {
            if (this.getMousePosition() != null  && isEnemyMap()) {
                if (this.tileSize != null) {
                    if (this.getMousePosition().x >= tileSize.x && this.getMousePosition().y >= tileSize.y && this.getMousePosition().x <= this.getWidth() && this.getMousePosition().y <= this.getHeight()) {
                        drawCrossair(g, this.getMousePosition());
                    }
                }
            }
        } catch (Exception ex) {
        }

        return g;
    }

    public void endRendering() {
        this.end();
    }

    @Override
    public void draw() {

        if (this.map == null) {
            return;
        }

        if(this.isVisible() && this.isValid()) {
            this.beginRendering();
            this.endRendering();
        }
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        if (this.explosionAnim != null) {
            this.explosionAnim.update();
        }
    }

    private void drawCrossair(Graphics g, Point pos){
        g.drawImage(Assets.Images.CROSSAIR, pos.x - 16, pos.y - 16, 32, 32, null);
    }

    private void drawGrid(Graphics g, Point tileSize) {
        g.setColor(new Color(0, 0, 0, 122));
        for (int i = 0; i <= map.getSize() + 1; i++) {
            //vertical
            g.drawLine(i * tileSize.x, 0, i * tileSize.x, (map.getSize() + 1) * tileSize.y);
            //horizontal
            g.drawLine(0, i * tileSize.y, (map.getSize() + 1) * tileSize.x, i * tileSize.y);
        }
    }

    private void drawImageTile(Graphics g, Image image, Rectangle rect) {
        g.drawImage(image, rect.x, rect.y, rect.width, rect.height, null);
    }

    protected void drawShips(Graphics g, Point tileSize) {
        for (int y = 0; y < this.map.getSize(); y++) {
            for (int x = 0; x < this.map.getSize(); x++) {
                MapTile tile = this.map.getTile(new Point(x, y));

                Rectangle tilePos = new Rectangle(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);

                if (tile.isBlocked()) {
                    g.setColor(Color.YELLOW);
                    //g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);
                    drawImageTile(g, Assets.Images.WATER_DARK, tilePos);
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

                    if(!this.shipsVisable)
                        g.setColor(Color.BLUE);

                    g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);

                    if (tile.isHit()) {
                        g.setColor(Color.ORANGE);
                        g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);
                    }

                } else if (tile.isHit() && !tile.isBlocked()) {
                    g.setColor(Color.RED);
                    g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);
                } else {
                    // draw water
                    g.setColor(Color.BLUE);
                    //g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);
                    drawImageTile(g, Assets.Images.WATER, tilePos);
                }
            }
        }
    }

    protected void highlightShip(Graphics g) {
        //ship selection using mouse

        if (this.getMousePosition() == null || tileSize == null)
            return;

        this.tempPoint = new Point((this.getMousePosition().x / tileSize.x) - 1, (this.getMousePosition().y / tileSize.y) - 1);
        // System.out.println(((this.getMousePosition().x / tileSize.x) - 1) + " " + ((this.getMousePosition().y / tileSize.y) - 1));

        this.hoveredMapTile = this.map.getTile(tempPoint);
        if (this.hoveredMapTile != null) {
            if (this.hoveredMapTile.hasShip() && !this.selected) {

                this.selectedShipTiles = this.hoveredMapTile.getShip().getTiles();
                if (!this.pressed) {
                    g.setColor(new Color(124, 252, 0, 200));
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
    }

    protected void dragAndDropShip(Graphics g) {
        //picking up ship
        if (this.selected) {
            g.setColor(new Color(124, 252, 0, 200));
            // free floating ship
            Point floatingShipPos = new Point( this.getMousePosition().x - this.mouseShipOffset.x, this.getMousePosition().y - this.mouseShipOffset.y);

            //rasterized ship
            Point floatingShipMarker = new Point((this.getMousePosition().x / tileSize.x * tileSize.x) - (this.mouseShipOffset.x / tileSize.x * tileSize.x), (this.getMousePosition().y / tileSize.y * tileSize.y) - (this.mouseShipOffset.y / tileSize.y * tileSize.y));

            Point dropPos = new Point(tempPoint.x - (this.mouseShipOffset.x / tileSize.x - 1) - 1, tempPoint.y - (this.mouseShipOffset.y / tileSize.y - 1) - 1);

            boolean quickfix_rotate;
            if (this.selectedShipTiles.get(0).getShip().isRotated()) {
                if (!this.rotated) {
                    quickfix_rotate = true;
                    if(!map.canMoveShip(selectedShipTiles.get(0).getShip(), dropPos, quickfix_rotate)) {
                        g.setColor(Color.RED);
                    }
                        g.fillRect(floatingShipPos.x, floatingShipPos.y, tileSize.x * selectedShipTiles.get(0).getShip().getSpace(), tileSize.y);
                        g.drawRect(floatingShipMarker.x, floatingShipMarker.y, tileSize.x * selectedShipTiles.get(0).getShip().getSpace(), tileSize.y);

                } else {
                    quickfix_rotate = false;
                    if(!map.canMoveShip(selectedShipTiles.get(0).getShip(), dropPos, quickfix_rotate)) {
                        g.setColor(Color.RED);
                    }
                    g.fillRect(floatingShipPos.x, floatingShipPos.y, tileSize.x, tileSize.x * selectedShipTiles.get(0).getShip().getSpace());
                    g.drawRect(floatingShipMarker.x, floatingShipMarker.y, tileSize.x, tileSize.x * selectedShipTiles.get(0).getShip().getSpace());

                }
            } else {
                quickfix_rotate = false;
                if (!this.rotated) {
                    if(!map.canMoveShip(selectedShipTiles.get(0).getShip(), dropPos, quickfix_rotate)) {
                        g.setColor(Color.RED);
                    }
                    g.fillRect(floatingShipPos.x, floatingShipPos.y, tileSize.x, tileSize.x * selectedShipTiles.get(0).getShip().getSpace());
                    g.drawRect(floatingShipMarker.x, floatingShipMarker.y, tileSize.x, tileSize.x * selectedShipTiles.get(0).getShip().getSpace());

                } else {
                    quickfix_rotate = true;
                    if(!map.canMoveShip(selectedShipTiles.get(0).getShip(), dropPos, quickfix_rotate )) {
                        g.setColor(Color.RED);
                    }
                    g.fillRect(floatingShipPos.x, floatingShipPos.y, tileSize.x * selectedShipTiles.get(0).getShip().getSpace(), tileSize.y);
                    g.drawRect(floatingShipMarker.x, floatingShipMarker.y, tileSize.x * selectedShipTiles.get(0).getShip().getSpace(), tileSize.y);

                }
            }

            //dropped
            if (!this.pressed) {
                this.selected = false;

                //dropPos.x = tempPoint.x - (this.mouseShipOffset.x / tileSize.x - 1) - 1;
                //dropPos.y = tempPoint.y - (this.mouseShipOffset.y / tileSize.y - 1) - 1;
                for (MapRendererListener mouseListener : listener) {
                    if (mouseListener != null) {
                        mouseListener.OnShipDropped(this.map, this.selectedShipTiles.get(0).getShip(), dropPos, quickfix_rotate);
                    }
                }
                this.rotated = false;
            }
        }
    }
    protected void drawHighlightTile(Graphics g) {
        Point mousePos = new Point(this.getMousePosition().x, this.getMousePosition().y);

        if(mousePos.x < gridSize.x && mousePos.y < gridSize.y) {
            this.highlightPoint = new Point((mousePos.x / tileSize.x) * tileSize.x, (mousePos.y / tileSize.y) * tileSize.y);

            g.setColor(Color.GREEN);
            g.drawRect(this.highlightPoint.x, this.highlightPoint.y, tileSize.x, tileSize.y);
        }
    }

    protected void drawLetters(Graphics g, Point tileSize) {
        // draw letters next to board
        int asciiCode = 65;
        for (int num = 1; num <= map.getSize(); num++) {

            // draw sand left side
            drawImageTile(g, Assets.Images.SAND_LEFT,  new Rectangle(0, num * tileSize.y, tileSize.x, tileSize.y));

            Helper.drawCenteredString(g, Character.toString((char) asciiCode), new Rectangle(0, num * tileSize.y, tileSize.x, tileSize.y), Assets.Fonts.DEFAULT_BOLD);
            asciiCode++;

        }
    }

    protected void drawNumbers(Graphics g, Point tileSize) {
        g.setColor(Color.BLACK);
        g.setFont(Assets.Fonts.DEFAULT);

        // draw sand top left corner
        drawImageTile(g, Assets.Images.SAND_TOP_LEFT,  new Rectangle(0, 0, tileSize.x, tileSize.y));

        // draw numbers above board
        for (Integer num = 1; num <= map.getSize(); num++) {

            // draw sand top coner
            drawImageTile(g, Assets.Images.SAND_TOP,  new Rectangle(num * tileSize.x, 0, tileSize.x, tileSize.y));

            Helper.drawCenteredString(g, num.toString(), new Rectangle(num * tileSize.x, 0, tileSize.x, tileSize.y), Assets.Fonts.DEFAULT_BOLD);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
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
        } catch (Exception ex) {

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        this.pressed = true;

        if (this.hoveredMapTile != null && !disabled) {

            if (this.hoveredMapTile.hasShip() && !this.selected) {

                this.selectedShipTiles = this.hoveredMapTile.getShip().getTiles();

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

    }

    @Override
    public void keyTyped(KeyEvent e) {

        if (!this.editorMode)
            return;

        if (e.getKeyCode() == KeyEvent.VK_R) {
            if (this.selected)
                this.rotated = !this.rotated;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}