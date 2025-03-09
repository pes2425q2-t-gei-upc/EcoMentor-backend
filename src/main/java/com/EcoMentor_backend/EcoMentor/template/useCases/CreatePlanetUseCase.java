package com.EcoMentor_backend.EcoMentor.template.useCases;

import com.EcoMentor_backend.EcoMentor.template.entity.Planet;
import com.EcoMentor_backend.EcoMentor.template.infrastructure.repositories.PlanetRepository;
import com.EcoMentor_backend.EcoMentor.template.useCases.dto.planetDTO;
import com.EcoMentor_backend.EcoMentor.template.useCases.mapper.planetMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class CreatePlanetUseCase {
    /*
    In use cases classes, we implement the actual code of the HTTP call.
    That way, the endpoint management is kept simple: calls and actual
    code are separated.
     */
    private final PlanetRepository planetRepository;
    private final planetMapper planetMapper;


    public CreatePlanetUseCase( PlanetRepository planetRepository , planetMapper planetMapper) {
        this.planetRepository = planetRepository;
        this.planetMapper = planetMapper;
    }

    public void execute(planetDTO planetDTO) {
        if( planetRepository.existsByName(planetDTO.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Planet already exists");
        }
        Planet planet = planetMapper.toEntity(planetDTO);
        planetRepository.save(planet);
        System.out.println(planet);
    }

}
