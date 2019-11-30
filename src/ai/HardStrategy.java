package ai;

import game.HitType;
import game.Map;

import java.awt.*;
import java.util.*;

public class HardStrategy implements AiStrategy {

    private boolean raster = true;
    private boolean shipFocused;
    private Map map;
    private int maxshotperline;
    private HashMap<Integer,Integer> columncounter = new HashMap<>();
    private HashMap<Integer,Integer> rowcounter = new HashMap<>();


    private HumanStrategy internstrategy = new HumanStrategy();

    public HardStrategy(Map map){
        this.map = map;
        this.maxshotperline = map.getSize()/5;
        for (int i = 0; i < map.getSize(); i++){
            this.columncounter.put(i,0);
            this.rowcounter.put(i,0);
        }
    }

    @Override
    public void prepare(HitType type, Point lastHit) {
        //rastercheck(lastHit);
        if(type == HitType.Ship){
            this.shipFocused = true;
            this.internstrategy.prepare(type, lastHit);
            //this.internstrategy.setShipFocused(true);
        }
        if(type == HitType.ShipDestroyed){
            this.shipFocused = false;
            this.internstrategy.prepare(type, lastHit);
        }
    }

    @Override
    public Point process(Map map) {
        Point trypoint = this.internstrategy.process(map);
        if (this.raster && !this.shipFocused){
            //System.out.println("Nächster Punkt: "+trypoint);
            trypoint = rastercheck(trypoint);
        }
        return trypoint;
    }

    private Point rastercheck(Point checkpoint){

        //System.out.println("Vorher"+this.columncounter.keySet());
        //System.out.println("Vorher"+this.columncounter.values());
        //System.out.println("Vorher"+this.rowcounter.keySet());
        //System.out.println("Vorher"+this.rowcounter.values());

        if(this.columncounter.get(checkpoint.x) != null && this.rowcounter.get(checkpoint.y) != null) {
            //System.out.println("Werte columncounter "+this.columncounter.get(checkpoint.x));
            //System.out.println("Werte rowcounter "+this.rowcounter.get(checkpoint.x));
            if (this.columncounter.get(checkpoint.x) < this.maxshotperline && this.rowcounter.get(checkpoint.y) < this.maxshotperline) {
                    this.columncounter.replace(checkpoint.x, this.columncounter.get(checkpoint.x) + 1);
                    this.rowcounter.replace(checkpoint.y, this.rowcounter.get(checkpoint.y) + 1);
            }
            else setnewrasterlocation(checkpoint);
        }
        else setnewrasterlocation(checkpoint);



        for(int i = 0;i < this.map.getSize();i++){
            if (this.columncounter.get(i) != null) {
                if (this.columncounter.get(i) == this.maxshotperline) {
                    this.columncounter.remove(i);
                }
            }
            if (this.rowcounter.get(i) != null) {
                if (this.rowcounter.get(i) == this.maxshotperline) {
                    this.rowcounter.remove(i);
                }
            }
            if (this.columncounter.isEmpty()){
                raster = false;
            }
        }

        //System.out.println("Raster ist: "+raster);
        //System.out.println("Nachher"+this.columncounter.keySet());
        //System.out.println("Nachher"+this.columncounter.values());
        //System.out.println("Nachher"+this.rowcounter.keySet());
        //System.out.println("Nachher"+this.rowcounter.values());

        return checkpoint;
    }

    private Point setnewrasterlocation(Point checkpoint){
        boolean searching = true;
        Object[] columncounterkey = this.columncounter.keySet().toArray();
        Object[] rowcounterkey = this.rowcounter.keySet().toArray();
        int trymarkx = checkpoint.x;
        int trymarky = checkpoint.y;

        while(searching){

            if (this.columncounter.get(checkpoint.x) == null) {
                int indexc = new Random().nextInt(columncounterkey.length);
                //System.out.println("indexc: "+indexc);
                trymarkx = (int) columncounterkey[indexc];
            }

            if (this.rowcounter.get(checkpoint.y) == null){
                int indexr = new Random().nextInt(rowcounterkey.length);
                //System.out.println("indexr: "+indexr);
                trymarky = (int) rowcounterkey[indexr];
            }

            Point newcheckpoint = new Point(trymarkx,trymarky);
            //System.out.println("new setted point for raster: "+newcheckpoint);

            if (this.map.fieldIsUseful(newcheckpoint)){
                searching = false;
                //System.out.println("key: "+newcheckpoint.x+"\nvalue: "+this.columncounter.get(newcheckpoint.x) + 1);
                //System.out.println("key: "+newcheckpoint.y+"\nvalue: "+this.rowcounter.get(newcheckpoint.y) + 1);
                this.columncounter.replace(newcheckpoint.x, this.columncounter.get(newcheckpoint.x) + 1);
                this.rowcounter.replace(newcheckpoint.y, this.rowcounter.get(newcheckpoint.y) + 1);
                return newcheckpoint;
            }
            else{
                    this.columncounter.replace(newcheckpoint.x, this.columncounter.get(newcheckpoint.x) + 1);
                    this.rowcounter.replace(newcheckpoint.y, this.rowcounter.get(newcheckpoint.y) + 1);
                    Point ranPoint = map.getRandomFreeTileIgnoreShip().getPos();
                    if (map.fieldIsUseful(ranPoint)){
                        return  ranPoint;
                    }
            }
        }
        return checkpoint;
    }
}
