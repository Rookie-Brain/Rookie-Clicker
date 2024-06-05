package Rookie.Brain.clickerGame.Service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rookie.brain.clickerGame.Entity.Ranking;
import rookie.brain.clickerGame.Exception.RankingNotFoundException;
import rookie.brain.clickerGame.Repository.RankingRepository;
import rookie.brain.clickerGame.Service.RankingService;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RankingServiceTest {

    @Mock
    private RankingRepository rankingRepository;

    @InjectMocks
    private RankingService rankingService;

    private Ranking ranking;

    @BeforeEach
    public void setUp() {
        ranking = new Ranking(1L, "John", 1000);
    }

    @Test
    public void saveRanking_ValidInput_Success() {
        when(rankingRepository.save(ranking)).thenReturn(ranking);

        Ranking savedRanking = rankingService.saveRanking(ranking);

        assertNotNull(savedRanking);
        assertEquals(ranking, savedRanking);

        verify(rankingRepository, times(1)).save(ranking);
    }

    @Test
    public void getAllRankings_ReturnsListOfRankings() {
        List<Ranking> rankings = Arrays.asList(
                new Ranking(1L, "John", 1000),
                new Ranking(2L, "Jane", 1500)
        );

        when(rankingRepository.findAll()).thenReturn(rankings);

        List<Ranking> result = rankingService.getAllRankings();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getPlayerName());
        assertEquals("Jane", result.get(1).getPlayerName());

        verify(rankingRepository, times(1)).findAll();
    }

    @Test
    public void getRankingById_ValidId_Success() {
        when(rankingRepository.findById(1L)).thenReturn(Optional.of(ranking));

        Ranking foundRanking = rankingService.getRankingById(1L);

        assertNotNull(foundRanking);
        assertEquals(ranking, foundRanking);

        verify(rankingRepository, times(1)).findById(1L);
    }

    @Test
    public void getRankingById_InvalidId_ExceptionThrown() {
        when(rankingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RankingNotFoundException.class, () -> rankingService.getRankingById(1L));

        verify(rankingRepository, times(1)).findById(1L);
    }

    @Test
    public void deleteRanking_ValidId_Success() {
        when(rankingRepository.existsById(1L)).thenReturn(true);
        doNothing().when(rankingRepository).deleteById(1L);

        rankingService.deleteRanking(1L);

        verify(rankingRepository, times(1)).existsById(1L);
        verify(rankingRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteRanking_InvalidId_ExceptionThrown() {
        when(rankingRepository.existsById(1L)).thenReturn(false);

        assertThrows(RankingNotFoundException.class, () -> rankingService.deleteRanking(1L));

        verify(rankingRepository, times(1)).existsById(1L);
        verify(rankingRepository, times(0)).deleteById(1L);
    }
}
