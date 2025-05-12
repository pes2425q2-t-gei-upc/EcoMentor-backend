package com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.mapper;

import com.EcoMentor_backend.EcoMentor.Achievements_User.entity.AchievementsUser;
import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.dto.AchievementsUserDTO;
import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.dto.CreateAchievementsUserDTO;
import org.springframework.stereotype.Component;

@Component
public class AchievementsUserMapper {

    public AchievementsUserDTO toDTO(AchievementsUser achievementsUser) {
        return AchievementsUserDTO.builder()
                .progressId(achievementsUser.getProgressId())
                .progressStatus(achievementsUser.getProgressStatus())
                .achievementId(achievementsUser.getAchievementProgress().getAchievementId())
                .achievementName(achievementsUser.getAchievementProgress().getAchievementName())
                .build();
    }

    public AchievementsUser toEntity(CreateAchievementsUserDTO createAchievementsUserDTO) {
        return AchievementsUser.builder()
                .progressStatus(createAchievementsUserDTO.getProgressStatus())
                .build();
    }
}
