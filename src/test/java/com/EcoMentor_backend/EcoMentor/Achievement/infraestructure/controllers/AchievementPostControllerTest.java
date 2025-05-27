package com.EcoMentor_backend.EcoMentor.Achievement.infraestructure.controllers;

import com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.controllers.AchievementPostController;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.CreateAchievementUseCase;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.CreateAchievementDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AchievementPostControllerTest {

    private CreateAchievementUseCase createAchievementUseCase;
    private AchievementPostController controller;

    @BeforeEach
    public void setUp() {
        createAchievementUseCase = mock(CreateAchievementUseCase.class);
        controller = new AchievementPostController(createAchievementUseCase);
    }

    @Test
    public void testCreateAchievement_ReturnsCreatedId() {
        // Arrange
        CreateAchievementDTO dto = new CreateAchievementDTO();
        dto.setAchievementName("1");
        dto.setMaxProgress(1);

        Long expectedId = 1L;
        when(createAchievementUseCase.execute(dto)).thenReturn(expectedId);

        // Act
        ResponseEntity<Long> response = controller.createAchievement(dto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedId, response.getBody());
        verify(createAchievementUseCase, times(1)).execute(dto);
    }


}
