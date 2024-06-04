package rookie.brain.clickerGame.Exception;

public class LevelNotFoundException extends RuntimeException {
    public LevelNotFoundException(String message) {
        super(message);
    }
}
