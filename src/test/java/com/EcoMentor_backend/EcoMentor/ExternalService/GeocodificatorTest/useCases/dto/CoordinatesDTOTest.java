package com.EcoMentor_backend.EcoMentor.ExternalService.GeocodificatorTest.useCases.dto;

import com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.useCases.dto.CoordinatesDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CoordinatesDTOTest {

private CoordinatesDTO coordinatesDTO;

    @BeforeEach
    void setUp() {
        coordinatesDTO = new CoordinatesDTO();
    }

    @Test
    void settingAndGettingName() {
        coordinatesDTO.setName("Barcelona");
        assertEquals("Barcelona", coordinatesDTO.getName());
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
        CoordinatesDTO coordinatesDTO1 = new CoordinatesDTO("Barcelona", 2.1734f, 41.3851f);
        CoordinatesDTO coordinatesDTO2 = new CoordinatesDTO("Barcelona", 2.1734f, 41.3851f);
        assertEquals(coordinatesDTO1, coordinatesDTO2);
    }

    @Test
    void coordinatesDTONonEquality() {
        CoordinatesDTO coordinatesDTO1 = new CoordinatesDTO("Barcelona", 2.1734f, 41.3851f);
        CoordinatesDTO coordinatesDTO2 = new CoordinatesDTO("Madrid", 2.1744f, 41.3861f);
        assertNotEquals(coordinatesDTO1, coordinatesDTO2);
    }

    @Test
    void coordinatesDTOBuilder() {
        CoordinatesDTO coordinatesDTO = CoordinatesDTO.builder()
                .name("Barcelona")
                .longitude(2.1734f)
                .latitude(41.3851f)
                .build();
        assertEquals("Barcelona", coordinatesDTO.getName());
        assertEquals(2.1734f, coordinatesDTO.getLongitude());
        assertEquals(41.3851f, coordinatesDTO.getLatitude());
    }
}