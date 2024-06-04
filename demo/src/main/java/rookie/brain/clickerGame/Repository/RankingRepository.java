package rookie.brain.clickerGame.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rookie.brain.clickerGame.Entity.Ranking;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
}
