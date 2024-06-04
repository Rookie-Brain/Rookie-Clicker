package rookie.brain.clickerGame.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rookie.brain.clickerGame.Entity.Player;
import rookie.brain.clickerGame.Repository.PlayerRepository;

import java.util.List;

@Service
public class PlayerService {


        @Autowired
        private PlayerRepository playerRepository;

        public Player savePlayer(Player player) {
            return playerRepository.save(player);
        }

        public List<Player> getAllPlayers() {
            return playerRepository.findAll();
        }

        public Player getPlayerById(Long id) {
            return playerRepository.findById(id).orElse(null);
        }

        public void deletePlayer(Long id) {
            playerRepository.deleteById(id);
        }

    public Player updatePlayerScore(Long id, int score) {
        Player player = playerRepository.findById(id).orElse(null);
        if (player != null) {
            player.setScore(score);
            return playerRepository.save(player);
        }
        return null;
    }

    public List<Player> getRanking() {
        // Consulta la base de datos y devuelve la lista de jugadores ordenados por puntaje
        return playerRepository.findAllByOrderByScoreDesc();
    }
}
