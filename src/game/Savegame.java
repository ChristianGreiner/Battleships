package game;

import ai.AI;
import ai.AiDifficulty;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Savegame implements Serializable {

    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date date = new Date();

    private String timestamp = null;
    private Map playerMap = null;
    private Map enemyMap = null;
    private AiDifficulty difficulty = AiDifficulty.Easy;
    private PlayerType currentTurn = null;
    private AI ai = null;

    public Savegame(Map playerMap, Map enemyMap, PlayerType currentTurn, AiDifficulty difficulty, AI ai) {
        this.timestamp = this.dateFormat.format(date);
        this.playerMap = playerMap;
        this.enemyMap = enemyMap;
        this.currentTurn = currentTurn;
        this.difficulty = difficulty;
        this.ai = ai;
    }


    public AI getAi() {
        return ai;
    }

    public String getTimestamp() {
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

    public AiDifficulty getDifficulty() {
        return difficulty;
    }


}
