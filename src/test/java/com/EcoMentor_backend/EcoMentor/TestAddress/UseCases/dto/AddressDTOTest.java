package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases.dto;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddressDTOTest {

    @Test
    public void testAddressDTO() {
        // Create a CertificateDTO for testing
        CertificateDTO certificateDTO = CertificateDTO.builder()
                .certificateId(1L)
                .certificateType(CertificateType.OFFICIAL)
                .build();

        // Create an AddressDTO using the builder
        AddressDTO addressDTO = AddressDTO.builder()
                .addressId(1L)
                .addressName("Test Address")
                .addressNumber("123")
                .zipcode(12345)
                .town("Test Town")
                .region("Test Region")
                .province("Test Province")
                .longitude(10.0f)
                .latitude(20.0f)
                .certificates(Collections.singletonList(new CertificateWithoutForeignEntitiesDTO()))
                .build();

        // Verify the AddressDTO fields
        assertEquals(1L, addressDTO.getAddressId());
        assertEquals("Test Address", addressDTO.getAddressName());
        assertEquals("123", addressDTO.getAddressNumber());
        assertEquals(12345, addressDTO.getZipcode());
        assertEquals("Test Town", addressDTO.getTown());
        assertEquals("Test Region", addressDTO.getRegion());
        assertEquals("Test Province", addressDTO.getProvince());
        assertEquals(10.0f, addressDTO.getLongitude());
        assertEquals(20.0f, addressDTO.getLatitude());
        assertNotNull(addressDTO.getCertificates());
        assertEquals(1, addressDTO.getCertificates().size());
        assertEquals(new CertificateWithoutForeignEntitiesDTO(), addressDTO.getCertificates().get(0));
    }
}
