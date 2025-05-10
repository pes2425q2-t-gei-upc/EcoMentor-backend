package com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Achievement.useCases.CreateAchievementUseCase;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.CreateAchievementDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/achievement")
public class AchievementPostController {
    private final CreateAchievementUseCase createAchievementUseCase;

    public AchievementPostController(CreateAchievementUseCase createAchievementUseCase) {
        this.createAchievementUseCase = createAchievementUseCase;
    }

    @PostMapping
    public ResponseEntity<Long> createAchievement(@RequestBody @Validated CreateAchievementDTO achievementDTO) {
        Long id = createAchievementUseCase.execute(achievementDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
}
