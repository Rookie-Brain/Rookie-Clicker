package rookie.brain.clickerGame.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rookie.brain.clickerGame.Entity.Player;
import rookie.brain.clickerGame.Exception.PlayerNotFoundException;
import rookie.brain.clickerGame.Exception.PlayerAlreadyExistsException;
import rookie.brain.clickerGame.Service.PlayerService;
import rookie.brain.clickerGame.Utils.GlobalConstants;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value = "Player Management System", description = "Operations pertaining to players in Player Management System")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/score/{playerId}")
    public int getPlayerScore(
            @ApiParam(value = "ID of the player to get the score", required = true)
            @PathVariable Long playerId) {
        Player player = playerService.getPlayerById(playerId);
        return player != GlobalConstants.NULL_PLAYER ? player.getScore() : GlobalConstants.DEFAULT_SCORE;
    }

    @PostMapping("/click/{playerId}")
    @ApiOperation(value = "Increment score of a player by their ID", response = Integer.class)
    public int click(
            @ApiParam(value = "ID of the player to increment the score", required = true)
            @PathVariable Long playerId) {
        return playerService.incrementPlayerScore(playerId);
    }

    @PostMapping("/players")
    @ApiOperation(value = "Create a new player", response = Player.class)
    public ResponseEntity<?> createPlayer(
            @ApiParam(value = "Player object to be created", required = true)
            @RequestBody Player player) {
        try {
            Player savedPlayer = playerService.savePlayer(player);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPlayer);
        } catch (PlayerAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/players")
    @ApiOperation(value = "Get a list of all players", response = List.class)
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/players/{id}")
    @ApiOperation(value = "Get a player by their ID", response = Player.class)
    public ResponseEntity<?> getPlayerById(
            @ApiParam(value = "ID of the player to retrieve", required = true)
            @PathVariable Long id) {
        try {
            Player player = playerService.getPlayerById(id);
            return ResponseEntity.ok(player);
        } catch (PlayerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/players/{id}")
    @ApiOperation(value = "Delete a player by their ID")
    public void deletePlayer(
            @ApiParam(value = "ID of the player to delete", required = true)
            @PathVariable Long id) {
        playerService.deletePlayer(id);
    }
}
