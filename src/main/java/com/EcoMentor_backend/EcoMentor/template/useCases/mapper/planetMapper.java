package com.EcoMentor_backend.EcoMentor.template.useCases.mapper;

import com.EcoMentor_backend.EcoMentor.template.entity.Planet;
import com.EcoMentor_backend.EcoMentor.template.useCases.dto.planetDTO;
import org.springframework.stereotype.Component;

@Component
public class planetMapper {
    //Entity to DTO
    public planetDTO toDTO(Planet planet) {
        if (planet == null) {
            return null;
        }
        planetDTO dto = new planetDTO();
        dto.setName(planet.getName());
        dto.setDiameterKm(planet.getDiameterKm());
        dto.setDiscoveryDate(planet.getDiscoveryDate());
        dto.setPlanetType(planet.getPlanetType());

        return dto;
    }

    //DTO to Entity
    public Planet toEntity(planetDTO dto) {
        if (dto == null) {
            return null;
        }
        Planet planet = new Planet();
        planet.setName(dto.getName());
        planet.setDiameterKm(dto.getDiameterKm());
        planet.setDiscoveryDate(dto.getDiscoveryDate());
        planet.setPlanetType(dto.getPlanetType());

        return planet;
    }
}