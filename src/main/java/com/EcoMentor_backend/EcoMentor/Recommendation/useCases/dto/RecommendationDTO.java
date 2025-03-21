package com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import java.util.List;
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
    private List<Certificate> certificates;
    private String name;
    private boolean canUpgradeIsolation;
    private float upgradedIsolation;
    private boolean canUpgradeWindows;
    private float upgradedWindows;
    private boolean canUpgradeSolarPanels;
    private float upgradedSolarPanels;
    private boolean canUpgradeBombHeat;
    private float upgradedBombHeat;
    private boolean canUpgradeHeat;
    private float upgradedHeat;
    private float io;
    private float ir;
    private float iss;
    private float r1;
    private float r0;
    private float upgradedICEE;
    private float totalPrice;
}