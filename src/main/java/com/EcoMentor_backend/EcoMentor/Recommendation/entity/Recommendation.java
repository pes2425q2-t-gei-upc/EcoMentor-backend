package com.EcoMentor_backend.EcoMentor.Recommendation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recommendation {
    //TODO change to UUID?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long recommendationId;

    @NotNull
    private String name;

    @NotNull
    private boolean canUpgradeIsolation;

    @NotNull
    private float UpgradedIsolation;

    @NotNull
    private boolean canUpgradeWindows;

    @NotNull
    private float UpgradedWindows;

    @NotNull
    private boolean canUpgradeSolarPanels;

    @NotNull
    private float UpgradedSolarPanels;

    @NotNull
    private boolean canUpgradeBombHeat;

    @NotNull
    private float UpgradedBombHeat;

    @NotNull
    private boolean canUpgradeHeat;

    @NotNull
    private float UpgradedHeat;

    @NotNull
    private float Io;

    @NotNull
    private float Ir;

    @NotNull
    private float Is;

    @NotNull
    private float R;

    @NotNull
    private float R0;

    @NotNull
    private float UpgradedICEE;

    @NotNull
    private float totalPrice;
}
