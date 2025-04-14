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
    private String description;

    @NotNull
    private String recommendationType; // e.g., "Insulation", "Windows", "Solar", etc.

    @NotNull
    private float upgradedICEE;

    @NotNull
    private float upgradePercentage;

    @NotNull
    private float upgradedAnualCost;

    @NotNull
    private float totalPrice;

}