package com.EcoMentor_backend.EcoMentor.AchievementsUserTest.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Achievements_User.infrastructure.controllers.AchievementsUserPostController;
import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.AssignAllAchievementsToUserUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AchievementsUserPostControllerTest {

    private final AssignAllAchievementsToUserUseCase assignAllAchievementsToUserUseCase = Mockito.mock(AssignAllAchievementsToUserUseCase.class);
    private final AchievementsUserPostController controller = new AchievementsUserPostController(assignAllAchievementsToUserUseCase);

    @Test
    @DisplayName("Should assign all achievements to user successfully")
    void shouldAssignAllAchievementsToUserSuccessfully() {
        ResponseEntity<String> response = controller.assignAllAchievementsToUser(1L);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Achievements assigned successfully to user 1", response.getBody());
    }

}