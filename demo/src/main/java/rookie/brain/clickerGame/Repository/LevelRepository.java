package rookie.brain.clickerGame.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rookie.brain.clickerGame.Entity.Level;

public interface LevelRepository extends JpaRepository<Level, Long> {
}