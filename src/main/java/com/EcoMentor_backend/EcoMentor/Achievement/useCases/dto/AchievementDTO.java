package com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AchievementDTO {
    private Long achievementId;
    private String achievementName;
}
