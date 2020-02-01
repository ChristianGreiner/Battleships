package graphics;

import core.Helper;
import game.Assets;
import game.Map;
import game.MapData;
import game.MapGenerator;
import game.ships.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.lang.reflect.Type;
import java.util.HashMap;

public class MapBuilderRenderer extends MapRenderer {
    HashMap<String, Integer> ShipSizes = new HashMap<String, Integer>();
    private int[] shipsLeftCnt = new int[4];
    private Point gridSize;
    private Point tileSize;
    private boolean battleshipSelected;
    private boolean carrierSelected;
    private boolean destroyerSelected;
    private boolean submarineSelected;
    private boolean selected;
    private Point carrierPos;
    private Point battleshipPos;
    private Point destroyerPos;
    private Point submarinePos;
    private boolean rotated = true;
    private boolean drawSizeChanged;
    private HashMap<Type, Integer> shipsCounter;
    private MapData mapData;
    private boolean isInit = false;

    public MapBuilderRenderer(Map map) {
        super(map);
        this.ShipSizes.put("Carrier", 5);
        this.ShipSizes.put("Battleship", 4);
        this.ShipSizes.put("Destroyer", 3);
        this.ShipSizes.put("Submarine", 2);
    }

    public void init(Map map) {
        this.mapData = MapGenerator.getConfigMap().get(map.getSize());
        this.map = map;
        isInit = true;
    }

    @Override
    public void draw() {
        if (!isInit)
            return;

        this.gridSize = new Point(this.getHeight(), this.getHeight());
        this.setGridSize(this.gridSize);
        this.tileSize = new Point(this.getWidth() / 20, this.getWidth() / 20);
        this.carrierPos = new Point((this.getWidth() / 3) * 2, 30);
        this.battleshipPos = new Point((this.getWidth() / 3) * 2, 30 + this.getHeight() / 5);
        this.destroyerPos = new Point((this.getWidth() / 3) * 2, 30 + (this.getHeight() / 5) * 2);
        this.submarinePos = new Point((this.getWidth() / 3) * 2, 30 + (this.getHeight() / 5) * 3);

        this.shipsCounter = map.getShipsCounter();

        shipsLeftCnt[0] = this.mapData.Submarines - this.shipsCounter.get(Submarine.class);
        shipsLeftCnt[1] = this.mapData.Destroyers - this.shipsCounter.get(Destroyer.class);
        shipsLeftCnt[2] = this.mapData.Battleships - this.shipsCounter.get(Battleship.class);
        shipsLeftCnt[3] = this.mapData.Carriers - this.shipsCounter.get(Carrier.class);

        Graphics2D g = super.beginRendering();
        //scaledFont
        Helper.drawLeftAlignedString(g, "Carrier " + shipsLeftCnt[3] + "x", new Rectangle(this.carrierPos.x, this.carrierPos.y, 4 * this.tileSize.x, this.tileSize.y), Assets.Fonts.DEFAULT);
        Helper.drawLeftAlignedString(g, "Battleship " + shipsLeftCnt[2] + "x", new Rectangle(this.battleshipPos.x, this.battleshipPos.y, 4 * this.tileSize.x, this.tileSize.y), Assets.Fonts.DEFAULT);
        Helper.drawLeftAlignedString(g, "Destroyer " + shipsLeftCnt[1] + "x", new Rectangle(this.destroyerPos.x, this.destroyerPos.y, 4 * this.tileSize.x, this.tileSize.y), Assets.Fonts.DEFAULT);
        Helper.drawLeftAlignedString(g, "Submarine " + shipsLeftCnt[0] + "x", new Rectangle(this.submarinePos.x, this.submarinePos.y, 4 * this.tileSize.x, this.tileSize.y), Assets.Fonts.DEFAULT);

        g.setColor(Color.BLACK);

        if (shipsLeftCnt[3] > 0) {
            //draw carrier
            drawImageShip(g, this.ShipSizes.get("Carrier"), Assets.Images.SHIP_CARRIER, new Rectangle(this.carrierPos.x, this.carrierPos.y + 40, this.ShipSizes.get("Carrier") * this.tileSize.x, this.tileSize.y), this.tileSize, true);
            //draw carrier outline
            //g.drawRect(this.carrierPos.x, this.carrierPos.y + 30, this.ShipSizes.get("Carrier") * this.tileSize.x, this.tileSize.y);
        }

        if (shipsLeftCnt[2] > 0) {
            //draw battleship
            drawImageShip(g, this.ShipSizes.get("Battleship"), Assets.Images.SHIP_BATTLESHIP, new Rectangle(this.battleshipPos.x, this.battleshipPos.y + 40, this.ShipSizes.get("Battleship") * this.tileSize.x, this.tileSize.y), this.tileSize, true);
        }

        if (shipsLeftCnt[1] > 0) {
            //draw destroyer
            drawImageShip(g, this.ShipSizes.get("Destroyer"), Assets.Images.SHIP_DESTROYER, new Rectangle(this.destroyerPos.x, this.destroyerPos.y + 40, this.ShipSizes.get("Destroyer") * this.tileSize.x, this.tileSize.y), this.tileSize, true);
        }

        if (shipsLeftCnt[0] > 0) {
            //draw submarine
            drawImageShip(g, this.ShipSizes.get("Submarine"), Assets.Images.SHIP_SUBMARINE, new Rectangle(this.submarinePos.x, this.submarinePos.y + 40, this.ShipSizes.get("Submarine") * this.tileSize.x, this.tileSize.y), this.tileSize, true);
        }

        try {
            moveShip(g);
        } catch (Exception e) {
        }

        super.endRendering();
    }

    void moveShip(Graphics2D g) {

        int shipSize;
        Ship shipToDrop;

        g.setColor(new Color(124, 252, 0, 200));

        if (this.carrierSelected) {
            shipSize = this.ShipSizes.get("Carrier");
            shipToDrop = new Carrier(this.map);
        } else if (this.battleshipSelected) {
            shipSize = this.ShipSizes.get("Battleship");
            shipToDrop = new Battleship(this.map);
        } else if (this.destroyerSelected) {
            shipSize = this.ShipSizes.get("Destroyer");
            shipToDrop = new Destroyer(this.map);
        } else if (this.submarineSelected) {
            shipSize = this.ShipSizes.get("Submarine");
            shipToDrop = new Submarine(this.map);
        } else
            return;

        this.tempPoint = new Point((this.getMousePosition().x / super.tileSize.x) - 1, (this.getMousePosition().y / super.tileSize.y) - 1);

        try {
            this.drawShip(g, shipSize, shipToDrop);
        } catch (Exception e) {
        }

        if (!this.pressed) {
            this.selected = false;
            this.carrierSelected = false;
            this.battleshipSelected = false;
            this.destroyerSelected = false;
            this.submarineSelected = false;
            this.drawSizeChanged = false;

            for (MapRendererListener mouseListener : listener) {
                if (mouseListener != null) {
                    mouseListener.OnShipDropped(this.map, shipToDrop, tempPoint, this.rotated);
                }
            }

            this.rotated = true;
        }

    }

    private void drawShip(Graphics2D g, int shipSize, Ship shipToDrop) {
        int drawnShipSize;
        Point drawingTileSize;
        Point floatingShipMarker;
        boolean drawDropPos = false;

        if (this.getMousePosition().x <= super.tileSize.x * (map.getSize() + 1) && this.getMousePosition().y <= super.tileSize.y * (map.getSize() + 1)) {
            this.drawSizeChanged = true;
            drawDropPos = true;
            floatingShipMarker = new Point((this.getMousePosition().x / super.tileSize.x * super.tileSize.x), (this.getMousePosition().y / super.tileSize.y * super.tileSize.y));
        } else
            floatingShipMarker = null;

        if (this.drawSizeChanged) {
            drawnShipSize = super.tileSize.x * shipSize;
            drawingTileSize = super.tileSize;
        } else {
            drawnShipSize = this.tileSize.x * shipSize;
            drawingTileSize = this.tileSize;
        }

        if (!map.canInsertShip(shipToDrop, this.tempPoint, this.rotated)) {
            g.setColor(Color.RED);
        } else {
            g.setColor(new Color(124, 252, 0, 200));
        }

        g.setStroke(new BasicStroke(3));

        if (this.rotated) {
            g.fillRect(this.getMousePosition().x, this.getMousePosition().y, drawnShipSize, drawingTileSize.y);
            if (drawDropPos && floatingShipMarker != null) {
                g.drawRect(floatingShipMarker.x, floatingShipMarker.y, drawnShipSize, drawingTileSize.y);
            }
        } else {
            g.fillRect(this.getMousePosition().x, this.getMousePosition().y, drawingTileSize.y, drawnShipSize);
            if (drawDropPos && floatingShipMarker != null) {
                g.drawRect(floatingShipMarker.x, floatingShipMarker.y, drawingTileSize.y, drawnShipSize);
                g.setStroke(new BasicStroke());
            }
        }
    }

    @Override
    public void rotate() {
        super.rotate();
        if (this.selected) {
            this.rotated = !this.rotated;
        }
    }

    @Override
    public void drawHighlightTile(Graphics2D g) {
        return;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        this.pressed = true;
        //select carrier
        try {
            if (this.getMousePosition().x >= this.carrierPos.x && this.getMousePosition().x <= this.carrierPos.x + this.ShipSizes.get("Carrier") * this.tileSize.x
                    && this.getMousePosition().y >= this.carrierPos.y + 30 && this.getMousePosition().y <= this.carrierPos.y + 30 + this.tileSize.y
                    && this.shipsLeftCnt[3] > 0) {
                this.carrierSelected = true;
                this.selected = true;
            }

            //select battleship
            else if (this.getMousePosition().x >= this.battleshipPos.x && this.getMousePosition().x <= this.battleshipPos.x + this.ShipSizes.get("Battleship") * this.tileSize.x
                    && this.getMousePosition().y >= this.battleshipPos.y + 30 && this.getMousePosition().y <= this.battleshipPos.y + 30 + this.tileSize.y
                    && this.shipsLeftCnt[2] > 0) {
                this.battleshipSelected = true;
                this.selected = true;
            } else if (this.getMousePosition().x >= this.destroyerPos.x && this.getMousePosition().x <= this.destroyerPos.x + this.ShipSizes.get("Destroyer") * this.tileSize.x
                    && this.getMousePosition().y >= this.destroyerPos.y + 30 && this.getMousePosition().y <= this.destroyerPos.y + 30 + this.tileSize.y
                    && this.shipsLeftCnt[1] > 0) {
                this.destroyerSelected = true;
                this.selected = true;
            } else if (this.getMousePosition().x >= this.submarinePos.x && this.getMousePosition().x <= this.submarinePos.x + this.ShipSizes.get("Submarine") * this.tileSize.x
                    && this.getMousePosition().y >= this.submarinePos.y + 30 && this.getMousePosition().y <= this.submarinePos.y + 30 + this.tileSize.y
                    && this.shipsLeftCnt[0] > 0) {
                this.submarineSelected = true;
                this.selected = true;
            }

        } catch (Exception ex) {
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        this.rotate();
    }

}