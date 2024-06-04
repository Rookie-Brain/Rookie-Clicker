package rookie.brain.clickerGame.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rookie.brain.clickerGame.Entity.Level;
import rookie.brain.clickerGame.Repository.LevelRepository;

import java.util.List;

@Service
public class LevelService {

    private final LevelRepository levelRepository;

    public LevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    public Level saveLevel(Level level) {
        return levelRepository.save(level);
    }

    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }

    public Level getLevelById(Long id) {
        return levelRepository.findById(id).orElse(null);
    }

    public void deleteLevel(Long id) {
        levelRepository.deleteById(id);
    }
}
