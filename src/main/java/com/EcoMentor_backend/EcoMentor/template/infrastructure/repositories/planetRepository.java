package com.EcoMentor_backend.EcoMentor.template.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.template.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface planetRepository extends JpaRepository<Planet, Long> {
    //check if a planet with this name already exists
    boolean existsByName(String name);

    //find planets by type
    List<Planet> findByPlanetType(Planet.PlanetType planetType);

    // Optional: find by name
    Optional<Planet> findByName(String name);
}