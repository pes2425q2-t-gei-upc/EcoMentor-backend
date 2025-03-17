package com.EcoMentor_backend.EcoMentor.Certificate.UseCases.dto;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CertificateDTOTest {

    @Mock
    private Address address;

    @Mock
    private Recommendation recommendation;

    private CertificateDTO certificateDTO;

    @Test
    void testCertificateDTO() {


        certificateDTO = CertificateDTO.builder()
                .certificateId(1L)
                .certificateType(CertificateType.OFFICIAL)
                .address(address)
                .recommendations(Collections.singletonList(recommendation))
                .build();

        assertEquals(1L, certificateDTO.getCertificateId());
        assertEquals(CertificateType.OFFICIAL, certificateDTO.getCertificateType());
        assertEquals(address, certificateDTO.getAddress());
        assertEquals(recommendation, certificateDTO.getRecommendations().getFirst());
    }
}