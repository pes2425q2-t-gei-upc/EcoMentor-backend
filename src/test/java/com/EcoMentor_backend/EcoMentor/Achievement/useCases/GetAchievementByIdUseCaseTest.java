package com.EcoMentor_backend.EcoMentor.Achievement.useCases;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.repositories.AchievementRepository;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.AchievementDTO;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.mapper.AchievementMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GetAchievementByIdUseCaseTest {

    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private AchievementMapper achievementMapper;

    @InjectMocks
    private GetAchievementByIdUseCase getAchievementByIdUseCase;

    public GetAchievementByIdUseCaseTest() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Successfully retrieves achievement by ID")
    @Test
    void successfullyRetrievesAchievementById() {
        Long id = 1L;
        Achievement achievement = Achievement.builder().achievementId(id).achievementName("Eco Warrior").build();
        AchievementDTO dto = new AchievementDTO(id, "Eco Warrior");

        when(achievementRepository.findByAchievementId(id)).thenReturn(achievement);
        when(achievementMapper.toDTO(achievement)).thenReturn(dto);

        AchievementDTO result = getAchievementByIdUseCase.execute(id);

        assertEquals(dto, result);
        verify(achievementRepository, times(1)).findByAchievementId(id);
        verify(achievementMapper, times(1)).toDTO(achievement);
    }

    @DisplayName("Throws exception when achievement is not found")
    @Test
    void throwsExceptionWhenAchievementNotFound() {
        Long id = 1L;

        when(achievementRepository.findByAchievementId(id)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> getAchievementByIdUseCase.execute(id));

        verify(achievementRepository, times(1)).findByAchievementId(id);
        verifyNoInteractions(achievementMapper);
    }
}
