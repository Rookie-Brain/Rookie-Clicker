package rookie.brain.clickerGame.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rookie.brain.clickerGame.Entity.Ranking;
import rookie.brain.clickerGame.Repository.RankingRepository;

import java.util.List;

@Service
public class RankingService {

    @Autowired
    private RankingRepository rankingRepository;

    public Ranking saveRanking(Ranking ranking) {
        return rankingRepository.save(ranking);
    }

    public List<Ranking> getAllRankings() {
        return rankingRepository.findAll();
    }

    public Ranking getRankingById(Long id) {
        return rankingRepository.findById(id).orElse(null);
    }

    public void deleteRanking(Long id) {
        rankingRepository.deleteById(id);
    }
}
