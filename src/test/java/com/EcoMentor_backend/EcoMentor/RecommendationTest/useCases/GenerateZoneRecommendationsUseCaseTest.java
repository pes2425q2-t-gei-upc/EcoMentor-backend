package com.EcoMentor_backend.EcoMentor.RecommendationTest.useCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAverageValuesInAZonUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AverageValuesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CustomCertificateRepositoryImpl;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculatorResultsDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.repositories.RecommendationRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GenerateZoneRecommendationsUseCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenerateZoneRecommendationsUseCaseTest {

    @Mock
    private RecommendationRepository recommendationRepository;

    @Mock
    private OfficialCertificateRepository certificateRepository;

    @Mock
    private GetAverageValuesInAZonUseCase averageValuesUseCase;

    @Mock
    private CustomCertificateRepositoryImpl customCertificateRepository;

    @InjectMocks
    private GenerateZoneRecommendationsUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_shouldReturnRecommendations_whenDataIsValid() {
        // Given
        Long certificateId = 1L;
        int radius = 10;

        OfficialCertificate certificate = mock(OfficialCertificate.class);
        when(certificateRepository.findById(certificateId)).thenReturn(Optional.of(certificate));
        when(certificate.getRecommendations()).thenReturn(new ArrayList<>());

        // Mock manual de Address y Location
        Address address = mock(Address.class);
        Point location = mock(Point.class);
        when(location.getX()).thenReturn(2.0);
        when(location.getY()).thenReturn(1.0);
        when(address.getLocation()).thenReturn(location);
        when(certificate.getAddress()).thenReturn(address);

        when(certificate.getClimateZone()).thenReturn("C");
        when(certificate.getBuildingUse()).thenReturn("RESIDENTIAL");
        when(certificate.getNonRenewablePrimaryEnergy()).thenReturn(100f);
        when(certificate.isSolarThermal()).thenReturn(false);
        when(certificate.isPhotovoltaicSolar()).thenReturn(false);
        when(certificate.isBiomass()).thenReturn(false);
        when(certificate.isDistrictNet()).thenReturn(false);
        when(certificate.isGeothermal()).thenReturn(false);
        when(certificate.getInsulation()).thenReturn(1.0f);
        when(certificate.getWindowEfficiency()).thenReturn(1.0f);
        when(certificate.getHeatingEmissions()).thenReturn(10f);
        when(certificate.getRefrigerationEmissions()).thenReturn(10f);
        when(certificate.getAcsEmissions()).thenReturn(10f);
        when(certificate.getLightingEmissions()).thenReturn(10f);
        when(certificate.getResidentialUseVentilation()).thenReturn(1.0f);
        when(certificate.getAnnualCost()).thenReturn(1000f);
        when(certificate.getFinalEnergyConsumption()).thenReturn(100f);

        AverageValuesDTO averages = new AverageValuesDTO();
        averages.setInsulation(2f);
        averages.setWindowEfficiency(2f);
        averages.setHeatingEmissions(5f);
        averages.setLightingEmissions(5f);
        averages.setFinalEnergyConsumption(50f);

        when(averageValuesUseCase.execute(anyDouble(), anyDouble(), eq(radius))).thenReturn(averages);

        CalculatorResultsDTO results = new CalculatorResultsDTO();
        results.setIoNonRenewablePrimaryEnergy(80f);
        results.setNonRenewablePrimaryQualification(Qualification.B);

        when(customCertificateRepository.calculateAproxInsulation(anyInt(), anyString())).thenReturn(2f);
        when(customCertificateRepository.calculateAproxWindowEfficiciency(anyInt(), anyString())).thenReturn(2f);
        when(customCertificateRepository.calculateRecomendationQualifications(any(), any(), anyFloat(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyFloat(), anyFloat(), anyFloat(), anyFloat(), anyFloat(), anyFloat(), anyFloat()))
                .thenReturn(results);

        // When
        List<RecommendationDTO> dtoList = useCase.execute(certificateId, radius);

        // Then
        assertNotNull(dtoList);
        assertFalse(dtoList.isEmpty());

        verify(certificateRepository).findById(certificateId);
        verify(recommendationRepository).deleteAll(anyList());
        verify(recommendationRepository).saveAll(anyList());
    }

    @Test
    void execute_shouldThrowException_whenCertificateNotFound() {
        when(certificateRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> useCase.execute(1L, 10));
        assertEquals("404 NOT_FOUND \"Certificate not found\"", ex.getMessage());
    }

    @Test
    void execute_shouldThrowException_whenAverageValuesAreNull() {
        OfficialCertificate certificate = mock(OfficialCertificate.class);
        when(certificateRepository.findById(1L)).thenReturn(Optional.of(certificate));
        when(certificate.getRecommendations()).thenReturn(new ArrayList<>());

        // Mock manual de Address y Location
        Address address = mock(Address.class);
        Point location = mock(Point.class);
        when(location.getX()).thenReturn(2.0);
        when(location.getY()).thenReturn(1.0);
        when(address.getLocation()).thenReturn(location);
        when(certificate.getAddress()).thenReturn(address);

        when(averageValuesUseCase.execute(anyDouble(), anyDouble(), anyInt())).thenReturn(null);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> useCase.execute(1L, 10));
        assertEquals("400 BAD_REQUEST \"Average values not found\"", ex.getMessage());
    }
}
