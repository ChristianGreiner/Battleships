package game;

/**
 * Interface for passing game session data
 */
public interface GameSession {

    /**
     * Initialize the game session.
     * @param data The game session data.
     */
    void initializeGameSession(GameSessionData data);
}
