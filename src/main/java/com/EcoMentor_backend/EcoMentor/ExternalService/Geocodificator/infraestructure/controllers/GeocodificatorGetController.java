package com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.infraestructure.controllers;


import com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.useCases.GetLonLatByCityNameUseCase;
import com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.useCases.dto.CoordinatesDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<CoordinatesDTO>> getLonLatByCityNameUseCase(@RequestParam String cityName,
                                                                           @RequestParam int size) {
        List<CoordinatesDTO> c = getLonLatByCityNameUseCase.execute(cityName, size);
        if (c == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(c);
        }
    }

}
