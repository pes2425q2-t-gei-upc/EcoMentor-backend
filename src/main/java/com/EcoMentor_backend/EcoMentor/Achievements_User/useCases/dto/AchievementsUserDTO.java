package com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.dto;

import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.AchievementDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AchievementsUserDTO {
    private Long progressId;
    private int progressStatus;
    private Long achievementId;
    private AchievementDTO achievement;
    private String achievementName;
}

