package com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.infraestructure.controllers;

import com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.useCases.GetLonLatByCityNameUseCase;
import com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.useCases.dto.CoordinatesDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/geocodificator")
public class GeocodificatorGetController {
    private final GetLonLatByCityNameUseCase getLonLatByCityNameUseCase;

    public GeocodificatorGetController(GetLonLatByCityNameUseCase getLonLatByCityNameUseCase) {
        this.getLonLatByCityNameUseCase = getLonLatByCityNameUseCase;
    }

    @GetMapping("/{cityName}")
    public ResponseEntity<CoordinatesDTO> getLonLatByCityNameUseCase(@PathVariable String cityName) {
        CoordinatesDTO c = getLonLatByCityNameUseCase.execute(cityName);
        if (c == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(c);
        }
    }

}
