package Rookie.Brain.clickerGame.Service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rookie.brain.clickerGame.Entity.Level;
import rookie.brain.clickerGame.Exception.LevelAlreadyExistsException;
import rookie.brain.clickerGame.Exception.LevelNotFoundException;
import rookie.brain.clickerGame.Repository.LevelRepository;
import rookie.brain.clickerGame.Service.LevelService;
import rookie.brain.clickerGame.Utils.ExceptionConstants;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LevelServiceTest {

    @Mock
    private LevelRepository levelRepository;

    @InjectMocks
    private LevelService levelService;

    private Level level;

    @BeforeEach
    public void setUp() {
        level = new Level(1L, "Beginner", 50, 30);
    }

    @Test
    public void saveLevel_ValidInput_Success() {
        when(levelRepository.findByName("Beginner")).thenReturn(null);
        when(levelRepository.save(level)).thenReturn(level);

        Level savedLevel = levelService.saveLevel(level);

        assertNotNull(savedLevel);
        assertEquals(level, savedLevel);

        verify(levelRepository, times(1)).findByName("Beginner");
        verify(levelRepository, times(1)).save(level);
    }

    @Test
    public void saveLevel_LevelAlreadyExists_ExceptionThrown() {
        when(levelRepository.findByName("Beginner")).thenReturn(level);

        assertThrows(LevelAlreadyExistsException.class, () -> levelService.saveLevel(level));

        verify(levelRepository, times(1)).findByName("Beginner");
        verify(levelRepository, times(0)).save(level);
    }

    @Test
    public void getAllLevels_ReturnsListOfLevels() {
        List<Level> levels = Arrays.asList(
                new Level(1L, "Beginner", 50, 30),
                new Level(2L, "Intermediate", 100, 60)
        );

        when(levelRepository.findAll()).thenReturn(levels);

        List<Level> result = levelService.getAllLevels();

        assertEquals(2, result.size());
        assertEquals("Beginner", result.get(0).getName());
        assertEquals("Intermediate", result.get(1).getName());

        verify(levelRepository, times(1)).findAll();
    }

    @Test
    public void getLevelById_ValidId_Success() {
        when(levelRepository.findById(1L)).thenReturn(Optional.of(level));

        Level foundLevel = levelService.getLevelById(1L);

        assertNotNull(foundLevel);
        assertEquals(level, foundLevel);

        verify(levelRepository, times(1)).findById(1L);
    }

    @Test
    public void getLevelById_InvalidId_ExceptionThrown() {
        when(levelRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(LevelNotFoundException.class, () -> levelService.getLevelById(1L));

        verify(levelRepository, times(1)).findById(1L);
    }

    @Test
    public void deleteLevel_ValidId_Success() {
        when(levelRepository.existsById(1L)).thenReturn(true);

        levelService.deleteLevel(1L);

        verify(levelRepository, times(1)).existsById(1L);
        verify(levelRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteLevel_InvalidId_ExceptionThrown() {
        when(levelRepository.existsById(1L)).thenReturn(false);

        assertThrows(LevelNotFoundException.class, () -> levelService.deleteLevel(1L));

        verify(levelRepository, times(1)).existsById(1L);
        verify(levelRepository, times(0)).deleteById(1L);
    }
}
