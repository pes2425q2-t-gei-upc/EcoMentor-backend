package com.EcoMentor_backend.EcoMentor.Achievement.infraestructure.controllers;

import com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.controllers.AchievementDeleteController;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.DeleteAchievementUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AchievementDeleteControllerTest {

    private DeleteAchievementUseCase deleteAchievementUseCase;
    private AchievementDeleteController controller;

    @BeforeEach
    public void setUp() {
        deleteAchievementUseCase = mock(DeleteAchievementUseCase.class);
        controller = new AchievementDeleteController(deleteAchievementUseCase);
    }

    @Test
    public void testDeleteAchievement_ReturnsNoContent() {
        // Arrange
        Long achievementId = 10L;

        // Act
        ResponseEntity<Void> response = controller.deleteAchievement(achievementId);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(deleteAchievementUseCase, times(1)).execute(achievementId);
    }

    @Test
    public void testDeleteAchievement_ThrowsException() {
        // Arrange
        Long achievementId = 99L;
        doThrow(new RuntimeException("Achievement not found"))
                .when(deleteAchievementUseCase).execute(achievementId);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            controller.deleteAchievement(achievementId);
        });

        assertEquals("Achievement not found", exception.getMessage());
        verify(deleteAchievementUseCase, times(1)).execute(achievementId);
    }
}
