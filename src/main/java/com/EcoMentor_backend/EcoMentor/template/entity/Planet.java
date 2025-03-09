package com.EcoMentor_backend.EcoMentor.template.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "planet")
@Data
@AllArgsConstructor //creates automatically a constructor with all arguments
@NoArgsConstructor //creates automatically a constructor without any arguments
@Builder
public class Planet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;
    
    @Column(name = "diameter_km")
    private Double diameterKm;
    
    @Column(name = "discovery_date")
    private LocalDate discoveryDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "planet_type", nullable = false)
    private PlanetType planetType;
    
    public enum PlanetType {        //usage of enums
        BIG,
        SMALL,
        PLUTO
    }
}