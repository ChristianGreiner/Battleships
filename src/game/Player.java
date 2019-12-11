package game;

public class Player {

    private int playedGames;
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    public String getName() {
        return name;
    }
}
