package rookie.brain.clickerGame.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rookie.brain.clickerGame.Entity.Game;
import rookie.brain.clickerGame.Entity.Level;
import rookie.brain.clickerGame.Entity.Player;
import rookie.brain.clickerGame.Exception.GameNotFoundException;
import rookie.brain.clickerGame.Exception.LevelNotFoundException;
import rookie.brain.clickerGame.Exception.PlayerNotFoundException;
import rookie.brain.clickerGame.Repository.GameRepository;
import rookie.brain.clickerGame.Repository.LevelRepository;
import rookie.brain.clickerGame.Repository.PlayerRepository;
import rookie.brain.clickerGame.Exception.GameAlreadyExistsException;
import rookie.brain.clickerGame.Utils.ExceptionConstants;
import rookie.brain.clickerGame.Utils.GlobalConstants;

import java.util.List;

@Service
public class GameService {
    private final PlayerRepository playerRepository;
    private final LevelRepository levelRepository;
    private final GameRepository gameRepository;

    @Autowired
    public GameService(PlayerRepository playerRepository, LevelRepository levelRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.levelRepository = levelRepository;
        this.gameRepository = gameRepository;
    }

    public Game saveGame(String playerName, Long levelId) {
        Player player = playerRepository.findByName(playerName);
        if (player == null) {
            throw new PlayerNotFoundException(String.format(ExceptionConstants.PLAYER_NOT_FOUND, playerName));
        }

        Level level = levelRepository.findById(levelId)
                .orElseThrow(() -> new LevelNotFoundException(String.format(ExceptionConstants.LEVEL_NOT_FOUND, levelId)));

        if (gameRepository.existsByPlayerNameAndLevelId(playerName, levelId)) {
            throw new GameAlreadyExistsException(String.format(ExceptionConstants.GAME_ALREADY_EXISTS, playerName, levelId));
        }

        Game game = new Game(player, level);
        return gameRepository.save(game);
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(String.format(ExceptionConstants.GAME_NOT_FOUND, id)));
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public void deleteGame(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new GameNotFoundException(String.format(ExceptionConstants.GAME_NOT_FOUND, id));
        }
        gameRepository.deleteById(id);
    }
}
