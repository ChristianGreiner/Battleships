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
import game.ships.Ship;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MapRenderer extends Renderer implements MouseListener, MouseWheelListener, KeyListener {

    private boolean editorMode = false;
    private boolean enemyMap = false;
    private boolean shipsVisable = true;
    protected Map map;
    private Point gridSize;
    protected Point tileSize;
    private Ship selectedShip;
    protected MapTile hoveredMapTile;
    protected Point mouseShipOffset;
    protected boolean selected;
    protected boolean rotated;
    protected boolean pressed;
    protected Point tempPoint;
    protected Point highlightPoint;
    protected ArrayList<MapRendererListener> listener = new ArrayList<>();
    private BufferedImage backgroundBackground;

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

    public Graphics2D beginRendering() {
        Graphics2D g = this.begin();

        if(this.gridSize == null)
            setGridSize(new Point(this.getWidth(), this.getHeight()));

        setTileSize();

        if(this.backgroundBackground == null) {
            this.backgroundBackground = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D bGr = this.backgroundBackground.createGraphics();
            for (int y = 0; y < this.map.getSize() + 1; y++) {
                for (int x = 0; x < this.map.getSize() + 1; x++) {
                    Rectangle tilePos = new Rectangle(x * tileSize.x, y * tileSize.y, tileSize.x, tileSize.y);
                    drawImage(bGr, Assets.Images.WATER, tilePos);
                }
            }
            bGr.dispose();
        }

        // draw map background
        g.drawImage(this.backgroundBackground, 0, 0, this.getWidth(), this.getHeight(), null);

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
                        drawCrosshair(g, this.getMousePosition());
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

    private void drawCrosshair(Graphics2D g, Point pos){
        g.drawImage(Assets.Images.CROSSHAIR, pos.x - 16, pos.y - 16, 32, 32, null);
    }

    private void drawGrid(Graphics2D g, Point tileSize) {
        g.setColor(new Color(0, 0, 0, 50));
        for (int i = 0; i <= map.getSize() + 1; i++) {
            //vertical
            g.drawLine(i * tileSize.x, 0, i * tileSize.x, (map.getSize() + 1) * tileSize.y);
            //horizontal
            g.drawLine(0, i * tileSize.y, (map.getSize() + 1) * tileSize.x, i * tileSize.y);
        }
    }

    private void drawImage(Graphics2D g, Image image, Rectangle rect) {
        g.drawImage(image, rect.x, rect.y, rect.width, rect.height, null);
    }

    protected void drawImageShip(Graphics2D g, int space, Image image, Rectangle rect, Point tileSize, boolean rotated) {
        if(rotated) {
            AffineTransform backup = g.getTransform();
            AffineTransform a = AffineTransform.getRotateInstance(Math.toRadians (90), rect.x, rect.y);
            g.setTransform(a);
            g.drawImage(image, rect.x, rect.y - tileSize.y * space, rect.height, rect.width, null);
            g.setTransform(backup);
        }
        else
            drawImage(g, image, rect);
    }

    protected void drawShips(Graphics2D g, Point tileSize) {

        for (int i = 0; i < this.map.getShips().size(); i++) {
            Ship ship = this.map.getShips().get(i);

            Rectangle shipPos = null;

            if(ship.isRotated())
                shipPos = new Rectangle(ship.getPosition().x * tileSize.x + tileSize.x, ship.getPosition().y * tileSize.y + tileSize.y, ship.getSpace() * tileSize.x, tileSize.y);
            else
                shipPos = new Rectangle(ship.getPosition().x * tileSize.x + tileSize.x, ship.getPosition().y * tileSize.y + tileSize.y, tileSize.x, ship.getSpace() * tileSize.y);

            Image shipImage = null;

            if (ship instanceof Battleship) {
                shipImage = Assets.Images.SHIP_BATTLESHIP;
            } else if (ship instanceof Carrier) {
                shipImage = Assets.Images.SHIP_CARRIER;
            } else if (ship instanceof Destroyer) {
                shipImage = Assets.Images.SHIP_DESTROYER;
            } else {
                shipImage = Assets.Images.SHIP_SUBMARINE;
            }

            if(ship.isDestroyed() || this.shipsVisable)
                drawImageShip(g, ship.getSpace(), shipImage, shipPos, this.tileSize, ship.isRotated());
        }
        for (int y = 0; y < this.map.getSize(); y++) {
            for (int x = 0; x < this.map.getSize(); x++) {
                MapTile tile = this.map.getTile(new Point(x, y));

                Rectangle tilePos = new Rectangle(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);

                if (tile.isBlocked()) {
                    drawImage(g, Assets.Images.WATER_DARK, tilePos);
                } else if (tile.isHit() && !tile.isBlocked() && !tile.hasShip()) {
                    g.setColor(new Color(255, 255, 255,  100));
                    if(tile.hasShip())
                        if(tile.getShip().isDestroyed())
                            g.setColor(new Color(255, 255, 255, 5));

                    g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);
                }
                else if (tile.isHit() && !tile.isBlocked() && tile.hasShip()) {
                    g.setColor(new Color(255, 0, 0, 30));
                    g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);
                }
            }
        }
    }

    protected void highlightShip(Graphics2D g) {
        //ship selection using mouse

        if (this.getMousePosition() == null || tileSize == null)
            return;

        this.tempPoint = new Point((this.getMousePosition().x / tileSize.x) - 1, (this.getMousePosition().y / tileSize.y) - 1);
        // System.out.println(((this.getMousePosition().x / tileSize.x) - 1) + " " + ((this.getMousePosition().y / tileSize.y) - 1));

        this.hoveredMapTile = this.map.getTile(tempPoint);
        if (this.hoveredMapTile != null) {
            if (this.hoveredMapTile.hasShip() && !this.selected) {

                //this.selectedShipTiles = this.hoveredMapTile.getShip().getTiles();
                this.selectedShip = this.hoveredMapTile.getShip();
                if (!this.pressed && this.selectedShip != null) {
                    //System.out.println(this.selectedShip.isRotated());
                    g.setColor(new Color(124, 252, 0, 200));
                    if (this.selectedShip.isRotated()) {
                        g.drawRect(this.selectedShip.getPosition().x * tileSize.x + tileSize.x, this.selectedShip.getPosition().y * tileSize.y + tileSize.y, tileSize.x * this.selectedShip.getSpace(), tileSize.y);
                    } else {
                        g.drawRect(this.selectedShip.getPosition().x * tileSize.x + tileSize.x, this.selectedShip.getPosition().y * tileSize.y + tileSize.y, tileSize.x, tileSize.x * this.selectedShip.getSpace());
                    }
                }
            } else if (!this.selected) {
                this.drawHighlightTile(g);
            }
        }
    }

    protected void dragAndDropShip(Graphics2D g) {
        //picking up ship
        if (this.selected && this.selectedShip != null) {
            g.setColor(new Color(124, 252, 0, 200));

            // free floating ship
            Point floatingShipPos = new Point( this.getMousePosition().x - this.mouseShipOffset.x, this.getMousePosition().y - this.mouseShipOffset.y);

            //rasterized ship
            Point floatingShipMarker = new Point((this.getMousePosition().x / tileSize.x * tileSize.x) - (this.mouseShipOffset.x / tileSize.x * tileSize.x), (this.getMousePosition().y / tileSize.y * tileSize.y) - (this.mouseShipOffset.y / tileSize.y * tileSize.y));

            Point dropPos = new Point(tempPoint.x - (this.mouseShipOffset.x / tileSize.x - 1) - 1, tempPoint.y - (this.mouseShipOffset.y / tileSize.y - 1) - 1);

            //map.remove(this.selectedShip);

            boolean quickfix_rotate;
            g.setStroke(new BasicStroke(3));
            if (this.selectedShip.isRotated()) {
                if (!this.rotated) {
                    quickfix_rotate = true;
                    if(!map.canMoveShip(this.selectedShip, dropPos, true)) {
                        g.setColor(Color.RED);
                    }
                    g.fillRect(floatingShipPos.x, floatingShipPos.y, tileSize.x * this.selectedShip.getSpace(), tileSize.y);
                    g.drawRect(floatingShipMarker.x, floatingShipMarker.y, tileSize.x * this.selectedShip.getSpace(), tileSize.y);

                } else {
                    quickfix_rotate = false;
                    dropPos.x = tempPoint.x;
                    floatingShipMarker.x = this.getMousePosition().x / tileSize.x * tileSize.x;
                    //System.out.println(dropPos);
                    if(!map.canMoveShip(this.selectedShip, dropPos, false)) {
                        g.setColor(Color.RED);
                    }
                    g.fillRect(floatingShipPos.x + this.mouseShipOffset.x - this.mouseShipOffset.y, floatingShipPos.y , tileSize.x, tileSize.x * this.selectedShip.getSpace());
                    g.drawRect(floatingShipMarker.x, floatingShipMarker.y, tileSize.x, tileSize.x * this.selectedShip.getSpace());

                }
            } else {
                if (!this.rotated) {
                    quickfix_rotate = false;
                    if(!map.canMoveShip(this.selectedShip, dropPos, false)) {
                        g.setColor(Color.RED);
                    }
                    g.fillRect(floatingShipPos.x, floatingShipPos.y , tileSize.x, tileSize.x * this.selectedShip.getSpace());
                    g.drawRect(floatingShipMarker.x, floatingShipMarker.y, tileSize.x, tileSize.x * this.selectedShip.getSpace());

                } else {
                    quickfix_rotate = true;
                    floatingShipMarker.y =  this.getMousePosition().y / tileSize.y * tileSize.y;
                    dropPos.y = tempPoint.y;
                    if(!map.canMoveShip(this.selectedShip, dropPos, true )) {
                        g.setColor(Color.RED);
                    }
                    g.fillRect(floatingShipPos.x, floatingShipPos.y + this.mouseShipOffset.y - this.mouseShipOffset.x, tileSize.x * this.selectedShip.getSpace(), tileSize.y);
                    g.drawRect(floatingShipMarker.x, floatingShipMarker.y, tileSize.x * this.selectedShip.getSpace(), tileSize.y);
                }
            }

            g.setStroke(new BasicStroke());
            //dropped
            if (!this.pressed) {
                this.selected = false;

                for (MapRendererListener mouseListener : listener) {
                    if (mouseListener != null) {
                        mouseListener.OnShipDropped(this.map, this.selectedShip, dropPos, quickfix_rotate);
                    }
                }
                this.rotated = false;
            }
        }
    }
    protected void drawHighlightTile(Graphics2D g) {
        Point mousePos = new Point(this.getMousePosition().x, this.getMousePosition().y);

        if(mousePos.x < gridSize.x && mousePos.y < gridSize.y) {
            this.highlightPoint = new Point((mousePos.x / tileSize.x) * tileSize.x, (mousePos.y / tileSize.y) * tileSize.y);

            g.setColor(Color.GREEN);
            g.drawRect(this.highlightPoint.x, this.highlightPoint.y, tileSize.x, tileSize.y);
        }
    }

    protected void drawLetters(Graphics2D g, Point tileSize) {
        // draw letters next to board
        int asciiCode = 65;
        for (int num = 1; num <= map.getSize(); num++) {

            // draw sand left side
            drawImage(g, Assets.Images.SAND_LEFT,  new Rectangle(0, num * tileSize.y, tileSize.x, tileSize.y));

            Helper.drawCenteredString(g, Character.toString((char) asciiCode), new Rectangle(0, num * tileSize.y, tileSize.x, tileSize.y), Assets.Fonts.DEFAULT_BOLD);
            asciiCode++;

        }
    }

    protected void drawNumbers(Graphics2D g, Point tileSize) {
        g.setColor(Color.BLACK);
        g.setFont(Assets.Fonts.DEFAULT);

        // draw sand top left corner
        drawImage(g, Assets.Images.SAND_TOP_LEFT,  new Rectangle(0, 0, tileSize.x, tileSize.y));

        // draw numbers above board
        for (Integer num = 1; num <= map.getSize(); num++) {

            // draw sand top coner
            drawImage(g, Assets.Images.SAND_TOP,  new Rectangle(num * tileSize.x, 0, tileSize.x, tileSize.y));

            Helper.drawCenteredString(g, num.toString(), new Rectangle(num * tileSize.x, 0, tileSize.x, tileSize.y), Assets.Fonts.DEFAULT_BOLD);
        }
    }

    public void rotate(){
        this.rotated = !this.rotated;
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

        System.out.println(e.getButton());
        this.pressed = true;

        if (this.hoveredMapTile != null && !disabled) {

            if (this.hoveredMapTile.hasShip()) {

                if(e.getButton() == MouseEvent.BUTTON3)
                    map.remove(this.hoveredMapTile.getShip());

                if(!this.selected && e.getButton() == MouseEvent.BUTTON1) {
                    this.selected = true;

                    Point shipTopPos = new Point(this.selectedShip.getPosition().x * tileSize.x + tileSize.x, this.selectedShip.getPosition().y * tileSize.y + tileSize.y);
                    Point mousePos = new Point(this.getMousePosition().x, this.getMousePosition().y);

                    this.mouseShipOffset = new Point(mousePos.x - shipTopPos.x, mousePos.y - shipTopPos.y);
                }
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

        if (this.selected)
            this.rotated = !this.rotated;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}