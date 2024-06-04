package rookie.brain.clickerGame.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rookie.brain.clickerGame.Entity.Game;
import rookie.brain.clickerGame.Entity.Level;
import rookie.brain.clickerGame.Entity.Player;


public interface GameRepository extends JpaRepository<Game, Long> {

    boolean existsByPlayerNameAndLevelId(String playerName, Long levelId);
}
