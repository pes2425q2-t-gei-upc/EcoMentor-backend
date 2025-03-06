package com.EcoMentor_backend.EcoMentor.template.useCases.dto;

import com.EcoMentor_backend.EcoMentor.template.entity.Planet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class planetDTO {
    /*

    WARNING: ALL THESE FIELD SHOULD HAVE SOME TYPE OF VALIDATION (MAYBE USING JAKARTA.VALIDATION)

     */
    private String name;

    private Double diameterKm;

    private LocalDate discoveryDate;

    private Planet.PlanetType planetType;
}