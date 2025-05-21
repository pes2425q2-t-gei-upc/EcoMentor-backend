package com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AchievementsUserDTO {
    private int progressStatus;
    private Long userId;
}

