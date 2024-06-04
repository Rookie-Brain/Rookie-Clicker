package rookie.brain.clickerGame.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rookie.brain.clickerGame.Entity.Level;
import rookie.brain.clickerGame.Service.LevelService;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value = "Level Management System", description = "Operations pertaining to levels in Level Management System")
public class LevelController {
    @Autowired
    private LevelService levelService;

    @PostMapping("/levels")
    @ApiOperation(value = "Create a new level", response = Level.class)
    public Level createLevel(
            @ApiParam(value = "Level object to be created", required = true)
            @RequestBody Level level) {
        return levelService.saveLevel(level);
    }

    @GetMapping("/levels")
    @ApiOperation(value = "Get a list of all levels", response = List.class)
    public List<Level> getAllLevels() {
        return levelService.getAllLevels();
    }

    @GetMapping("/levels/{id}")
    @ApiOperation(value = "Get a level by its ID", response = Level.class)
    public Level getLevelById(
            @ApiParam(value = "ID of the level to retrieve", required = true)
            @PathVariable Long id) {
        return levelService.getLevelById(id);
    }

    @DeleteMapping("/levels/{id}")
    @ApiOperation(value = "Delete a level by its ID")
    public void deleteLevel(
            @ApiParam(value = "ID of the level to delete", required = true)
            @PathVariable Long id) {
        levelService.deleteLevel(id);
    }
}
