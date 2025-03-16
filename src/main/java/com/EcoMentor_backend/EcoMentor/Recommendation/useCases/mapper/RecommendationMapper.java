package com.EcoMentor_backend.EcoMentor.Recommendation.useCases.mapper;

import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.CreateRecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import org.springframework.stereotype.Component;

@Component
public class RecommendationMapper {

    // Entity to DTO conversion
    public RecommendationDTO toDTO(Recommendation recommendation) {
        if (recommendation == null) {
            return null;
        }
        return RecommendationDTO.builder()
                .recommendationId(recommendation.getRecommendationId())
                .certificates(recommendation.getCertificates())
                .name(recommendation.getName())
                .canUpgradeIsolation(recommendation.isCanUpgradeIsolation())
                .upgradedIsolation(recommendation.getUpgradedIsolation())
                .canUpgradeWindows(recommendation.isCanUpgradeWindows())
                .upgradedWindows(recommendation.getUpgradedWindows())
                .canUpgradeSolarPanels(recommendation.isCanUpgradeSolarPanels())
                .upgradedSolarPanels(recommendation.getUpgradedSolarPanels())
                .canUpgradeBombHeat(recommendation.isCanUpgradeBombHeat())
                .upgradedBombHeat(recommendation.getUpgradedBombHeat())
                .canUpgradeHeat(recommendation.isCanUpgradeHeat())
                .upgradedHeat(recommendation.getUpgradedHeat())
                .io(recommendation.getIo())
                .ir(recommendation.getIr())
                .iss(recommendation.getIss())
                .r(recommendation.getR())
                .r0(recommendation.getR0())
                .upgradedICEE(recommendation.getUpgradedICEE())
                .totalPrice(recommendation.getTotalPrice())
                .build();
    }

    // CreateDTO to Entity conversion
    public Recommendation toEntity(CreateRecommendationDTO dto) {
        if (dto == null) {
            return null;
        }
        return Recommendation.builder()
                .certificates(dto.getCertificates())
                .name(dto.getName())
                .canUpgradeIsolation(dto.isCanUpgradeIsolation())
                .UpgradedIsolation(dto.getUpgradedIsolation())
                .canUpgradeWindows(dto.isCanUpgradeWindows())
                .UpgradedWindows(dto.getUpgradedWindows())
                .canUpgradeSolarPanels(dto.isCanUpgradeSolarPanels())
                .UpgradedSolarPanels(dto.getUpgradedSolarPanels())
                .canUpgradeBombHeat(dto.isCanUpgradeBombHeat())
                .UpgradedBombHeat(dto.getUpgradedBombHeat())
                .canUpgradeHeat(dto.isCanUpgradeHeat())
                .UpgradedHeat(dto.getUpgradedHeat())
                .Io(dto.getIo())
                .Ir(dto.getIr())
                .Iss(dto.getIss())
                .R(dto.getR())
                .R0(dto.getR0())
                .UpgradedICEE(dto.getUpgradedICEE())
                .totalPrice(dto.getTotalPrice())
                .build();
    }
}