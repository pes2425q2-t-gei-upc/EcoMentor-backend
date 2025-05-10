package com.EcoMentor_backend.EcoMentor.Achievement.useCases;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.repositories.AchievementRepository;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.CreateAchievementDTO;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.mapper.AchievementMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateAchievementUseCaseTest {

    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private AchievementMapper achievementMapper;

    @InjectMocks
    private CreateAchievementUseCase createAchievementUseCase;

    public CreateAchievementUseCaseTest() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Successfully creates and saves an achievement")
    @Test
    void successfullyCreatesAndSavesAchievement() {
        CreateAchievementDTO dto = new CreateAchievementDTO("Eco Warrior");
        Achievement achievement = Achievement.builder().achievementId(1L).achievementName("Eco Warrior").build();

        when(achievementMapper.toEntity(dto)).thenReturn(achievement);
        when(achievementRepository.save(any(Achievement.class))).thenReturn(achievement);

        long result = createAchievementUseCase.execute(dto);

        assertEquals(1L, result);
        verify(achievementMapper, times(1)).toEntity(dto);
        verify(achievementRepository, times(1)).save(achievement);
    }

}