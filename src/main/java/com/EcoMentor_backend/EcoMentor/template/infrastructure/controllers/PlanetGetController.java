package com.EcoMentor_backend.EcoMentor.template.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.template.infrastructure.repositories.PlanetRepository;
import com.EcoMentor_backend.EcoMentor.template.useCases.mapper.planetMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/planets")
public class PlanetGetController {
    private final planetMapper planetMapper;

    //we need the repository in order to use its functions (which are implemented automatically by JPA)
    private final PlanetRepository planetRepository;

    public PlanetGetController(PlanetRepository planetRepository, planetMapper planetMapper) {
        this.planetRepository = planetRepository;
        this.planetMapper = planetMapper;
    }

    @GetMapping("/test")
    public String test() {
        return "API is accessible";
    }

}
