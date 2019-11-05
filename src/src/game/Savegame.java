package game;

import java.sql.Timestamp;

public class Savegame {
    private Timestamp timestamp;

    public Savegame(Timestamp timestamp, Map playerMap, Map enemyMap, PlayerType currentTurn) {
        this.timestamp = timestamp;
        this.playerMap = playerMap;
        this.enemyMap = enemyMap;
        this.currentTurn = currentTurn;
    }

    private Map playerMap;
    private Map enemyMap;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Map getPlayerMap() {
        return playerMap;
    }

    public Map getEnemyMap() {
        return enemyMap;
    }

    public PlayerType getCurrentTurn() {
        return currentTurn;
    }

    private PlayerType currentTurn;

}
