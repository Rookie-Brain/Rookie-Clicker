package rookie.brain.clickerGame.controller;


import rookie.brain.clickerGame.model.Game;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final Game game;

    public GameController() {
        this.game = new Game();
    }

    @GetMapping("/score")
    public int getScore() {
        return game.getScore();
    }

    @PostMapping("/click")
    public int click() {
        game.incrementScore();
        return game.getScore();
    }
}
