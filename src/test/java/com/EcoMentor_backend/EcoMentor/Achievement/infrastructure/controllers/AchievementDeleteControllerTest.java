package com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Achievement.useCases.DeleteAchievementUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AchievementDeleteControllerTest {

    @DisplayName("Deletes achievement successfully when valid ID is provided")
    @Test
    void deletesAchievementSuccessfullyWhenValidIdIsProvided() {
        DeleteAchievementUseCase deleteAchievementUseCase = mock(DeleteAchievementUseCase.class);
        AchievementDeleteController controller = new AchievementDeleteController(deleteAchievementUseCase);

        ResponseEntity<Void> response = controller.deleteAchievement(1L);

        verify(deleteAchievementUseCase, times(1)).execute(1L);
        assertEquals(ResponseEntity.noContent().build(), response);
    }

    @DisplayName("Does not throw exception when delete use case is called with non-existent ID")
    @Test
    void doesNotThrowExceptionWhenDeleteUseCaseIsCalledWithNonExistentId() {
        DeleteAchievementUseCase deleteAchievementUseCase = mock(DeleteAchievementUseCase.class);
        AchievementDeleteController controller = new AchievementDeleteController(deleteAchievementUseCase);

        doNothing().when(deleteAchievementUseCase).execute(999L);

        ResponseEntity<Void> response = controller.deleteAchievement(999L);

        verify(deleteAchievementUseCase, times(1)).execute(999L);
        assertEquals(ResponseEntity.noContent().build(), response);
    }
}