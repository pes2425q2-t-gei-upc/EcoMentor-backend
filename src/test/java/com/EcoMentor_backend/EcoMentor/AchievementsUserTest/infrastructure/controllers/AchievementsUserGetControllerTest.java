package com.EcoMentor_backend.EcoMentor.AchievementsUserTest.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Achievements_User.infrastructure.controllers.AchievementsUserGetController;
import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.GetAchievementsByUserUseCase;
import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.dto.AchievementsUserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AchievementsUserGetControllerTest {

    private final GetAchievementsByUserUseCase getAchievementsByUserUseCase = Mockito.mock(GetAchievementsByUserUseCase.class);
    private final AchievementsUserGetController controller = new AchievementsUserGetController(getAchievementsByUserUseCase);

    @Test
    @DisplayName("Should return achievements for a valid user ID")
    void shouldReturnAchievementsForValidUserId() {
        AchievementsUserDTO achievement = AchievementsUserDTO.builder()
                .progressId(1L)
                .progressStatus("Completed")
                .achievementId(2L)
                .achievementName("Achievement Name")
                .build();

        when(getAchievementsByUserUseCase.execute(1L)).thenReturn(List.of(achievement));

        ResponseEntity<List<AchievementsUserDTO>> response = controller.getUserAchievements(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Achievement Name", response.getBody().get(0).getAchievementName());
    }

    @Test
    @DisplayName("Should return empty list when user has no achievements")
    void shouldReturnEmptyListWhenUserHasNoAchievements() {
        when(getAchievementsByUserUseCase.execute(1L)).thenReturn(Collections.emptyList());

        ResponseEntity<List<AchievementsUserDTO>> response = controller.getUserAchievements(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().size());
    }

    @Test
    @DisplayName("Should handle null user ID gracefully")
    void shouldHandleNullUserIdGracefully() {
        when(getAchievementsByUserUseCase.execute(null)).thenReturn(Collections.emptyList());

        ResponseEntity<List<AchievementsUserDTO>> response = controller.getUserAchievements(null);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().size());
    }
}