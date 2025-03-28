package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases.dto;

import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateAddressDTOTest {

    @Test
    void testCreateAddressDTOGettersAndSetters() {
        List<Long> certificates = Arrays.asList(1L, 2L, 3L);
        CreateAddressDTO createAddressDTO = new CreateAddressDTO();
        createAddressDTO.setAddressName("Test Address");
        createAddressDTO.setAddressNumber("123");
        createAddressDTO.setZipcode(12345);
        createAddressDTO.setTown("Test Town");
        createAddressDTO.setRegion("Test Region");
        createAddressDTO.setProvince("Test Province");
        createAddressDTO.setLongitude(12.34f);
        createAddressDTO.setLatitude(56.78f);
        createAddressDTO.setCertificates(certificates);

        assertEquals("Test Address", createAddressDTO.getAddressName());
        assertEquals("123", createAddressDTO.getAddressNumber());
        assertEquals(12345, createAddressDTO.getZipcode());
        assertEquals("Test Town", createAddressDTO.getTown());
        assertEquals("Test Region", createAddressDTO.getRegion());
        assertEquals("Test Province", createAddressDTO.getProvince());
        assertEquals(12.34f, createAddressDTO.getLongitude());
        assertEquals(56.78f, createAddressDTO.getLatitude());
        assertEquals(certificates, createAddressDTO.getCertificates());
    }

    @Test
    void testCreateAddressDTOBuilder() {
        List<Long> certificates = Arrays.asList(1L, 2L, 3L);
        CreateAddressDTO createAddressDTO = CreateAddressDTO.builder()
                .addressName("Test Address")
                .addressNumber("123")
                .zipcode(12345)
                .town("Test Town")
                .region("Test Region")
                .province("Test Province")
                .longitude(12.34f)
                .latitude(56.78f)
                .certificates(certificates)
                .build();

        assertEquals("Test Address", createAddressDTO.getAddressName());
        assertEquals("123", createAddressDTO.getAddressNumber());
        assertEquals(12345, createAddressDTO.getZipcode());
        assertEquals("Test Town", createAddressDTO.getTown());
        assertEquals("Test Region", createAddressDTO.getRegion());
        assertEquals("Test Province", createAddressDTO.getProvince());
        assertEquals(12.34f, createAddressDTO.getLongitude());
        assertEquals(56.78f, createAddressDTO.getLatitude());
        assertEquals(certificates, createAddressDTO.getCertificates());
    }
}
