package com.EcoMentor_backend.EcoMentor.Recommendation.useCases.mapper;

import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.CreateRecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import org.springframework.stereotype.Component;

@Component
public class RecommendationMapper {

    public RecommendationDTO toDTO(Recommendation recommendation) {
        return RecommendationDTO.builder()
                .recommendationId(recommendation.getRecommendationId())
                .name(recommendation.getName())
                .description(recommendation.getDescription())
                .recommendationType(recommendation.getRecommendationType())
                .upgradedICEE(recommendation.getUpgradedICEE())
                .upgradePercentage(recommendation.getUpgradePercentage())
                .upgradedAnualCost(recommendation.getUpgradedAnualCost())
                .totalPrice(recommendation.getTotalPrice())
                .build();
    }

    public Recommendation toEntity(CreateRecommendationDTO createRecommendationDTO) {
        return Recommendation.builder()
                .name(createRecommendationDTO.getName())
                .description(createRecommendationDTO.getDescription())
                .recommendationType(createRecommendationDTO.getRecommendationType())
                .upgradedICEE(createRecommendationDTO.getUpgradedICEE())
                .upgradePercentage(createRecommendationDTO.getUpgradePercentage())
                .upgradedAnualCost(createRecommendationDTO.getUpgradedAnualCost())
                .totalPrice(createRecommendationDTO.getTotalPrice())
                .build();
    }
}