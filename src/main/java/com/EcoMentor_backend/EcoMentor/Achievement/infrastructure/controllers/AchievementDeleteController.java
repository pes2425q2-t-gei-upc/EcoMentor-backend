package com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Achievement.useCases.DeleteAchievementUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/achievement")
public class AchievementDeleteController {
    private final DeleteAchievementUseCase deleteAchievementUseCase;

    public AchievementDeleteController(DeleteAchievementUseCase deleteAchievementUseCase) {
        this.deleteAchievementUseCase = deleteAchievementUseCase;
    }

    @DeleteMapping("/{achievementId}")
    public ResponseEntity<Void> deleteAchievement(@PathVariable Long achievementId) {
        deleteAchievementUseCase.execute(achievementId);
        return ResponseEntity.noContent().build();
    }
}
