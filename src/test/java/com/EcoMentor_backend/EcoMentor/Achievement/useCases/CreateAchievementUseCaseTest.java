package com.EcoMentor_backend.EcoMentor.Achievement.useCases;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.repositories.AchievementRepository;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.CreateAchievementUseCase;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.CreateAchievementDTO;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.mapper.AchievementMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CreateAchievementUseCaseTest {

    private AchievementRepository achievementRepository;
    private AchievementMapper achievementMapper;
    private CreateAchievementUseCase createAchievementUseCase;

    @BeforeEach
    public void setUp() {
        achievementRepository = mock(AchievementRepository.class);
        achievementMapper = mock(AchievementMapper.class);
        createAchievementUseCase = new CreateAchievementUseCase(achievementRepository, achievementMapper);
    }

    @Test
    public void testExecute_ReturnsAchievementId() {
        CreateAchievementDTO dto = new CreateAchievementDTO();
        dto.setAchievementName("1");
        dto.setMaxProgress(1);

        Achievement achievement = new Achievement();
        achievement.setAchievementId(1L);
        achievement.setAchievementName("1");
        achievement.setMaxProgress(0);

        when(achievementMapper.toEntity(dto)).thenReturn(achievement);
        when(achievementRepository.save(achievement)).thenReturn(achievement);

        // Act
        long resultId = createAchievementUseCase.execute(dto);

        // Assert
        assertEquals(1L, resultId);
        verify(achievementMapper, times(1)).toEntity(dto);
        verify(achievementRepository, times(1)).save(achievement);
    }
}
