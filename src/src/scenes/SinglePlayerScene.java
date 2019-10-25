package scenes;

import core.Drawable;
import core.Updatable;
import game.GameState;
import game.Map;
import game.PlayerType;

public class SinglePlayerScene extends Scene implements Updatable, Drawable {

    private Map playerMap;
    private Map aiMap;
    private PlayerType playerTurn = PlayerType.Player;
    private GameState gameState = GameState.Started;

    public SinglePlayerScene() {
        super("SinglePlayer");
    }

    @Override
    public void update(double deltaTime) {

        if (playerTurn == PlayerType.Player) {

        } else if (playerTurn == PlayerType.AI) {
            // ai = new AI();
            // boolean hit = ai.shot();
            // if(!hit) -> nextPlayer
        }
    }

    @Override
    public void draw() {

    }

}
