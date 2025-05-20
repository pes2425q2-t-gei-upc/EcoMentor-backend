package com.EcoMentor_backend.EcoMentor.Achievements_User.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.GetAchievementsByUserUseCase;
import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.dto.AchievementsUserDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/achievements-user")
public class AchievementsUserGetController {

    private final GetAchievementsByUserUseCase getAchievementsByUserUseCase;

    public AchievementsUserGetController(GetAchievementsByUserUseCase getAchievementsByUserUseCase) {
        this.getAchievementsByUserUseCase = getAchievementsByUserUseCase;
    }

    @GetMapping("/{userId}/{achievementId}")
    public ResponseEntity<AchievementsUserDTO> getUserAchievements(@PathVariable Long userId,
                                                                         @PathVariable Long achievementId) {

        AchievementsUserDTO achievements = getAchievementsByUserUseCase.execute(userId, achievementId);
        return ResponseEntity.ok(achievements);
    }
}
