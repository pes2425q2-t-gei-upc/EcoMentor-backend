package com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.mapper;

import com.EcoMentor_backend.EcoMentor.Achievement.useCases.mapper.AchievementMapper;
import com.EcoMentor_backend.EcoMentor.Achievements_User.entity.AchievementsUser;
import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.dto.AchievementsUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AchievementsUserMapper {
    private AchievementMapper achivementMapper;

    public AchievementsUserDTO toDTO(AchievementsUser achievementsUser) {
        return AchievementsUserDTO.builder()
                .progressId(achievementsUser.getProgressId())
                .progressStatus(achievementsUser.getProgressStatus())
                .achievementId(achievementsUser.getAchievementProgress().getAchievementId())
                .achievementName(achievementsUser.getAchievementProgress().getAchievementName())
                .achievement(achivementMapper.toDTO(achievementsUser.getAchievementProgress()))
                .build();
    }
}
