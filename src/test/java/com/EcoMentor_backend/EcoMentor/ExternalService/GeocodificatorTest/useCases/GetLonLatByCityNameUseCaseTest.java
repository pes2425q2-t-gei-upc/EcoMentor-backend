package com.EcoMentor_backend.EcoMentor.ExternalService.GeocodificatorTest.useCases;


import com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.useCases.GetLonLatByCityNameUseCase;
import com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.useCases.dto.CoordinatesDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class GetLonLatByCityNameUseCaseTest {

@Mock
private RestTemplate restTemplate;

@InjectMocks
private GetLonLatByCityNameUseCase getLonLatByCityNameUseCase;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}

@Test
void executeReturnsCoordinatesForValidCityName() throws Exception {
    String cityName = "Barcelona";
    String jsonResponse = "{\"features\":[{\"geometry\":{\"coordinates\":[2.1734,41.3851]}}]}";
    when(restTemplate.getForEntity("https://eines.icgc.cat/geocodificador/cerca?text=Barcelona", String.class))
            .thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));

    CoordinatesDTO result = getLonLatByCityNameUseCase.execute(cityName);

    assertEquals(2.1734f, result.getLongitude());
    assertEquals(41.3851f, result.getLatitude());
}

@Test
void executeReturnsNullForInvalidCityName() {
    String cityName = "InvalidCity";
    String jsonResponse = "{\"features\":[]}";
    when(restTemplate.getForEntity("https://eines.icgc.cat/geocodificador/cerca?text=InvalidCity", String.class))
            .thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));

    CoordinatesDTO result = getLonLatByCityNameUseCase.execute(cityName);

    assertNull(result);
}

@Test
void executeReturnsNullForErrorResponse() {
    String cityName = "ErrorCity";
    when(restTemplate.getForEntity("https://eines.icgc.cat/geocodificador/cerca?text=ErrorCity", String.class))
            .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

    CoordinatesDTO result = getLonLatByCityNameUseCase.execute(cityName);

    assertNull(result);
}

@Test
void executeReturnsNullForMalformedJsonResponse() {
    String cityName = "MalformedCity";
    String jsonResponse = "{\"features\":[{\"geometry\":{\"coordinates\":[]}}]}";
    when(restTemplate.getForEntity("https://eines.icgc.cat/geocodificador/cerca?text=MalformedCity", String.class))
            .thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));

    CoordinatesDTO result = getLonLatByCityNameUseCase.execute(cityName);

    assertNull(result);
}
}