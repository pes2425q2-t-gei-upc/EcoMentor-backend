package com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAchievementDTO {
    private String achievementName;
    private int maxProgress;
    private int type;
}
