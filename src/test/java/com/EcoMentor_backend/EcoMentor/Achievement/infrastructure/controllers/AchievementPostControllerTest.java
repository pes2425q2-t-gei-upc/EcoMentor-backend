package com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Achievement.useCases.CreateAchievementUseCase;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.CreateAchievementDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AchievementPostControllerTest {

    @DisplayName("Creates achievement successfully when valid data is provided")
    @Test
    void createsAchievementSuccessfullyWhenValidDataIsProvided() {
        CreateAchievementUseCase createAchievementUseCase = mock(CreateAchievementUseCase.class);
        AchievementPostController controller = new AchievementPostController(createAchievementUseCase);

        CreateAchievementDTO mockDTO = new CreateAchievementDTO("Eco Warrior");
        when(createAchievementUseCase.execute(mockDTO)).thenReturn(1L);

        ResponseEntity<Long> response = controller.createAchievement(mockDTO);

        verify(createAchievementUseCase, times(1)).execute(mockDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody());
    }

}