package com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Achievement.useCases.GetAchievementByIdUseCase;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.GetAllAchievementsUseCase;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.AchievementDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
@RequestMapping("/api/achievement")
public class AchievementGetController {
    private final GetAchievementByIdUseCase getAchievementByIdUseCase;
    private final GetAllAchievementsUseCase getAllAchievementsUseCase;

    public AchievementGetController(GetAchievementByIdUseCase getAchievementByIdUseCase,
                                    GetAllAchievementsUseCase getAllAchievementsUseCase) {
        this.getAchievementByIdUseCase = getAchievementByIdUseCase;
        this.getAllAchievementsUseCase = getAllAchievementsUseCase;
    }

    @GetMapping
    public ResponseEntity<List<AchievementDTO>> getAllAchievements() {
        List<AchievementDTO> achievements = getAllAchievementsUseCase.execute();
        return ResponseEntity.ok(achievements);
    }

    @GetMapping("/{achievementId}")
    public ResponseEntity<AchievementDTO> getAchievement(@PathVariable Long achievementId) {
        AchievementDTO achievement = getAchievementByIdUseCase.execute(achievementId);
        return ResponseEntity.ok(achievement);
    }
}
