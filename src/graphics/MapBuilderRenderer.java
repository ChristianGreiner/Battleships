package graphics;

import core.Helper;
import game.Assets;
import game.Map;
import game.ships.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;

public class MapBuilderRenderer extends MapRenderer {
    private int[] shipCnt = {3,3,3,3}; //new int[4];
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

    public MapBuilderRenderer(Map map) {
        super(map);
        this.ShipSizes.put("Carrier", 5);
        this.ShipSizes.put("Battleship", 4);
        this.ShipSizes.put("Destroyer", 3);
        this.ShipSizes.put("Submarine", 2);
    }

    @Override
    public void draw() {
        this.gridSize = new Point(this.getWidth() / 2, this.getWidth() / 2);
        this.setGridSize(this.gridSize);
        this.tileSize = new Point( this.getWidth() / 20, this.getHeight() / 20);
        this.carrierPos = new Point((this.getWidth() / 3) * 2, 30);
        this.battleshipPos = new Point((this.getWidth() / 3) * 2, 30 + this.getHeight() / 5);
        this.destroyerPos = new Point((this.getWidth() / 3) * 2, 30 + (this.getHeight() / 5) * 2);
        this.submarinePos = new Point((this.getWidth() / 3) * 2, 30 + (this.getHeight() / 5) * 3);

        Graphics g = super.beginRendering();

        Helper.drawCenteredString(g, "Carrier " + shipCnt[0] + "x", new Rectangle(this.carrierPos.x, this.carrierPos.y, 4 * this.tileSize.x, this.tileSize.y), Assets.Fonts.DEFAULT);
        Helper.drawCenteredString(g, "Battleship " + shipCnt[0] + "x", new Rectangle(this.battleshipPos.x, this.battleshipPos.y, 4 * this.tileSize.x, this.tileSize.y), Assets.Fonts.DEFAULT);
        Helper.drawCenteredString(g, "Destroyer " + shipCnt[0] + "x", new Rectangle(this.destroyerPos.x, this.destroyerPos.y, 4 * this.tileSize.x, this.tileSize.y), Assets.Fonts.DEFAULT);
        Helper.drawCenteredString(g, "Submarine " + shipCnt[0] + "x", new Rectangle(this.submarinePos.x, this.submarinePos.y, 4*  this.tileSize.x, this.tileSize.y), Assets.Fonts.DEFAULT);

        if(shipCnt[0] > 0) {

            g.setColor(Color.DARK_GRAY);
            //draw carrier
            g.fillRect(this.carrierPos.x, this.carrierPos.y + 30, this.ShipSizes.get("Carrier") * this.tileSize.x, this.tileSize.y);
            //draw battleship
            g.fillRect(this.battleshipPos.x, this.battleshipPos.y + 30, this.ShipSizes.get("Battleship") * this.tileSize.x, this.tileSize.y);
            //draw destroyer
            g.fillRect(this.destroyerPos.x, this.destroyerPos.y + 30, this.ShipSizes.get("Destroyer") * this.tileSize.x, this.tileSize.y);
            //draw submarine
            g.fillRect(this.submarinePos.x, this.submarinePos.y + 30, this.ShipSizes.get("Submarine") * this.tileSize.x, this.tileSize.y);

            g.setColor(Color.BLACK);
            //draw carrier outline
            g.drawRect(this.carrierPos.x, this.carrierPos.y + 30, this.ShipSizes.get("Carrier") * this.tileSize.x, this.tileSize.y);
            //draw battleship outline
            g.drawRect(this.battleshipPos.x, this.battleshipPos.y + 30, this.ShipSizes.get("Battleship") * this.tileSize.x, this.tileSize.y);
            //draw carrier outline
            g.drawRect(this.destroyerPos.x, this.destroyerPos.y + 30, this.ShipSizes.get("Destroyer") * this.tileSize.x, this.tileSize.y);
            //draw battleship outline
            g.drawRect(this.submarinePos.x, this.submarinePos.y + 30, this.ShipSizes.get("Submarine") * this.tileSize.x, this.tileSize.y);
        }

        try {
            moveShip(g);
        }catch (Exception e){}

        super.endRendering();
    }

    void moveShip(Graphics g) {

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
            System.out.println(rotated);
            for (MapRendererListener mouseListener : listener) {
                if (mouseListener != null) {
                    mouseListener.OnShipDropped(this.map, shipToDrop, tempPoint, this.rotated);
                }
            }

            this.rotated = true;
        }

    }

    private void drawShip(Graphics g, int shipSize, Ship shipToDrop)
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
    public void drawHighlightTile(Graphics g){
        return;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        this.pressed = true;
        //select carrier

        try {
            if(this.getMousePosition().x >= this.carrierPos.x && this.getMousePosition().x <= this.carrierPos.x + this.ShipSizes.get("Carrier") * this.tileSize.x
                    && this.getMousePosition().y >= this.carrierPos.y + 30 && this.getMousePosition().y <= this.carrierPos.y + 30 + this.tileSize.y)
                this.carrierSelected = true;

                //select battleship
            else if(this.getMousePosition().x >= this.battleshipPos.x && this.getMousePosition().x <= this.battleshipPos.x + this.ShipSizes.get("Battleship") * this.tileSize.x
                    && this.getMousePosition().y >= this.battleshipPos.y + 30 && this.getMousePosition().y <= this.battleshipPos.y + 30 + this.tileSize.y)
                this.battleshipSelected = true;

            else if(this.getMousePosition().x >= this.destroyerPos.x && this.getMousePosition().x <= this.destroyerPos.x + this.ShipSizes.get("Destroyer") * this.tileSize.x
                    && this.getMousePosition().y >= this.destroyerPos.y + 30 && this.getMousePosition().y <= this.destroyerPos.y + 30 + this.tileSize.y)
                this.destroyerSelected = true;

            else if(this.getMousePosition().x >= this.submarinePos.x && this.getMousePosition().x <= this.submarinePos.x + this.ShipSizes.get("Submarine") * this.tileSize.x
                    && this.getMousePosition().y >= this.submarinePos.y + 30 && this.getMousePosition().y <= this.submarinePos.y + 30 + this.tileSize.y)
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