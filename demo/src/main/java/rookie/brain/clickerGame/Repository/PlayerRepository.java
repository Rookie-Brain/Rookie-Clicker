package rookie.brain.clickerGame.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rookie.brain.clickerGame.Entity.Player;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findAllByOrderByScoreDesc();

    Player findByName(String playerName);
}
