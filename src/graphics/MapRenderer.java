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

/**
 * Class to draw the {@link Map} of a Battleships game.
 */
public class MapRenderer extends Renderer implements MouseListener, MouseWheelListener, KeyListener {

    protected Map map;
    protected Point tileSize;
    protected MapTile hoveredMapTile;
    protected Point mouseShipOffset;
    protected boolean selected;
    protected boolean rotated;
    protected boolean pressed;
    protected Point tempPoint;
    protected Point highlightPoint;
    protected ArrayList<MapRendererListener> listener = new ArrayList<>();
    private boolean editorMode = false;
    private boolean enemyMap = false;
    private boolean shipsVisable = true;
    private Point gridSize;
    private Ship selectedShip;
    private BufferedImage backgroundBackground;
    private boolean disabled = false;
    private ArrayList<BufferedImage> explosionFrames = new ArrayList<BufferedImage>();
    private Sprite explosionSprite;
    private Animation explosionAnim;
    private Point explosionAnimPos;

    /**
     * Constructor to initialize the MapRenderer Object.
     * @param map The {@link Map} to be graphically represented.
     */
    public MapRenderer(Map map) {
        this.map = map;
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
        this.addKeyListener(this);
        setBackground(new Color(147, 200, 218));
    }

    /**
     * Disable/Enable the MapRenderer.
     *
     * @param disabled boolean to disable/enable
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isEditorMode() {
        return this.editorMode;
    }

    /**
     * Disables/Enables the editorMode
     *
     * @param editorMode boolean to disable/enable
     */
    public void setEditorMode(boolean editorMode) {
        this.editorMode = editorMode;
    }

    /**
     * Checks whether this is the enemy map or player map
     *
     * @return whether or not this is the enemy map
     */
    public boolean isEnemyMap() {
        return this.enemyMap;
    }

    /**
     * Sets the enemy map.
     *
     * @param enemyMap The enemy map.
     */
    public void setEnemyMap(boolean enemyMap) {
        this.enemyMap = enemyMap;
    }

    /**
     * Adds a map listener.
     *
     * @param listener The map listener
     */
    public void addMapRendererListener(MapRendererListener listener) {
        this.listener.add(listener);
    }

    /**
     * Sets a map to the map renderer.
     *
     * @param map The map that should be rendererd.
     */
    public void setMap(Map map) {
        this.map = map;
        if (this.explosionAnim == null) {
            this.explosionSprite = new Sprite(Assets.Images.EXPLOSION, 48);
            if (this.explosionSprite.getSpriteSheet() != null) {
                for (int i = 0; i < 12; i++)
                    this.explosionFrames.add(explosionSprite.getSprite(i, 0));

                this.explosionAnim = new Animation(explosionFrames.toArray(new BufferedImage[explosionFrames.size()]), 2);
            }
        }
    }

    /**
     * Plays an explosion animation at given point.
     *
     * @param pos The position
     */
    public void playExplosion(Point pos) {
        this.explosionAnimPos = pos;
        this.explosionAnim.reset();
        this.explosionAnim.start();
    }

    /**
     * Sets the ship visable.
     *
     * @param shipsVisable If the ships is visable.
     */
    public void setShipsVisable(boolean shipsVisable) {
        this.shipsVisable = shipsVisable;
    }

    /**
     * Sets the grid size of the map.
     *
     * @param size The size.
     */
    protected void setGridSize(Point size) {
        if (size != null && size.x > 0 && size.y > 0)
            this.gridSize = size;
    }

    /**
     * Sets the size of the map's tiles.
     */
    private void setTileSize() {
        if (this.gridSize != null && this.map != null)
            this.tileSize = new Point((int) ((double) this.gridSize.x / (double) (this.map.getSize() + 1)), (int) ((double) this.gridSize.y / (double) (this.map.getSize() + 1)));
    }

    /**
     * Forces the renderer to resize the map.
     */
    public void resizeMapSize() {
        setGridSize(new Point(this.getWidth(), this.getHeight()));
        this.invalidateBuffer();
        Game.getInstance().getWindow().revalidate();
    }

    /**
     * Begins the rendering batch.
     *
     * @return The Graphics element.
     */
    public Graphics2D beginRendering() {
        Graphics2D g = this.begin();

        if (this.gridSize == null)
            setGridSize(new Point(this.getWidth(), this.getHeight()));

        setTileSize();
        g.setColor(new Color(147, 200, 218));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);

        if (this.backgroundBackground == null) {
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
            if (this.getMousePosition() != null && isEnemyMap()) {
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

    /**
     * Ends the rendering batch.
     */
    public void endRendering() {
        this.end();
    }

    /**
     * Draws everything.
     */
    @Override
    public void draw() {

        if (this.map == null) {
            return;
        }

        if (this.isVisible() && this.isValid()) {
            this.beginRendering();
            this.endRendering();
        }
    }

    /**
     * Updates the explosion animation.
     * @param deltaTime The time difference between 2 consecutive frames.
     */
    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        if (this.explosionAnim != null) {
            this.explosionAnim.update();
        }
    }

    /**
     * Draws a crosshair image.
     * @param g The Graphics element.
     * @param pos position of where the crosshair needs to be drawn.
     */
    private void drawCrosshair(Graphics2D g, Point pos) {
        g.drawImage(Assets.Images.CROSSHAIR, pos.x - 16, pos.y - 16, 32, 32, null);
    }

    /**
     * Draws the grid of the map.
     * @param g The Graphics element.
     * @param tileSize The size of the map's tiles.
     */
    private void drawGrid(Graphics2D g, Point tileSize) {
        g.setColor(new Color(0, 0, 0, 50));
        for (int i = 0; i <= map.getSize() + 1; i++) {
            //vertical
            g.drawLine(i * tileSize.x, 0, i * tileSize.x, (map.getSize() + 1) * tileSize.y);
            //horizontal
            g.drawLine(0, i * tileSize.y, (map.getSize() + 1) * tileSize.x, i * tileSize.y);

        }
    }

    /**
     * Draws an image.
     * @param g The Graphics element.
     * @param image The image to be drawn.
     * @param rect The Rectangle the image needs to be drawn in.
     */
    private void drawImage(Graphics2D g, Image image, Rectangle rect) {
        g.drawImage(image, rect.x, rect.y, rect.width, rect.height, null);
    }

    /**
     * Draws the images of ships.
     * @param g The Graphics Element.
     * @param space The space occupied by a ship.
     * @param image The image to be drawn.
     * @param rect The Rectangle the image needs to be drawn in.
     * @param tileSize The size of the map's tiles.
     * @param rotated Indicator for whether or not the image should be drawn vertically or horizontally.
     */
    protected void drawImageShip(Graphics2D g, int space, Image image, Rectangle rect, Point tileSize, boolean rotated) {
        if (rotated) {
            AffineTransform backup = g.getTransform();
            AffineTransform a = AffineTransform.getRotateInstance(Math.toRadians(90), rect.x, rect.y);
            g.setTransform(a);
            g.drawImage(image, rect.x, rect.y - tileSize.y * space, rect.height, rect.width, null);
            g.setTransform(backup);
        } else
            drawImage(g, image, rect);
    }

    /**
     * Helper method to select the right image for a given ship.
     * @param ship The ship to be drawn.
     * @return An Object array containing the appropriate image and position of a ship.
     */
    private Object[] shipImageHelper(Ship ship) {
        Rectangle shipPos = null;
        Object[] tuple = new Object[2];

        if (ship.isRotated())
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

        tuple[0] = shipImage;
        tuple[1] = shipPos;

        return tuple;

    }

    /**
     * Draws images in the spots of the map that are occupied by ships.
     * @param g The Graphics element.
     * @param tileSize The size of the map's tiles.
     */
    protected void drawShips(Graphics2D g, Point tileSize) {
        Object[] tuple;

        for (int i = 0; i < this.map.getShips().size(); i++) {
            Ship ship = this.map.getShips().get(i);

            tuple = shipImageHelper(ship);

            if (this.shipsVisable)
                drawImageShip(g, ship.getSpace(), (Image) tuple[0], (Rectangle) tuple[1], this.tileSize, ship.isRotated());
        }

        for (int i = 0; i < this.map.getDestroyedShips().size(); i++) {
            Ship ship = this.map.getDestroyedShips().get(i);
            tuple = shipImageHelper(ship);
            drawImageShip(g, ship.getSpace(), (Image) tuple[0], (Rectangle) tuple[1], this.tileSize, ship.isRotated());
        }

        for (int y = 0; y < this.map.getSize(); y++) {
            for (int x = 0; x < this.map.getSize(); x++) {
                MapTile tile = this.map.getTile(new Point(x, y));

                Rectangle tilePos = new Rectangle(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);

                if (tile.isBlocked()) {
                    drawImage(g, Assets.Images.WATER_DARK, tilePos);
                } else if (tile.isHit() && !tile.isBlocked() && !tile.hasShip()) {
                    g.setColor(new Color(255, 255, 255, 100));
                    if (tile.hasShip())
                        if (tile.getShip().isDestroyed())
                            g.setColor(new Color(255, 255, 255, 5));

                    g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);
                } else if (tile.isHit() && !tile.isBlocked() && tile.hasShip()) {
                    g.setColor(new Color(255, 0, 0, 30));
                    g.fillRect(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y);

                    drawImage(g, Assets.Images.HITMARKER, new Rectangle(x * tileSize.x + tileSize.x, y * tileSize.y + tileSize.y, tileSize.x, tileSize.y));
                }
            }
        }
    }

    /**
     * Draws an outline around a ship that the cursor is hovering over.
     * @param g The Graphics element.
     */
    protected void highlightShip(Graphics2D g) {
        //ship selection using mouse
        if (this.getMousePosition() == null || tileSize == null)
            return;

        this.tempPoint = new Point((this.getMousePosition().x / tileSize.x) - 1, (this.getMousePosition().y / tileSize.y) - 1);

        this.hoveredMapTile = this.map.getTile(tempPoint);
        if (this.hoveredMapTile != null) {
            if (this.hoveredMapTile.hasShip() && !this.selected) {

                this.selectedShip = this.hoveredMapTile.getShip();
                if (!this.pressed && this.selectedShip != null) {
                    g.setColor(new Color(124, 252, 0, 200));
                    g.setStroke(new BasicStroke(3));
                    if (this.selectedShip.isRotated()) {
                        g.drawRect(this.selectedShip.getPosition().x * tileSize.x + tileSize.x, this.selectedShip.getPosition().y * tileSize.y + tileSize.y, tileSize.x * this.selectedShip.getSpace(), tileSize.y);
                    } else {
                        g.drawRect(this.selectedShip.getPosition().x * tileSize.x + tileSize.x, this.selectedShip.getPosition().y * tileSize.y + tileSize.y, tileSize.x, tileSize.x * this.selectedShip.getSpace());
                    }
                    g.setStroke(new BasicStroke());
                }
            } else if (!this.selected) {
                this.drawHighlightTile(g);
            }
        }
    }

    /**
     * Draws a rectangle representing a ship that's being moved around.
     * @param g The Graphics element.
     */
    protected void dragAndDropShip(Graphics2D g) {
        //picking up ship
        if (this.selected && this.selectedShip != null) {
            g.setColor(new Color(124, 252, 0, 200));

            // free floating ship
            Point floatingShipPos = new Point(this.getMousePosition().x - this.mouseShipOffset.x, this.getMousePosition().y - this.mouseShipOffset.y);

            //rasterized ship
            Point floatingShipMarker = new Point((this.getMousePosition().x / tileSize.x * tileSize.x) - (this.mouseShipOffset.x / tileSize.x * tileSize.x), (this.getMousePosition().y / tileSize.y * tileSize.y) - (this.mouseShipOffset.y / tileSize.y * tileSize.y));

            Point dropPos = new Point(tempPoint.x - (this.mouseShipOffset.x / tileSize.x - 1) - 1, tempPoint.y - (this.mouseShipOffset.y / tileSize.y - 1) - 1);

            boolean setRotation;
            g.setStroke(new BasicStroke(3));
            if (this.selectedShip.isRotated()) {
                if (!this.rotated) {
                    setRotation = true;
                    if (!map.canMoveShip(this.selectedShip, dropPos, true)) {
                        g.setColor(Color.RED);
                    }
                    g.fillRect(floatingShipPos.x, floatingShipPos.y, tileSize.x * this.selectedShip.getSpace(), tileSize.y);
                    g.drawRect(floatingShipMarker.x, floatingShipMarker.y, tileSize.x * this.selectedShip.getSpace(), tileSize.y);

                } else {
                    setRotation = false;
                    dropPos.x = tempPoint.x;
                    floatingShipMarker.x = this.getMousePosition().x / tileSize.x * tileSize.x;
                    if (!map.canMoveShip(this.selectedShip, dropPos, false)) {
                        g.setColor(Color.RED);
                    }
                    g.fillRect(floatingShipPos.x + this.mouseShipOffset.x - this.mouseShipOffset.y, floatingShipPos.y, tileSize.x, tileSize.x * this.selectedShip.getSpace());
                    g.drawRect(floatingShipMarker.x, floatingShipMarker.y, tileSize.x, tileSize.x * this.selectedShip.getSpace());

                }
            } else {
                if (!this.rotated) {
                    setRotation = false;
                    if (!map.canMoveShip(this.selectedShip, dropPos, false)) {
                        g.setColor(Color.RED);
                    }
                    g.fillRect(floatingShipPos.x, floatingShipPos.y, tileSize.x, tileSize.x * this.selectedShip.getSpace());
                    g.drawRect(floatingShipMarker.x, floatingShipMarker.y, tileSize.x, tileSize.x * this.selectedShip.getSpace());

                } else {
                    setRotation = true;
                    floatingShipMarker.y = this.getMousePosition().y / tileSize.y * tileSize.y;
                    dropPos.y = tempPoint.y;
                    if (!map.canMoveShip(this.selectedShip, dropPos, true)) {
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
                        mouseListener.OnShipDropped(this.map, this.selectedShip, dropPos, setRotation);
                    }
                }
                this.rotated = false;
            }
        }
    }

    /**
     * Highlights the tile the cursor is hovering over.
     * @param g The Graphics element.
     */
    protected void drawHighlightTile(Graphics2D g) {
        Point mousePos = new Point(this.getMousePosition().x, this.getMousePosition().y);

        if (mousePos.x < gridSize.x && mousePos.y < gridSize.y) {
            this.highlightPoint = new Point((mousePos.x / tileSize.x) * tileSize.x, (mousePos.y / tileSize.y) * tileSize.y);

            g.setColor(Color.GREEN);
            g.setStroke(new BasicStroke(3));
            g.drawRect(this.highlightPoint.x, this.highlightPoint.y, tileSize.x, tileSize.y);
            g.setStroke(new BasicStroke());
        }
    }

    /**
     * Draws images of sand and the alphabetical indeces of the map tiles on the left side of the map.
     * @param g The Graphics element.
     * @param tileSize The size of the map's tiles.
     */
    protected void drawLetters(Graphics2D g, Point tileSize) {
        // draw letters next to board
        int asciiCode = 65;
        String text;

        for (int num = 1; num <= map.getSize(); num++) {

            text = Character.toString((char) asciiCode);

            // draw sand left side
            drawImage(g, Assets.Images.SAND_LEFT, new Rectangle(0, num * tileSize.y, tileSize.x, tileSize.y));

            if (asciiCode < 91) {

                Font f = scaleFontToFit(text, tileSize.x, Assets.Fonts.DEFAULT_BOLD, g);
                Helper.drawCenteredString(g, text, new Rectangle(0, num * tileSize.y, tileSize.x, tileSize.y), f);

            } else {
                int temp = asciiCode - 26;

                text = Character.toString((char) temp) + (char) temp;

                Font f = scaleFontToFit(text, tileSize.x, Assets.Fonts.DEFAULT_BOLD, g);
                Helper.drawCenteredString(g, text, new Rectangle(0, num * tileSize.y, tileSize.x, tileSize.y), f);

            }

            asciiCode++;

        }
    }

    /**
     * Draws images of sand and the numerical indeces of the map tiles above the map.
     * @param g The Graphics element
     * @param tileSize The size of the map's tiles.
     */
    protected void drawNumbers(Graphics2D g, Point tileSize) {
        g.setColor(Color.BLACK);
        g.setFont(Assets.Fonts.DEFAULT);

        // draw sand top left corner
        drawImage(g, Assets.Images.SAND_TOP_LEFT, new Rectangle(0, 0, tileSize.x, tileSize.y));

        // draw numbers above board
        for (Integer num = 1; num <= map.getSize(); num++) {

            // draw sand top corner
            drawImage(g, Assets.Images.SAND_TOP, new Rectangle(num * tileSize.x, 0, tileSize.x, tileSize.y));

            Font f = scaleFontToFit(num.toString(), tileSize.x, Assets.Fonts.DEFAULT_BOLD, g);
            Helper.drawCenteredString(g, num.toString(), new Rectangle(num * tileSize.x, 0, tileSize.x, tileSize.y), f);
        }
    }

    /**
     * Scales a text so it fits into a given space.
     * @param text The text to be resized.
     * @param width The width of the space the text needs to fit into.
     * @param pFont The font to be used for the text.
     * @param g The Graphics element.
     * @return The correctly sized font.
     */
    protected Font scaleFontToFit(String text, int width, Font pFont, Graphics g) {
        float fontSize = pFont.getSize();
        float fWidth = g.getFontMetrics(pFont).stringWidth(text);
        if (fWidth <= width)
            return pFont;
        fontSize = ((float) width / fWidth) * fontSize;
        return pFont.deriveFont(fontSize);
    }

    /**
     * Rotates the selected ship
     */
    public void rotate() {
        if (this.selected)
            this.rotated = !this.rotated;
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Handles the pressing of mousebuttons. If the left mousebutton is being pressed the ship the cursor is hovering over gets selected.
     * If the right mousebutton is being pressed the ship the cursor is hovering over gets removed.
     * If the right mousebutton is being pressed while over the map of the opponent a shotFired event gets sent.
     * @param e The MouseEvent.
     */
    @Override
    public void mousePressed(MouseEvent e) {

        this.pressed = true;

        if (this.hoveredMapTile != null && !disabled) {

            if (this.hoveredMapTile.hasShip()) {

                if (e.getButton() == MouseEvent.BUTTON3) {
                    this.map.remove(this.hoveredMapTile.getShip());
                }

                if (!this.selected && e.getButton() == MouseEvent.BUTTON1) {
                    this.selected = true;

                    Point shipTopPos = new Point(this.selectedShip.getPosition().x * tileSize.x + tileSize.x, this.selectedShip.getPosition().y * tileSize.y + tileSize.y);
                    Point mousePos = new Point(this.getMousePosition().x, this.getMousePosition().y);

                    this.mouseShipOffset = new Point(mousePos.x - shipTopPos.x, mousePos.y - shipTopPos.y);
                }
            }
        }

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
                    }
                }
            }
        } catch (Exception ex) {}
    }

    /**
     * Indicates that no mousebutton is being pressed anymore.
     * @param e The MouseEvent.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        this.pressed = false;
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Invokes the rotate method if the MapRenderer isn't in editorMode.
     * @param e The MouseWheelEvent.
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        if (!this.editorMode)
            return;

        this.rotate();
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {

    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }
}