package rookie.brain.clickerGame.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rookie.brain.clickerGame.Entity.Level;
import rookie.brain.clickerGame.Entity.Player;
import rookie.brain.clickerGame.Entity.Ranking;
import rookie.brain.clickerGame.Service.LevelService;
import rookie.brain.clickerGame.Service.PlayerService;
import rookie.brain.clickerGame.Service.RankingService;
import rookie.brain.clickerGame.model.Game;

import java.util.List;


@RestController
@RequestMapping("/api")
public class GameController {

    private final Game game;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private LevelService levelService;

    @Autowired
    private RankingService rankingService;

    public GameController() {
        this.game = new Game();
    }


    @GetMapping("/score/{playerId}")
    public int getPlayerScore(@PathVariable Long playerId) {
        Player player = playerService.getPlayerById(playerId);
        return player != null ? player.getScore() : 0;
    }

    @PostMapping("/click/{playerId}")
    public int click(@PathVariable Long playerId) {
        Player player = playerService.getPlayerById(playerId);
        if (player != null) {
            int newScore = player.getScore() + 1; // Incrementar el score
            playerService.updatePlayerScore(playerId, newScore);
            return newScore;
        }
        return 0;
    }

    // Player endpoints
    @PostMapping("/players")
    public Player createPlayer(@RequestBody Player player) {
        return playerService.savePlayer(player);
    }

    @GetMapping("/players")
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/players/{id}")
    public Player getPlayerById(@PathVariable Long id) {
        return playerService.getPlayerById(id);
    }

    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
    }

    // Level endpoints
    @PostMapping("/levels")
    public Level createLevel(@RequestBody Level level) {
        return levelService.saveLevel(level);
    }

    @GetMapping("/levels")
    public List<Level> getAllLevels() {
        return levelService.getAllLevels();
    }

    @GetMapping("/levels/{id}")
    public Level getLevelById(@PathVariable Long id) {
        return levelService.getLevelById(id);
    }

    @DeleteMapping("/levels/{id}")
    public void deleteLevel(@PathVariable Long id) {
        levelService.deleteLevel(id);
    }

    // Ranking endpoints
    @PostMapping("/rankings")
    public Ranking createRanking(@RequestBody Ranking ranking) {
        return rankingService.saveRanking(ranking);
    }

    @GetMapping("/ranking")
    public List<Player> getRanking() {
        return playerService.getRanking();
    }

    @GetMapping("/rankings/{id}")
    public Ranking getRankingById(@PathVariable Long id) {
        return rankingService.getRankingById(id);
    }

    @DeleteMapping("/rankings/{id}")
    public void deleteRanking(@PathVariable Long id) {
        rankingService.deleteRanking(id);
    }
}
