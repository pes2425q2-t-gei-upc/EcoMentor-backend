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

import java.util.Collections;
import java.util.List;

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
void getLonLatByCityNameReturnsCoordinatesListForValidCityName() {
    String cityName = "Barcelona";
    int size = 1;
    CoordinatesDTO coordinatesDTO = new CoordinatesDTO("Barcelona", 2.1734f, 41.3851f);
    when(getLonLatByCityNameUseCase.execute(cityName, size)).thenReturn(Collections.singletonList(coordinatesDTO));

    ResponseEntity<List<CoordinatesDTO>> response = geocodificatorGetController.getLonLatByCityNameUseCase(cityName, size);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1, response.getBody().size());
    assertEquals(coordinatesDTO, response.getBody().get(0));
}

@Test
void getLonLatByCityNameReturnsNotFoundForInvalidCityName() {
    String cityName = "InvalidCity";
    int size = 1;
    when(getLonLatByCityNameUseCase.execute(cityName, size)).thenReturn(null);

    ResponseEntity<List<CoordinatesDTO>> response = geocodificatorGetController.getLonLatByCityNameUseCase(cityName, size);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNull(response.getBody());
}

@Test
void getLonLatByCityNameHandlesEmptyCityName() {
    String cityName = "";
    int size = 1;
    when(getLonLatByCityNameUseCase.execute(cityName, size)).thenReturn(null);

    ResponseEntity<List<CoordinatesDTO>> response = geocodificatorGetController.getLonLatByCityNameUseCase(cityName, size);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNull(response.getBody());
}

@Test
void getLonLatByCityNameHandlesNullCityName() {
    String cityName = null;
    int size = 1;
    when(getLonLatByCityNameUseCase.execute(cityName, size)).thenReturn(null);

    ResponseEntity<List<CoordinatesDTO>> response = geocodificatorGetController.getLonLatByCityNameUseCase(cityName, size);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNull(response.getBody());
}

@Test
void getLonLatByCityNameReturnsLimitedNumberOfCoordinates() {
    String cityName = "CityWithManyResults";
    int size = 1;
    CoordinatesDTO coordinatesDTO1 = new CoordinatesDTO("Location1", 2.1734f, 41.3851f);
    CoordinatesDTO coordinatesDTO2 = new CoordinatesDTO("Location2", 2.1744f, 41.3861f);
    when(getLonLatByCityNameUseCase.execute(cityName, size)).thenReturn(Collections.singletonList(coordinatesDTO1));

    ResponseEntity<List<CoordinatesDTO>> response = geocodificatorGetController.getLonLatByCityNameUseCase(cityName, size);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1, response.getBody().size());
    assertEquals(coordinatesDTO1, response.getBody().get(0));
}
}