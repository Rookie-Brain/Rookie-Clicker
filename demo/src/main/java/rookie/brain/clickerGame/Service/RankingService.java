package rookie.brain.clickerGame.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rookie.brain.clickerGame.Entity.Ranking;
import rookie.brain.clickerGame.Exception.RankingNotFoundException;
import rookie.brain.clickerGame.Repository.RankingRepository;
import rookie.brain.clickerGame.Utils.ExceptionConstants;

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
        return rankingRepository.findById(id)
                .orElseThrow(() -> new RankingNotFoundException(
                        String.format(ExceptionConstants.RANKING_NOT_FOUND, id)));
    }

    public void deleteRanking(Long id) {
        if (!rankingRepository.existsById(id)) {
            throw new RankingNotFoundException(
                    String.format(ExceptionConstants.RANKING_NOT_FOUND, id));
        }
        rankingRepository.deleteById(id);
    }
}
