package game;

import java.io.Serializable;

/**
 * Stores the number of ships that can be placed into a map.
 */
public class MapData implements Serializable {
    public int Carriers;
    public int Battleships;
    public int Destroyers;
    public int Submarines;
    public int ShipsCount;
    public int MapSize;
}
