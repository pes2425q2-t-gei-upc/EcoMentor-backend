package com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.infraestructure.controllers;

import com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.useCases.GetLonLatByCityNameUseCase;
import com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.useCases.dto.CoordinatesDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/geocodificator")
public class GeocodificatorGetController {
    private final GetLonLatByCityNameUseCase getLonLatByCityNameUseCase;

    public GeocodificatorGetController(GetLonLatByCityNameUseCase getLonLatByCityNameUseCase) {
        this.getLonLatByCityNameUseCase = getLonLatByCityNameUseCase;
    }

    @GetMapping
    public ResponseEntity<List<CoordinatesDTO>> getLonLatByCityNameUseCase(@RequestParam String cityName, @RequestParam int size) {
        List<CoordinatesDTO> c = getLonLatByCityNameUseCase.execute(cityName, size);
        if (c == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(c);
        }
    }

}
