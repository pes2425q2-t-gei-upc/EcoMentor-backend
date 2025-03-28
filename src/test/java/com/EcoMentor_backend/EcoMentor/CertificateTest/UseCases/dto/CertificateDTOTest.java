package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTOWithoutCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;





public class CertificateDTOTest {

    @Mock
    private AddressDTOWithoutCertificate addressDTO;

    @Mock
    private RecommendationDTO recommendationDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCertificateDTOCreation() {
        List<RecommendationDTO> recommendations = List.of(recommendationDTO);

        CertificateDTO certificateDTO = CertificateDTO.builder()
                .certificateId(1L)
                .certificateType(CertificateType.OFFICIAL)
                .addressDTO(addressDTO)
                .recommendations(recommendations)
                .build();

        assertNotNull(certificateDTO);
        assertEquals(1L, certificateDTO.getCertificateId());
        assertEquals(CertificateType.OFFICIAL, certificateDTO.getCertificateType());
        assertEquals(addressDTO, certificateDTO.getAddressDTO());
        assertEquals(1, certificateDTO.getRecommendations().size());
        assertEquals(recommendationDTO, certificateDTO.getRecommendations().getFirst());
    }
}