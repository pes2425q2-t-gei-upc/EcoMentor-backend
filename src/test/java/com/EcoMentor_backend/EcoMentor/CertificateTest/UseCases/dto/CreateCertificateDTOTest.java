package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;





public class CreateCertificateDTOTest {

    @Mock
    private CreateAddressDTO createAddressDTO;

    @Mock
    private Recommendation recommendation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCertificateDTOCreation() {
        List<Recommendation> recommendations = List.of(recommendation);

        CreateCertificateDTO dto = CreateCertificateDTO.builder()
                .certificateType(CertificateType.OFFICIAL)
                .createAddressDTO(createAddressDTO)
                .recommendations(recommendations)
                .build();

        assertNotNull(dto);
        assertEquals(CertificateType.OFFICIAL, dto.getCertificateType());
        assertEquals(createAddressDTO, dto.getCreateAddressDTO());
        assertEquals(1, dto.getRecommendations().size());
        assertEquals(recommendation, dto.getRecommendations().getFirst());
    }
}