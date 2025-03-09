package com.EcoMentor_backend.EcoMentor.template.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.template.useCases.CreatePlanetUseCase;
import com.EcoMentor_backend.EcoMentor.template.useCases.dto.planetDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/planets")
public class PlanetPostController {
    private final CreatePlanetUseCase createPlanetUseCase;

    public PlanetPostController(CreatePlanetUseCase createPlanetUseCase) {
        this.createPlanetUseCase = createPlanetUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> createPlanet(@RequestBody planetDTO planet) {
        createPlanetUseCase.execute(planet);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
