package rookie.brain.clickerGame.model;

public class Game {
    private int score;

    public Game() {
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score++;
    }
}
