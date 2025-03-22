package com.EcoMentor_backend.EcoMentor.ExternalService.GeocodificatorTest.infraestructure.controllers;

import com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.infraestructure.controllers.GeocodificatorGetController;
import com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.useCases.GetLonLatByCityNameUseCase;
import com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.useCases.dto.CoordinatesDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class GeocodificatorGetControllerTest {

@Mock
private GetLonLatByCityNameUseCase getLonLatByCityNameUseCase;

@InjectMocks
private GeocodificatorGetController geocodificatorGetController;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}

@Test
void getLonLatByCityNameReturnsCoordinatesForValidCityName() {
    String cityName = "Barcelona";
    CoordinatesDTO coordinatesDTO = new CoordinatesDTO(2.1734f, 41.3851f);
    when(getLonLatByCityNameUseCase.execute(cityName)).thenReturn(coordinatesDTO);

    ResponseEntity<CoordinatesDTO> response = geocodificatorGetController.getLonLatByCityNameUseCase(cityName);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(coordinatesDTO, response.getBody());
}

@Test
void getLonLatByCityNameReturnsNotFoundForInvalidCityName() {
    String cityName = "InvalidCity";
    when(getLonLatByCityNameUseCase.execute(cityName)).thenReturn(null);

    ResponseEntity<CoordinatesDTO> response = geocodificatorGetController.getLonLatByCityNameUseCase(cityName);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNull(response.getBody());
}

@Test
void getLonLatByCityNameHandlesEmptyCityName() {
    String cityName = "";
    when(getLonLatByCityNameUseCase.execute(cityName)).thenReturn(null);

    ResponseEntity<CoordinatesDTO> response = geocodificatorGetController.getLonLatByCityNameUseCase(cityName);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNull(response.getBody());
}

@Test
void getLonLatByCityNameHandlesNullCityName() {
    String cityName = null;
    when(getLonLatByCityNameUseCase.execute(cityName)).thenReturn(null);

    ResponseEntity<CoordinatesDTO> response = geocodificatorGetController.getLonLatByCityNameUseCase(cityName);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNull(response.getBody());
}
}
