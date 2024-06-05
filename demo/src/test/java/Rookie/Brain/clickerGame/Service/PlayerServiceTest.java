package Rookie.Brain.clickerGame.Service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rookie.brain.clickerGame.Entity.Player;
import rookie.brain.clickerGame.Exception.PlayerAlreadyExistsException;
import rookie.brain.clickerGame.Exception.PlayerNotFoundException;
import rookie.brain.clickerGame.Repository.PlayerRepository;
import rookie.brain.clickerGame.Service.PlayerService;
import rookie.brain.clickerGame.Utils.ExceptionConstants;
import rookie.brain.clickerGame.Utils.GlobalConstants;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player(1L, "John", 0, 0);
    }

    @Test
    public void savePlayer_ValidInput_Success() {
        when(playerRepository.findByName("John")).thenReturn(null);
        when(playerRepository.save(player)).thenReturn(player);

        Player savedPlayer = playerService.savePlayer(player);

        assertNotNull(savedPlayer);
        assertEquals(player, savedPlayer);

        verify(playerRepository, times(1)).findByName("John");
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    public void savePlayer_PlayerAlreadyExists_ExceptionThrown() {
        when(playerRepository.findByName("John")).thenReturn(player);

        assertThrows(PlayerAlreadyExistsException.class, () -> playerService.savePlayer(player));

        verify(playerRepository, times(1)).findByName("John");
        verify(playerRepository, times(0)).save(player);
    }

    @Test
    public void getAllPlayers_ReturnsListOfPlayers() {
        List<Player> players = Arrays.asList(
                new Player(1L, "John", 0, 0),
                new Player(2L, "Jane", 0, 0)
        );

        when(playerRepository.findAll()).thenReturn(players);

        List<Player> result = playerService.getAllPlayers();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("Jane", result.get(1).getName());

        verify(playerRepository, times(1)).findAll();
    }

    @Test
    public void getPlayerById_ValidId_Success() {
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        Player foundPlayer = playerService.getPlayerById(1L);

        assertNotNull(foundPlayer);
        assertEquals(player, foundPlayer);

        verify(playerRepository, times(1)).findById(1L);
    }

    @Test
    public void getPlayerById_InvalidId_ExceptionThrown() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PlayerNotFoundException.class, () -> playerService.getPlayerById(1L));

        verify(playerRepository, times(1)).findById(1L);
    }

    @Test
    public void deletePlayer_ValidId_Success() {
        doNothing().when(playerRepository).deleteById(1L);

        playerService.deletePlayer(1L);

        verify(playerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void updatePlayerScore_ValidId_Success() {
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        playerService.updatePlayerScore(1L, 10);

        assertEquals(10, player.getScore());
        verify(playerRepository, times(1)).findById(1L);
        verify(playerRepository, times(1)).save(player);
    }




}
