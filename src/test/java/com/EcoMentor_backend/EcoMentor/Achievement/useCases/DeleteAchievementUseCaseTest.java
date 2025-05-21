package com.EcoMentor_backend.EcoMentor.Achievement.useCases;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.repositories.AchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class DeleteAchievementUseCaseTest {

    private AchievementRepository achievementRepository;
    private DeleteAchievementUseCase deleteAchievementUseCase;

    @BeforeEach
    public void setUp() {
        achievementRepository = mock(AchievementRepository.class);
        deleteAchievementUseCase = new DeleteAchievementUseCase(achievementRepository);
    }

    @Test
    public void testExecute_DeletesAchievement_WhenFound() {
        // Arrange
        Long achievementId = 1L;
        Achievement achievement = new Achievement();
        achievement.setAchievementId(achievementId);

        when(achievementRepository.findById(achievementId)).thenReturn(Optional.of(achievement));

        // Act
        deleteAchievementUseCase.execute(achievementId);

        // Assert
        verify(achievementRepository, times(1)).findById(achievementId);
        verify(achievementRepository, times(1)).delete(achievement);
    }

    @Test
    public void testExecute_ThrowsException_WhenNotFound() {
        // Arrange
        Long achievementId = 1L;
        when(achievementRepository.findById(achievementId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            deleteAchievementUseCase.execute(achievementId);
        });

        assertEquals("Achievement not found", exception.getMessage());
        verify(achievementRepository, times(1)).findById(achievementId);
        verify(achievementRepository, never()).delete(any());
    }
}
