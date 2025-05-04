package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.FilterRequestDTO;
import java.util.Map;
import org.junit.jupiter.api.Test;


public class FilterRequestDTOTest {

    @Test
    public void testFilterRequestDTOCreation() {
        Map<String, String> filters = Map.of("key1", "value1", "key2", "value2");

        FilterRequestDTO dto = new FilterRequestDTO(
                filters,
                10.0,
                20.0,
                30.0,
                40.0
        );

        assertNotNull(dto);
        assertEquals(filters, dto.getFilters());
        assertEquals(10.0, dto.getMinLatitude());
        assertEquals(20.0, dto.getMaxLatitude());
        assertEquals(30.0, dto.getMinLongitude());
        assertEquals(40.0, dto.getMaxLongitude());
    }

    @Test
    public void testSetFilters() {
        FilterRequestDTO dto = new FilterRequestDTO();
        Map<String, String> filters = Map.of("key1", "value1", "key2", "value2");

        dto.setFilters(filters);

        assertEquals(filters, dto.getFilters());
    }

    @Test
    public void testSetMinLatitude() {
        FilterRequestDTO dto = new FilterRequestDTO();

        dto.setMinLatitude(10.0);

        assertEquals(10.0, dto.getMinLatitude());
    }

    @Test
    public void testSetMaxLatitude() {
        FilterRequestDTO dto = new FilterRequestDTO();

        dto.setMaxLatitude(20.0);

        assertEquals(20.0, dto.getMaxLatitude());
    }

    @Test
    public void testSetMinLongitude() {
        FilterRequestDTO dto = new FilterRequestDTO();

        dto.setMinLongitude(30.0);

        assertEquals(30.0, dto.getMinLongitude());
    }

    @Test
    public void testSetMaxLongitude() {
        FilterRequestDTO dto = new FilterRequestDTO();

        dto.setMaxLongitude(40.0);

        assertEquals(40.0, dto.getMaxLongitude());
    }

}