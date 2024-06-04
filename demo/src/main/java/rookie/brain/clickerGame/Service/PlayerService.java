package rookie.brain.clickerGame.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rookie.brain.clickerGame.Entity.Player;
import rookie.brain.clickerGame.Exception.PlayerAlreadyExistsException;
import rookie.brain.clickerGame.Exception.PlayerNotFoundException;
import rookie.brain.clickerGame.Repository.PlayerRepository;
import rookie.brain.clickerGame.Utils.ExceptionConstants;
import rookie.brain.clickerGame.Utils.GlobalConstants;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {


        @Autowired
        private PlayerRepository playerRepository;

    public Player savePlayer(Player player) {
        Optional<Player> existingPlayer = Optional.ofNullable(playerRepository.findByName(player.getName()));
        if (existingPlayer.isPresent()) {
            throw new PlayerAlreadyExistsException(String.format(ExceptionConstants.PLAYER_ALREADY_EXISTS, player.getName()));
        }
        return playerRepository.save(player);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(String.format(ExceptionConstants.PLAYER_NOT_FOUND, id)));
    }

        public void deletePlayer(Long id) {
            playerRepository.deleteById(id);
        }

    public void updatePlayerScore(Long playerId, int newScore) {
        Player player = getPlayerById(playerId);
        player.setScore(newScore);
        playerRepository.save(player);
    }

    public List<Player> getRanking() {
        return playerRepository.findAllByOrderByScoreDesc();
    }

    public int incrementPlayerScore(Long playerId) {
        Player player = getPlayerById(playerId);
        int newScore = player.getScore() + GlobalConstants.CLICK_SCORE;
        updatePlayerScore(playerId, newScore);
        return newScore;
    }
}
