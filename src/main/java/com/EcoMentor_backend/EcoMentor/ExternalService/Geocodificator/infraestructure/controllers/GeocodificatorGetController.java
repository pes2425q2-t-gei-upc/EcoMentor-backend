package com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.infraestructure.controllers;

import com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.useCases.GetLonLatByCityNameUseCase;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public void getLonLatByCityNameUseCase() {
        //getLonLatByCityNameUseCase.execute();


    }

}
