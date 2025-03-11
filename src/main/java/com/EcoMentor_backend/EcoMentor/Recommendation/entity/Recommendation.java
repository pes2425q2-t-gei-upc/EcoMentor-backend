package com.EcoMentor_backend.EcoMentor.Recommendation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Recommendation {
    //TODO change to UUID?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long recommendationId;

    @NotNull
    private String name;

    @NotNull
    private String description;

    //TODO complete, change...
}
