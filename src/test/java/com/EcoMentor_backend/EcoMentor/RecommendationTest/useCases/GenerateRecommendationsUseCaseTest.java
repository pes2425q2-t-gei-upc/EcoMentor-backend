package com.EcoMentor_backend.EcoMentor.RecommendationTest.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.repositories.RecommendationRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GenerateRecommendationsUseCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

class GenerateRecommendationsUseCaseTest {

    private RecommendationRepository recommendationRepository;
    private OfficialCertificateRepository certificateRepository;
    private GenerateRecommendationsUseCase generateRecommendationsUseCase;

    @BeforeEach
    void setUp() {
        recommendationRepository = mock(RecommendationRepository.class);
        certificateRepository = mock(OfficialCertificateRepository.class);
        generateRecommendationsUseCase = new GenerateRecommendationsUseCase(recommendationRepository, certificateRepository);
    }

    @DisplayName("execute should throw exception when certificate is not found")
    @Test
    void executeThrowsExceptionWhenCertificateNotFound() {
        Long certificateId = 1L;
        when(certificateRepository.findById(certificateId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> generateRecommendationsUseCase.execute(certificateId));
    }

    @DisplayName("execute should generate recommendations and return DTOs when certificate is valid")
    @Test
    void executeGeneratesRecommendationsAndReturnsDTOsWhenCertificateIsValid() {
        Long certificateId = 1L;
        OfficialCertificate certificate = mock(OfficialCertificate.class);
        when(certificateRepository.findById(certificateId)).thenReturn(Optional.of(certificate));
        when(certificate.getInsulation()).thenReturn(0.4f);
        when(certificate.isPhotovoltaicSolar()).thenReturn(false);
        when(certificate.getWindowEfficiency()).thenReturn(0.5f);
        when(certificate.getHeatingEmissions()).thenReturn(50f);
        when(certificate.getFinalEnergyConsumption()).thenReturn(200f);
        when(certificate.getLightingEmissions()).thenReturn(6f);
        when(certificate.getAnnualCost()).thenReturn(1000f);

        List<RecommendationDTO> result = generateRecommendationsUseCase.execute(certificateId);

        assertEquals(7, result.size());
        verify(recommendationRepository, times(1)).saveAll(anyList());
        verify(certificateRepository, times(1)).save(certificate);
    }

    @DisplayName("generate should return empty list when no recommendations are applicable")
    @Test
    void generateReturnsEmptyListWhenNoRecommendationsAreApplicable() {
        OfficialCertificate certificate = mock(OfficialCertificate.class);
        when(certificate.getInsulation()).thenReturn(0.6f);
        when(certificate.isPhotovoltaicSolar()).thenReturn(true);
        when(certificate.getWindowEfficiency()).thenReturn(0.7f);
        when(certificate.getHeatingEmissions()).thenReturn(30f);
        when(certificate.getFinalEnergyConsumption()).thenReturn(150f);
        when(certificate.getLightingEmissions()).thenReturn(4f);

        List<Recommendation> result = generateRecommendationsUseCase.generate(certificate);

        assertEquals(0, result.size());
    }
}