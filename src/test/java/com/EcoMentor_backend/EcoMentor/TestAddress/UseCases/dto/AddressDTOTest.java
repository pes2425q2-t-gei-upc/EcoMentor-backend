package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases.dto;

import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTO;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressDTOTest {

    @Test
    void testAddressDTOGettersAndSetters() {
        List<Long> certificatesId = Arrays.asList(1L, 2L, 3L);
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressId(1L);
        addressDTO.setAddressName("Test Address");
        addressDTO.setAddressNumber("123");
        addressDTO.setZipcode(12345);
        addressDTO.setTown("Test Town");
        addressDTO.setRegion("Test Region");
        addressDTO.setProvince("Test Province");
        addressDTO.setLongitude(12.34f);
        addressDTO.setLatitude(56.78f);
        addressDTO.setCertificatesId(certificatesId);

        assertEquals(1L, addressDTO.getAddressId());
        assertEquals("Test Address", addressDTO.getAddressName());
        assertEquals("123", addressDTO.getAddressNumber());
        assertEquals(12345, addressDTO.getZipcode());
        assertEquals("Test Town", addressDTO.getTown());
        assertEquals("Test Region", addressDTO.getRegion());
        assertEquals("Test Province", addressDTO.getProvince());
        assertEquals(12.34f, addressDTO.getLongitude());
        assertEquals(56.78f, addressDTO.getLatitude());
        assertEquals(certificatesId, addressDTO.getCertificatesId());
    }

    @Test
    void testAddressDTOBuilder() {
        List<Long> certificatesId = Arrays.asList(1L, 2L, 3L);
        AddressDTO addressDTO = AddressDTO.builder()
                .addressId(1L)
                .addressName("Test Address")
                .addressNumber("123")
                .zipcode(12345)
                .town("Test Town")
                .region("Test Region")
                .province("Test Province")
                .longitude(12.34f)
                .latitude(56.78f)
                .certificatesId(certificatesId)
                .build();

        assertEquals(1L, addressDTO.getAddressId());
        assertEquals("Test Address", addressDTO.getAddressName());
        assertEquals("123", addressDTO.getAddressNumber());
        assertEquals(12345, addressDTO.getZipcode());
        assertEquals("Test Town", addressDTO.getTown());
        assertEquals("Test Region", addressDTO.getRegion());
        assertEquals("Test Province", addressDTO.getProvince());
        assertEquals(12.34f, addressDTO.getLongitude());
        assertEquals(56.78f, addressDTO.getLatitude());
        assertEquals(certificatesId, addressDTO.getCertificatesId());
    }
}
