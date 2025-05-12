package com.EcoMentor_backend.EcoMentor.Achievements_User.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.AssignAllAchievementsToUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/achievements-user")
public class AchievementsUserPostController {

    private final AssignAllAchievementsToUserUseCase assignAllAchievementsToUserUseCase;

    public AchievementsUserPostController(AssignAllAchievementsToUserUseCase assignAllAchievementsToUserUseCase) {
        this.assignAllAchievementsToUserUseCase = assignAllAchievementsToUserUseCase;
    }

    @PostMapping("/assign-all/{userId}")
    public ResponseEntity<String> assignAllAchievementsToUser(@PathVariable Long userId) {
        assignAllAchievementsToUserUseCase.execute(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Achievements assigned successfully to user " + userId);
    }
}
