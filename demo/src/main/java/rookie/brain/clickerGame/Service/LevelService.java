package rookie.brain.clickerGame.Service;

import org.springframework.stereotype.Service;
import rookie.brain.clickerGame.Entity.Level;
import rookie.brain.clickerGame.Exception.LevelAlreadyExistsException;
import rookie.brain.clickerGame.Exception.LevelNotFoundException;
import rookie.brain.clickerGame.Repository.LevelRepository;
import rookie.brain.clickerGame.Utils.ExceptionConstants;

import java.util.List;
import java.util.Optional;

@Service
public class LevelService {

    private final LevelRepository levelRepository;

    public LevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    public Level saveLevel(Level level) {
        Optional<Level> existingLevel = Optional.ofNullable(levelRepository.findByName(level.getName()));
        if (existingLevel.isPresent()) {
            throw new LevelAlreadyExistsException(String.format(ExceptionConstants.LEVEL_ALREADY_EXISTS, level.getName()));
        }
        return levelRepository.save(level);
    }

    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }

    public Level getLevelById(Long id) {
        return levelRepository.findById(id)
                .orElseThrow(() -> new LevelNotFoundException(String.format(ExceptionConstants.LEVEL_NOT_FOUND, id)));
    }

    public void deleteLevel(Long id) {
        if (!levelRepository.existsById(id)) {
            throw new LevelNotFoundException(String.format(ExceptionConstants.LEVEL_NOT_FOUND, id));
        }
        levelRepository.deleteById(id);
    }
}
