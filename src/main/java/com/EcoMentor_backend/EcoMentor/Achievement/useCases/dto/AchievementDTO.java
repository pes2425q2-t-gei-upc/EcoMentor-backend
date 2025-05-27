package com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto;

import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.dto.AchievementsUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AchievementDTO {
    private String achievementName;
    private int maxProgress;
    private int actualProgress;
    private int type;
}
