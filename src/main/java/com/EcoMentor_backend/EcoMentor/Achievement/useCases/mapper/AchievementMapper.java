package com.EcoMentor_backend.EcoMentor.Achievement.useCases.mapper;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.AchievementDTO;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.CreateAchievementDTO;
import org.springframework.stereotype.Component;

@Component
public class AchievementMapper {

    public AchievementDTO toDTO(Achievement achievement) {
        return AchievementDTO.builder()
                .achievementName(achievement.getAchievementName())
                .maxProgress(achievement.getMaxProgress())
                .actualProgress(0) // Assuming actual progress is 0 when mapping to DTO
                .build();
    }

    public Achievement toEntity(CreateAchievementDTO createAchievementDTO) {
        return Achievement.builder()
                .achievementName(createAchievementDTO.getAchievementName())
                .maxProgress(createAchievementDTO.getMaxProgress())
                .build();
    }
}
