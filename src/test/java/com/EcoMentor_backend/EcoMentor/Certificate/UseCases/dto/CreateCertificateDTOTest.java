package com.EcoMentor_backend.EcoMentor.Certificate.UseCases.dto;

import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCertificateDTOTest {

    @Mock
    private Recommendation recommendation;

    @Mock
    private CreateAddressDTO createAddressDTO;

    private CreateCertificateDTO createCertificateDTO;

    @Test
    void testCreateCertificateDTO() {
        createCertificateDTO = CreateCertificateDTO.builder()
                .certificateId(1L)
                .certificateType(CertificateType.OFFICIAL)
                .createAddressDTO(createAddressDTO)
                .recommendations(Collections.singletonList(recommendation))
                .build();

        assertEquals(1L, createCertificateDTO.getCertificateId());
        assertEquals(CertificateType.OFFICIAL, createCertificateDTO.getCertificateType());
        assertEquals(createAddressDTO, createCertificateDTO.getCreateAddressDTO());
        assertEquals(recommendation, createCertificateDTO.getRecommendations().getFirst());
    }
}