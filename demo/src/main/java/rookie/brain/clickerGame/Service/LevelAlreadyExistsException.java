package rookie.brain.clickerGame.Service;

public class LevelAlreadyExistsException extends RuntimeException {
    public LevelAlreadyExistsException(String message) {
        super(message);
    }
}
