package com.EcoMentor_backend.EcoMentor.ExternalService.GeocodificatorTest.useCases.dto;

import com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.useCases.dto.CoordinatesDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CoordinatesDTOTest {

private CoordinatesDTO coordinatesDTO;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
    coordinatesDTO = new CoordinatesDTO();
}

@Test
void settingAndGettingLongitude() {
    coordinatesDTO.setLongitude(2.1734f);
    assertEquals(2.1734f, coordinatesDTO.getLongitude());
}

@Test
void settingAndGettingLatitude() {
    coordinatesDTO.setLatitude(41.3851f);
    assertEquals(41.3851f, coordinatesDTO.getLatitude());
}

@Test
void coordinatesDTOEquality() {
    CoordinatesDTO coordinatesDTO1 = new CoordinatesDTO(2.1734f, 41.3851f);
    CoordinatesDTO coordinatesDTO2 = new CoordinatesDTO(2.1734f, 41.3851f);
    assertEquals(coordinatesDTO1, coordinatesDTO2);
}

@Test
void coordinatesDTONonEquality() {
    CoordinatesDTO coordinatesDTO1 = new CoordinatesDTO(2.1734f, 41.3851f);
    CoordinatesDTO coordinatesDTO2 = new CoordinatesDTO(2.1735f, 41.3852f);
    assertNotEquals(coordinatesDTO1, coordinatesDTO2);
}

@Test
void coordinatesDTOBuilder() {
    CoordinatesDTO coordinatesDTO = CoordinatesDTO.builder()
            .longitude(2.1734f)
            .latitude(41.3851f)
            .build();
    assertEquals(2.1734f, coordinatesDTO.getLongitude());
    assertEquals(41.3851f, coordinatesDTO.getLatitude());
}
}
