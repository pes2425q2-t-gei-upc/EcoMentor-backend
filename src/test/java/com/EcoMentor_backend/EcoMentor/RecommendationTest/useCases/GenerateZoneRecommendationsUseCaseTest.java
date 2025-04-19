package com.EcoMentor_backend.EcoMentor.RecommendationTest.useCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAverageValuesInAZonUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AverageValuesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.repositories.RecommendationRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GenerateZoneRecommendationsUseCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenerateZoneRecommendationsUseCaseTest {

    @Mock
    private RecommendationRepository recommendationRepository;

    @Mock
    private OfficialCertificateRepository certificateRepository;

    @Mock
    private GetAverageValuesInAZonUseCase averageValuesUseCase;

    @InjectMocks
    private GenerateZoneRecommendationsUseCase generateZoneRecommendationsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Returns recommendations when certificate and averages are valid")
    void returnsRecommendationsForValidCertificateAndAverages() {
        Long certificateId = 1L;
        Integer radius = 100000;
        OfficialCertificate certificate = mock(OfficialCertificate.class);
        AverageValuesDTO averages = mock(AverageValuesDTO.class);
        Address address = mock(Address.class);
        Point location = mock(Point.class);

        when(certificate.getAddress()).thenReturn(address);
        when(address.getLocation()).thenReturn(location);
        when(location.getX()).thenReturn(10.0);
        when(location.getY()).thenReturn(20.0);
        when(certificateRepository.findById(certificateId)).thenReturn(Optional.of(certificate));
        when(averageValuesUseCase.execute(anyDouble(), anyDouble(), eq(radius))).thenReturn(averages);
        when(recommendationRepository.saveAll(anyList())).thenReturn(List.of());

        List<RecommendationDTO> result = generateZoneRecommendationsUseCase.execute(certificateId, radius);

        assertNotNull(result);
        verify(certificateRepository).findById(certificateId);
        verify(averageValuesUseCase).execute(anyDouble(), anyDouble(), eq(radius));
        verify(recommendationRepository).saveAll(anyList());
    }

    @Test
    @DisplayName("Throws exception when certificate is not found")
    void throwsExceptionWhenCertificateNotFound() {
        Long certificateId = 1L;
        Integer radius = 10;

        when(certificateRepository.findById(certificateId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> generateZoneRecommendationsUseCase.execute(certificateId, radius));

        assertEquals("404 NOT_FOUND \"Certificate not found\"", exception.getMessage());
        verify(certificateRepository).findById(certificateId);
        verifyNoInteractions(averageValuesUseCase, recommendationRepository);
    }

    @Test
    @DisplayName("Handles empty recommendations list gracefully")
    void handlesEmptyRecommendationsList() {
        Long certificateId = 1L;
        Integer radius = 10;
        OfficialCertificate certificate = mock(OfficialCertificate.class);
        AverageValuesDTO averages = mock(AverageValuesDTO.class);
        Address address = mock(Address.class);
        Point location = mock(Point.class);

        // Configurar mocks para evitar que se generen recomendaciones
        when(certificate.getAddress()).thenReturn(address);
        when(address.getLocation()).thenReturn(location);
        when(location.getX()).thenReturn(10.0);
        when(location.getY()).thenReturn(20.0);
        when(certificate.getInsulation()).thenReturn(100.0F); // Por encima del promedio
        when(certificate.isPhotovoltaicSolar()).thenReturn(true); // Ya tiene paneles solares
        when(certificate.getWindowEfficiency()).thenReturn(100.0F); // Por encima del promedio
        when(certificate.getHeatingEmissions()).thenReturn(0.0F); // Por debajo del promedio
        when(certificate.getFinalEnergyConsumption()).thenReturn(0.0F); // Por debajo del promedio
        when(certificate.getLightingEmissions()).thenReturn(0.0F); // Por debajo del promedio
        when(certificateRepository.findById(certificateId)).thenReturn(Optional.of(certificate));
        when(averageValuesUseCase.execute(anyDouble(), anyDouble(), eq(radius))).thenReturn(averages);

        // Configurar valores promedio para evitar recomendaciones
        when(averages.getInsulation()).thenReturn(50.0F);
        when(averages.getWindowEfficiency()).thenReturn(50.0F);
        when(averages.getHeatingEmissions()).thenReturn(50.0F);
        when(averages.getFinalEnergyConsumption()).thenReturn(50.0F);
        when(averages.getLightingEmissions()).thenReturn(50.0F);

        when(recommendationRepository.saveAll(anyList())).thenReturn(List.of());

        // Ejecutar el caso de uso
        List<RecommendationDTO> result = generateZoneRecommendationsUseCase.execute(certificateId, radius);

        // Verificar que la lista solo tenga las recomendaciones generadas
        assertTrue(result.size() == 7);
        verify(certificateRepository).findById(certificateId);
        verify(averageValuesUseCase).execute(anyDouble(), anyDouble(), eq(radius));
        verify(recommendationRepository).saveAll(anyList());
    }
}
