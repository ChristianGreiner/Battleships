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
import java.util.HashMap;
import java.lang.reflect.Type;

public class MapBuilderRenderer extends MapRenderer {
    private int[] shipsLeftCnt = new int[4];
    private Point gridSize;
    private Point tileSize;// = new Point( this.getWidth() / 20, this.getHeight() / 20);
    HashMap<String, Integer> ShipSizes = new HashMap<String, Integer>();
    private boolean battleshipSelected;
    private boolean carrierSelected;
    private boolean destroyerSelected;
    private boolean submarineSelected;
    private Point carrierPos;
    private Point battleshipPos;
    private Point destroyerPos;
    private Point submarinePos;
    private boolean rotated = true;
    private boolean drawSizeChanged;
    private HashMap<Integer, MapData> shipNumberLimit;
    private HashMap<Type, Integer> shipsCounter;
    private int mapSize = map.getSize();

    private Ship[] dummyShips = new Ship[4];

    public MapBuilderRenderer(Map map) {
        super(map);
        this.ShipSizes.put("Carrier", 5);
        this.ShipSizes.put("Battleship", 4);
        this.ShipSizes.put("Destroyer", 3);
        this.ShipSizes.put("Submarine", 2);

        dummyShips[0] = new Submarine(map);
        dummyShips[1] = new Destroyer(map);
        dummyShips[2] = new Battleship(map);
        dummyShips[3] = new Carrier(map);

        this.shipNumberLimit = MapGenerator.getConfigMap();


    }

    @Override
    public void draw() {
        this.gridSize = new Point(this.getWidth() / 2, this.getWidth() / 2);
        this.setGridSize(this.gridSize);
        this.tileSize = new Point( this.getWidth() / 20, this.getWidth() / 20);
        this.carrierPos = new Point((this.getWidth() / 3) * 2, 30);
        this.battleshipPos = new Point((this.getWidth() / 3) * 2, 30 + this.getHeight() / 5);
        this.destroyerPos = new Point((this.getWidth() / 3) * 2, 30 + (this.getHeight() / 5) * 2);
        this.submarinePos = new Point((this.getWidth() / 3) * 2, 30 + (this.getHeight() / 5) * 3);

        this.shipsCounter = map.getShipsCounter();

        shipsLeftCnt[0] = this.shipNumberLimit.get(map.getSize()).Submarines - this.shipsCounter.get(dummyShips[0].getClass());
        shipsLeftCnt[1] = this.shipNumberLimit.get(map.getSize()).Destroyers - this.shipsCounter.get(dummyShips[1].getClass());
        shipsLeftCnt[2] = this.shipNumberLimit.get(map.getSize()).Battleships - this.shipsCounter.get(dummyShips[2].getClass());
        shipsLeftCnt[3] = this.shipNumberLimit.get(map.getSize()).Carriers - this.shipsCounter.get(dummyShips[3].getClass());

        Graphics2D g = super.beginRendering();

        Helper.drawCenteredString(g, "Carrier " + shipsLeftCnt[3] + "x", new Rectangle(this.carrierPos.x, this.carrierPos.y, 4 * this.tileSize.x, this.tileSize.y), Assets.Fonts.DEFAULT);
        Helper.drawCenteredString(g, "Battleship " + shipsLeftCnt[2] + "x", new Rectangle(this.battleshipPos.x, this.battleshipPos.y, 4 * this.tileSize.x, this.tileSize.y), Assets.Fonts.DEFAULT);
        Helper.drawCenteredString(g, "Destroyer " + shipsLeftCnt[1] + "x", new Rectangle(this.destroyerPos.x, this.destroyerPos.y, 4 * this.tileSize.x, this.tileSize.y), Assets.Fonts.DEFAULT);
        Helper.drawCenteredString(g, "Submarine " + shipsLeftCnt[0] + "x", new Rectangle(this.submarinePos.x, this.submarinePos.y, 4*  this.tileSize.x, this.tileSize.y), Assets.Fonts.DEFAULT);

        g.setColor(Color.BLACK);

        if(shipsLeftCnt[3] > 0) {
            //draw carrier
            drawImageShip(g, this.ShipSizes.get("Carrier"), Assets.Images.SHIP_CARRIER, new Rectangle(this.carrierPos.x + 20, this.carrierPos.y + 30, this.ShipSizes.get("Carrier") * this.tileSize.x, this.tileSize.y), true);
            //g.fillRect(this.carrierPos.x, this.carrierPos.y + 30, this.ShipSizes.get("Carrier") * this.tileSize.x, this.tileSize.y);
            //draw carrier outline
            g.drawRect(this.carrierPos.x, this.carrierPos.y + 30, this.ShipSizes.get("Carrier") * this.tileSize.x, this.tileSize.y);
        }

        if(shipsLeftCnt[2] > 0) {
            //draw battleship
            drawImageShip(g, this.ShipSizes.get("Battleship"), Assets.Images.SHIP_BATTLESHIP, new Rectangle(this.battleshipPos.x + 17, this.battleshipPos.y + 30, this.ShipSizes.get("Battleship") * this.tileSize.x, this.tileSize.y), true);
            //draw battleship outline
            g.drawRect(this.battleshipPos.x, this.battleshipPos.y + 30, this.ShipSizes.get("Battleship") * this.tileSize.x, this.tileSize.y);
            //g.fillRect(this.battleshipPos.x, this.battleshipPos.y + 30, this.ShipSizes.get("Battleship") * this.tileSize.x, this.tileSize.y);
        }

        if(shipsLeftCnt[1] > 0) {
            //draw destroyer
            drawImageShip(g, this.ShipSizes.get("Destroyer"), Assets.Images.SHIP_DESTROYER, new Rectangle(this.destroyerPos.x + 13, this.destroyerPos.y + 30, this.ShipSizes.get("Destroyer") * this.tileSize.x, this.tileSize.y), true);
            //g.fillRect(this.destroyerPos.x, this.destroyerPos.y + 30, this.ShipSizes.get("Destroyer") * this.tileSize.x, this.tileSize.y);
            //draw destroyer outline
            g.drawRect(this.destroyerPos.x, this.destroyerPos.y + 30, this.ShipSizes.get("Destroyer") * this.tileSize.x, this.tileSize.y);
        }

        if(shipsLeftCnt[0] > 0) {
            //draw submarine
            drawImageShip(g, this.ShipSizes.get("Submarine"), Assets.Images.SHIP_SUBMARINE, new Rectangle(this.submarinePos.x + 8, this.submarinePos.y + 30, this.ShipSizes.get("Submarine") * this.tileSize.x, this.tileSize.y), true);
            //g.fillRect(this.submarinePos.x, this.submarinePos.y + 30, this.ShipSizes.get("Submarine") * this.tileSize.x, this.tileSize.y);
            //draw battleship outline
            g.drawRect(this.submarinePos.x, this.submarinePos.y + 30, this.ShipSizes.get("Submarine") * this.tileSize.x, this.tileSize.y);
        }

        try {
            moveShip(g);
        }catch (Exception e){}

        super.endRendering();
    }

    void moveShip(Graphics2D g) {

        Point shipPos;
        int shipSize;
        Ship shipToDrop;

        g.setColor(new Color(124, 252, 0, 200));

        if(this.carrierSelected){
            shipPos = this.carrierPos;
            shipSize = this.ShipSizes.get("Carrier");
            shipToDrop = new Carrier(this.map);
        }
        else if(this.battleshipSelected){
            shipPos = this.battleshipPos;
            shipSize = this.ShipSizes.get("Battleship");
            shipToDrop = new Battleship(this.map);
        }
        else if(this.destroyerSelected){
            shipPos = this.destroyerPos;
            shipSize = this.ShipSizes.get("Destroyer");
            shipToDrop = new Destroyer(this.map);
        }
        else if(this.submarineSelected){
            shipPos = this.submarinePos;
            shipSize = this.ShipSizes.get("Submarine");
            shipToDrop = new Submarine(this.map);
        }
        else
            return;


        g.drawRect(shipPos.x, shipPos.y + 30, shipSize * this.tileSize.x, this.tileSize.y);

        this.tempPoint = new Point((this.getMousePosition().x / super.tileSize.x) - 1, (this.getMousePosition().y / super.tileSize.y) - 1);

        try {
            this.drawShip(g, shipSize, shipToDrop);
        } catch (Exception e) {}

        if (!this.pressed) {
            this.carrierSelected = false;
            this.battleshipSelected = false;
            this.destroyerSelected = false;
            this.submarineSelected = false;
            this.drawSizeChanged = false;

            //this.tempPoint = new Point((this.getMousePosition().x / super.tileSize.x) - 1, (this.getMousePosition().y / super.tileSize.y) - 1);
            System.out.println("**builder**");
            System.out.println(rotated);
            for (MapRendererListener mouseListener : listener) {
                if (mouseListener != null) {
                    mouseListener.OnShipDropped(this.map, shipToDrop, tempPoint, this.rotated);
                    System.out.println(shipToDrop.isRotated());
                }
            }

            this.rotated = true;
        }

    }

    private void drawShip(Graphics2D g, int shipSize, Ship shipToDrop)
    {
        int drawnShipSize;
        Point drawingTileSize;
        Point floatingShipMarker;
        boolean drawDropPos = false;

        if (this.getMousePosition().x <= super.tileSize.x * (map.getSize() + 1) && this.getMousePosition().y <= super.tileSize.y * (map.getSize() + 1)) {
            this.drawSizeChanged = true;
            drawDropPos = true;
            floatingShipMarker = new Point((this.getMousePosition().x / super.tileSize.x * super.tileSize.x), (this.getMousePosition().y / super.tileSize.y * super.tileSize.y));
        }
        else
            floatingShipMarker = null;

        if(this.drawSizeChanged) {
            drawnShipSize = super.tileSize.x * shipSize;
            drawingTileSize = super.tileSize;
        }
        else{
            drawnShipSize = this.tileSize.x * shipSize;
            drawingTileSize = this.tileSize;
        }

        if(!map.canInsertShip(shipToDrop, this.tempPoint,this.rotated)) {
            g.setColor(Color.RED);
        }
        else {
            g.setColor(new Color(124, 252, 0, 200));
        }

        if(this.rotated) {
            g.fillRect(this.getMousePosition().x, this.getMousePosition().y, drawnShipSize, drawingTileSize.y);
            if (drawDropPos && floatingShipMarker != null) {
                //g.setColor(Color.GREEN);
                g.drawRect(floatingShipMarker.x, floatingShipMarker.y, drawnShipSize, drawingTileSize.y);
            }
        }
        else {
            g.fillRect(this.getMousePosition().x, this.getMousePosition().y, drawingTileSize.y, drawnShipSize);
            if(drawDropPos && floatingShipMarker != null) {
                // g.setColor(Color.GREEN);
                g.drawRect(floatingShipMarker.x, floatingShipMarker.y, drawingTileSize.y, drawnShipSize);
            }
        }
    }

    @Override
    public void rotate(){
        super.rotate();
        this.rotated = !this.rotated;
    }

    @Override
    public void drawHighlightTile(Graphics2D g){
        return;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        this.pressed = true;
        //select carrier

        try {
            if(this.getMousePosition().x >= this.carrierPos.x && this.getMousePosition().x <= this.carrierPos.x + this.ShipSizes.get("Carrier") * this.tileSize.x
                    && this.getMousePosition().y >= this.carrierPos.y + 30 && this.getMousePosition().y <= this.carrierPos.y + 30 + this.tileSize.y
                    && this.shipsLeftCnt[3] > 0)
                this.carrierSelected = true;

                //select battleship
            else if(this.getMousePosition().x >= this.battleshipPos.x && this.getMousePosition().x <= this.battleshipPos.x + this.ShipSizes.get("Battleship") * this.tileSize.x
                    && this.getMousePosition().y >= this.battleshipPos.y + 30 && this.getMousePosition().y <= this.battleshipPos.y + 30 + this.tileSize.y
                    && this.shipsLeftCnt[2] > 0)
                this.battleshipSelected = true;

            else if(this.getMousePosition().x >= this.destroyerPos.x && this.getMousePosition().x <= this.destroyerPos.x + this.ShipSizes.get("Destroyer") * this.tileSize.x
                    && this.getMousePosition().y >= this.destroyerPos.y + 30 && this.getMousePosition().y <= this.destroyerPos.y + 30 + this.tileSize.y
                    && this.shipsLeftCnt[1] > 0)
                this.destroyerSelected = true;

            else if(this.getMousePosition().x >= this.submarinePos.x && this.getMousePosition().x <= this.submarinePos.x + this.ShipSizes.get("Submarine") * this.tileSize.x
                    && this.getMousePosition().y >= this.submarinePos.y + 30 && this.getMousePosition().y <= this.submarinePos.y + 30 + this.tileSize.y
                    && this.shipsLeftCnt[0] > 0)
                this.submarineSelected = true;

        } catch (Exception ex) {}
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        super.mouseWheelMoved(e);

        if (this.carrierSelected)
            this.rotated = !this.rotated;
        else if (this.battleshipSelected)
            this.rotated = !this.rotated;
        else if (this.destroyerSelected)
            this.rotated = !this.rotated;
        else if (this.submarineSelected)
            this.rotated = !this.rotated;
    }

}