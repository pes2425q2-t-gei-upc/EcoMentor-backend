package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import org.junit.jupiter.api.Test;



public class CertificateWithoutForeignEntitiesDTOTest {

    @Test
    public void testCertificateWithoutForeignEntitiesDTOCreation() {
        CertificateWithoutForeignEntitiesDTO dto = CertificateWithoutForeignEntitiesDTO.builder()
                .certificateId(1L)
                .certificateType(CertificateType.OFFICIAL)
                .build();

        assertNotNull(dto);
        assertEquals(1L, dto.getCertificateId());
        assertEquals(CertificateType.OFFICIAL, dto.getCertificateType());
    }
}
