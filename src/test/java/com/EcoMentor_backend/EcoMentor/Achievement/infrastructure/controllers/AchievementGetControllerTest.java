package com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Achievement.useCases.GetAchievementByIdUseCase;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.GetAllAchievementsUseCase;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.AchievementDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AchievementGetControllerTest {

    @DisplayName("Returns all achievements successfully when data is available")
    @Test
    void returnsAllAchievementsSuccessfullyWhenDataIsAvailable() {
        GetAllAchievementsUseCase getAllAchievementsUseCase = mock(GetAllAchievementsUseCase.class);
        GetAchievementByIdUseCase getAchievementByIdUseCase = mock(GetAchievementByIdUseCase.class);
        AchievementGetController controller = new AchievementGetController(getAchievementByIdUseCase, getAllAchievementsUseCase);

        List<AchievementDTO> mockAchievements = List.of(new AchievementDTO(1L, "Achievement 1"), new AchievementDTO(2L, "Achievement 2"));
        when(getAllAchievementsUseCase.execute()).thenReturn(mockAchievements);

        ResponseEntity<List<AchievementDTO>> response = controller.getAllAchievements();

        verify(getAllAchievementsUseCase, times(1)).execute();
        assertEquals(ResponseEntity.ok(mockAchievements), response);
    }

    @DisplayName("Returns empty list when no achievements are available")
    @Test
    void returnsEmptyListWhenNoAchievementsAreAvailable() {
        GetAllAchievementsUseCase getAllAchievementsUseCase = mock(GetAllAchievementsUseCase.class);
        GetAchievementByIdUseCase getAchievementByIdUseCase = mock(GetAchievementByIdUseCase.class);
        AchievementGetController controller = new AchievementGetController(getAchievementByIdUseCase, getAllAchievementsUseCase);

        when(getAllAchievementsUseCase.execute()).thenReturn(List.of());

        ResponseEntity<List<AchievementDTO>> response = controller.getAllAchievements();

        verify(getAllAchievementsUseCase, times(1)).execute();
        assertEquals(ResponseEntity.ok(List.of()), response);
    }

    @DisplayName("Returns achievement by ID successfully when valid ID is provided")
    @Test
    void returnsAchievementByIdSuccessfullyWhenValidIdIsProvided() {
        GetAllAchievementsUseCase getAllAchievementsUseCase = mock(GetAllAchievementsUseCase.class);
        GetAchievementByIdUseCase getAchievementByIdUseCase = mock(GetAchievementByIdUseCase.class);
        AchievementGetController controller = new AchievementGetController(getAchievementByIdUseCase, getAllAchievementsUseCase);

        AchievementDTO mockAchievement = new AchievementDTO(1L, "Achievement 1");
        when(getAchievementByIdUseCase.execute(1L)).thenReturn(mockAchievement);

        ResponseEntity<AchievementDTO> response = controller.getAchievement(1L);

        verify(getAchievementByIdUseCase, times(1)).execute(1L);
        assertEquals(ResponseEntity.ok(mockAchievement), response);
    }

    @DisplayName("Throws exception when invalid ID is provided for achievement retrieval")
    @Test
    void throwsExceptionWhenInvalidIdIsProvidedForAchievementRetrieval() {
        GetAllAchievementsUseCase getAllAchievementsUseCase = mock(GetAllAchievementsUseCase.class);
        GetAchievementByIdUseCase getAchievementByIdUseCase = mock(GetAchievementByIdUseCase.class);
        AchievementGetController controller = new AchievementGetController(getAchievementByIdUseCase, getAllAchievementsUseCase);

        when(getAchievementByIdUseCase.execute(999L)).thenThrow(new IllegalArgumentException("Achievement not found"));

        try {
            controller.getAchievement(999L);
        } catch (IllegalArgumentException e) {
            assertEquals("Achievement not found", e.getMessage());
        }

        verify(getAchievementByIdUseCase, times(1)).execute(999L);
    }
}