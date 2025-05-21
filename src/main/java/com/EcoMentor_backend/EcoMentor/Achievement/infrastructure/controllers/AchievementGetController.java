package com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Achievement.useCases.GetAllAchievementsUseCase;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.AchievementDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetUserIdByToken;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/api/achievement")
public class AchievementGetController {
    private final GetAllAchievementsUseCase getAllAchievementsUseCase;
    private GetUserIdByToken getUserIdByToken;

    @GetMapping
    public ResponseEntity<List<AchievementDTO>> getAllAchievements(HttpServletRequest request) {
        Long userId = getUserIdFromToken(request);
        List<AchievementDTO> achievements = getAllAchievementsUseCase.execute(userId);
        return ResponseEntity.ok(achievements);
    }

    private Long getUserIdFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        return getUserIdByToken.execute(token);
    }

}
