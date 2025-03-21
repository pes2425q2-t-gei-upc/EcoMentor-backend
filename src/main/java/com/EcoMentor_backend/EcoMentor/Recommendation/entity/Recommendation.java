package com.EcoMentor_backend.EcoMentor.Recommendation.entity;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "recommendation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recommendation {
    //TODO change to UUID?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long recommendationId;

    @ManyToMany(mappedBy = "recommendations")
    private List<Certificate> certificates;

    @NotNull
    private String name;

    @NotNull
    private boolean canUpgradeIsolation;

    @NotNull
    private float upgradedIsolation;

    @NotNull
    private boolean canUpgradeWindows;

    @NotNull
    private float upgradedWindows;

    @NotNull
    private boolean canUpgradeSolarPanels;

    @NotNull
    private float upgradedSolarPanels;

    @NotNull
    private boolean canUpgradeBombHeat;

    @NotNull
    private float upgradedBombHeat;

    @NotNull
    private boolean canUpgradeHeat;

    @NotNull
    private float upgradedHeat;

    @NotNull
    private float io;

    @NotNull
    private float ir;

    @NotNull
    private float iss;

    @NotNull
    private float r1;

    @NotNull
    private float r0;

    @NotNull
    private float upgradedICEE;

    @NotNull
    private float totalPrice;
}