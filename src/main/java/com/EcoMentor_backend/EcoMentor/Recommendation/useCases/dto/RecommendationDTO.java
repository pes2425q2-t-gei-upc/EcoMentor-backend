package com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendationDTO {
    private Long recommendationId;
    private String name;
    private String description;
    private String recommendationType;
    private String upgradedICEE;
    private float upgradePercentage;
    private float upgradedAnualCost;
    private float totalPrice;
}