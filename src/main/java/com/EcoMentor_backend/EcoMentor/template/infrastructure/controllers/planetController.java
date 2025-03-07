package com.EcoMentor_backend.EcoMentor.template.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.template.entity.Planet;
import com.EcoMentor_backend.EcoMentor.template.infrastructure.repositories.planetRepository;
import com.EcoMentor_backend.EcoMentor.template.useCases.dto.planetDTO;
import com.EcoMentor_backend.EcoMentor.template.useCases.mapper.planetMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/planets")
public class planetController {
    private final planetMapper planetMapper;

    //we need the repository in order to use its functions (which are implemented automatically by JPA)
    private final planetRepository planetRepository;

    public planetController(planetRepository planetRepository, planetMapper planetMapper) {
        this.planetRepository = planetRepository;
        this.planetMapper = planetMapper;
    }

    @GetMapping("/test")
    public String test() {
        return "API is accessible";
    }

    //API Endpoint for creating a planet (this is only to understand how to set up an entity/ its endpoints)
    @PostMapping("/create")
    public ResponseEntity<planetDTO> createAndSavePlanet(@RequestBody planetDTO planetDTO) {
        //we can do validation with the repository methods using the DTO attributes
        if (planetRepository.existsByName(planetDTO.getName())) return ResponseEntity.badRequest().build();
        else {
            //First we create an entity based on the body that we receive
            Planet planet = planetMapper.toEntity(planetDTO);

            //We use such entity to save it in the DB with the repository
            Planet savedPlanet = planetRepository.save(planet);

            //Now we return a response with the planetDTO of the one we just created (using the mapper again)

            return ResponseEntity.ok(planetMapper.toDTO(savedPlanet));
        }
    }

}
