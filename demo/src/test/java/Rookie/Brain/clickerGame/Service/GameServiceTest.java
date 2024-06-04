package Rookie.Brain.clickerGame.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rookie.brain.clickerGame.Entity.Game;
import rookie.brain.clickerGame.Entity.Level;
import rookie.brain.clickerGame.Entity.Player;
import rookie.brain.clickerGame.Exception.GameAlreadyExistsException;
import rookie.brain.clickerGame.Exception.LevelNotFoundException;
import rookie.brain.clickerGame.Exception.PlayerNotFoundException;
import rookie.brain.clickerGame.Repository.GameRepository;
import rookie.brain.clickerGame.Repository.LevelRepository;
import rookie.brain.clickerGame.Repository.PlayerRepository;
import rookie.brain.clickerGame.Service.GameService;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private LevelRepository levelRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    private Player player;
    private Level level;

    @BeforeEach
    public void setUp() {
        player = new Player(1L,"John",0,0);
        level = new Level(1L,"Beginner");
    }

    @Test
    public void saveGame_ValidInput_Success() {
        when(playerRepository.findByName("John")).thenReturn(player);
        when(levelRepository.findById(1L)).thenReturn(Optional.of(level));
        when(gameRepository.existsByPlayerNameAndLevelId("John", 1L)).thenReturn(false);

        Game savedGame = gameService.saveGame("John", 1L);

        assertNotNull(savedGame);
        assertEquals(player, savedGame.getPlayer());
        assertEquals(level, savedGame.getLevel());

        verify(playerRepository, times(1)).findByName("John");
        verify(levelRepository, times(1)).findById(1L);
        verify(gameRepository, times(1)).existsByPlayerNameAndLevelId("John", 1L);
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    public void saveGame_PlayerNotFound_ExceptionThrown() {
        when(playerRepository.findByName("John")).thenReturn(null);

        assertThrows(PlayerNotFoundException.class, () -> gameService.saveGame("John", 1L));

        verify(playerRepository, times(1)).findByName("John");
        verifyNoInteractions(levelRepository, gameRepository);
    }

    @Test
    public void saveGame_GameAlreadyExists_ExceptionThrown() {
        when(playerRepository.findByName("John")).thenReturn(player);
        when(levelRepository.findById(1L)).thenReturn(Optional.of(level));
        when(gameRepository.existsByPlayerNameAndLevelId("John", 1L)).thenReturn(true);

        assertThrows(GameAlreadyExistsException.class, () -> gameService.saveGame("John", 1L));

        verify(playerRepository, times(1)).findByName("John");
        verify(levelRepository, times(1)).findById(1L);
        verify(gameRepository, times(1)).existsByPlayerNameAndLevelId("John", 1L);
        verifyNoMoreInteractions(gameRepository);
    }

    @Test
    public void saveGame_LevelNotFound_ExceptionThrown() {
        when(playerRepository.findByName("John")).thenReturn(player);
        when(levelRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(LevelNotFoundException.class, () -> gameService.saveGame("John", 1L));

        verify(playerRepository, times(1)).findByName("John");
        verify(levelRepository, times(1)).findById(1L);
        verifyNoInteractions(gameRepository);
    }
}