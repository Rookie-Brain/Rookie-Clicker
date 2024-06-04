package rookie.brain.clickerGame.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rookie.brain.clickerGame.Entity.Player;
import rookie.brain.clickerGame.Entity.Ranking;
import rookie.brain.clickerGame.Service.PlayerService;
import rookie.brain.clickerGame.Service.RankingService;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value = "Ranking Management System", description = "Operations pertaining to rankings in Ranking Management System")
public class RankingController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private RankingService rankingService;

    @PostMapping("/rankings")
    @ApiOperation(value = "Create a new ranking", response = Ranking.class)
    public Ranking createRanking(
            @ApiParam(value = "Ranking object to be created", required = true)
            @RequestBody Ranking ranking) {
        return rankingService.saveRanking(ranking);
    }

    @GetMapping("/ranking")
    @ApiOperation(value = "Get the ranking of players", response = List.class)
    public List<Player> getRanking() {
        return playerService.getRanking();
    }

    @GetMapping("/rankings/{id}")
    @ApiOperation(value = "Get a ranking by its ID", response = Ranking.class)
    public Ranking getRankingById(
            @ApiParam(value = "ID of the ranking to retrieve", required = true)
            @PathVariable Long id) {
        return rankingService.getRankingById(id);
    }

    @DeleteMapping("/rankings/{id}")
    @ApiOperation(value = "Delete a ranking by its ID")
    public void deleteRanking(
            @ApiParam(value = "ID of the ranking to delete", required = true)
            @PathVariable Long id) {
        rankingService.deleteRanking(id);
    }
}
