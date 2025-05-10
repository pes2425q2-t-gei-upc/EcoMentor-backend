package com.EcoMentor_backend.EcoMentor.Achievement.useCases.mapper;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.AchievementDTO;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.CreateAchievementDTO;
import org.springframework.stereotype.Component;

@Component
public class AchievementMapper {

    public AchievementDTO toDTO(Achievement achievement) {
        return AchievementDTO.builder()
                .achievementId(achievement.getAchievementId())
                .achievementName(achievement.getAchievementName())
                .build();
    }

    public Achievement toEntity(CreateAchievementDTO createAchievementDTO) {
        return Achievement.builder()
                .achievementName(createAchievementDTO.getAchievementName())
                .build();
    }
}
