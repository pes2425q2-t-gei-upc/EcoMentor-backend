package com.EcoMentor_backend.EcoMentor.RecommendationTest.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.repositories.RecommendationRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GenerateRecommendationsUseCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GenerateRecommendationsUseCaseTest {

    private RecommendationRepository recommendationRepository;
    private OfficialCertificateRepository certificateRepository;
    private GenerateRecommendationsUseCase useCase;
    private OfficialCertificate certificate;

    @BeforeEach
    void setUp() {
        recommendationRepository = mock(RecommendationRepository.class);
        certificateRepository = mock(OfficialCertificateRepository.class);
        useCase = new GenerateRecommendationsUseCase(recommendationRepository, certificateRepository);

        // Mock a certificate and common repository behavior
        certificate = mock(OfficialCertificate.class);
        when(certificateRepository.findById(anyLong()))
                .thenReturn(Optional.of(certificate));

        // Stub flags to skip extended recommendations except unconditional ones
        when(certificate.isSolarThermal()).thenReturn(true);
        when(certificate.isDistrictNet()).thenReturn(true);
        when(certificate.isGeothermal()).thenReturn(true);
        when(certificate.isElectricVehicle()).thenReturn(true);
        when(certificate.isEnergeticRehabilitation()).thenReturn(true);
        when(certificate.getResidentialUseVentilation()).thenReturn(0f);
        when(certificate.getRefrigerationEmissions()).thenReturn(0f);
        when(certificate.getAcsEmissions()).thenReturn(0f);
    }

    @Test
    @DisplayName("execute should return core recommendations plus unconditional ones when valid certificate is provided")
    void executeReturnsCoreAndUnconditionalRecsWhenValidCertificateProvided() {
        // Core criteria: only INSULATION, SOLAR, WINDOWS; others below thresholds
        when(certificate.getInsulation()).thenReturn(0.4f);
        when(certificate.isPhotovoltaicSolar()).thenReturn(false);
        when(certificate.getWindowEfficiency()).thenReturn(0.5f);
        when(certificate.getAnnualCost()).thenReturn(1000f);
        // Below thresholds to skip extended recs
        when(certificate.getHeatingEmissions()).thenReturn(0f);
        when(certificate.getFinalEnergyConsumption()).thenReturn(100f);
        when(certificate.getLightingEmissions()).thenReturn(0f);

        List<RecommendationDTO> result = useCase.execute(1L);

        // Extract types
        List<String> types = result.stream()
                .map(RecommendationDTO::getRecommendationType)
                .collect(Collectors.toList());

        // Core recommendations
        assertThat(types).contains("INSULATION", "SOLAR", "WINDOWS");
        // Plus unconditional ones
        assertThat(types).contains("COMMISSIONING", "SMART_CONTROL");
        // Total count should be 5
        assertThat(types).hasSize(5);
    }

    @Test
    @DisplayName("execute should handle null certificate ID gracefully")
    void executeHandlesNullCertificateIdGracefully() {
        assertThrows(ResponseStatusException.class,
                () -> useCase.execute(null));
    }
}
