package com.EcoMentor_backend.EcoMentor.Achievement.useCases;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.repositories.AchievementRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeleteAchievementUseCaseTest {

    @Mock
    private AchievementRepository achievementRepository;

    @InjectMocks
    private DeleteAchievementUseCase deleteAchievementUseCase;

    public DeleteAchievementUseCaseTest() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Successfully deletes an existing achievement")
    @Test
    void successfullyDeletesExistingAchievement() {
        Long id = 1L;
        Achievement achievement = Achievement.builder().achievementId(id).build();

        when(achievementRepository.findById(id)).thenReturn(Optional.of(achievement));

        deleteAchievementUseCase.execute(id);

        verify(achievementRepository, times(1)).findById(id);
        verify(achievementRepository, times(1)).delete(achievement);
    }

    @DisplayName("Throws exception when achievement is not found")
    @Test
    void throwsExceptionWhenAchievementNotFound() {
        Long id = 1L;

        when(achievementRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> deleteAchievementUseCase.execute(id));

        verify(achievementRepository, times(1)).findById(id);
        verify(achievementRepository, never()).delete(any(Achievement.class));
    }
}