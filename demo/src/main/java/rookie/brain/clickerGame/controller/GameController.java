package rookie.brain.clickerGame.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rookie.brain.clickerGame.Entity.Game;
import rookie.brain.clickerGame.Exception.GameAlreadyExistsException;
import rookie.brain.clickerGame.Exception.GameNotFoundException;
import rookie.brain.clickerGame.Exception.LevelNotFoundException;
import rookie.brain.clickerGame.Exception.PlayerNotFoundException;
import rookie.brain.clickerGame.Service.GameService;

import java.util.List;


@RestController
@RequestMapping("/api")
@Api(value = "Game Management System", description = "Operations pertaining to games in Game Management System")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/games")
    @ApiOperation(value = "Create a new game", response = Game.class)
    public ResponseEntity<?> createGame(
            @ApiParam(value = "Name of the player", required = true) @RequestParam String playerName,
            @ApiParam(value = "ID of the level", required = true) @RequestParam Long levelId) {
        try {
            Game game = gameService.saveGame(playerName, levelId);
            return ResponseEntity.status(HttpStatus.CREATED).body(game);
        } catch (PlayerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (LevelNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (GameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/games/{id}")
    @ApiOperation(value = "Get a game by its ID", response = Game.class)
    public ResponseEntity<?> getGameById(
            @ApiParam(value = "ID of the game to retrieve", required = true) @PathVariable Long id) {
        try {
            Game game = gameService.getGameById(id);
            return ResponseEntity.ok(game);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/games")
    @ApiOperation(value = "Get all games", response = List.class)
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @DeleteMapping("/games/{id}")
    @ApiOperation(value = "Delete a game by its ID")
    public ResponseEntity<?> deleteGame(
            @ApiParam(value = "ID of the game to delete", required = true) @PathVariable Long id) {
        try {
            gameService.deleteGame(id);
            return ResponseEntity.ok().build();
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
