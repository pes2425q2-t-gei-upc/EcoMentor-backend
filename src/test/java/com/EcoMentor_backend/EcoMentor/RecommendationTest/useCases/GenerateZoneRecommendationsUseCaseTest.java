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

import java.util.ArrayList;
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

    @DisplayName("Returns recommendations when certificate and averages are valid")
    @Test
    void returnsRecommendationsWhenCertificateAndAveragesAreValid() {
        Long certificateId = 1L;
        Integer radius = 5000; // Ensure this matches the expected value
        OfficialCertificate certificate = mock(OfficialCertificate.class);
        AverageValuesDTO averages = new AverageValuesDTO();
        averages.setInsulation(10.0f); // Set valid values for averages
        averages.setWindowEfficiency(8.0f); // Add other required fields
        Address address = mock(Address.class);
        Point location = mock(Point.class);

        when(certificateRepository.findById(certificateId)).thenReturn(Optional.of(certificate));
        when(certificate.getAddress()).thenReturn(address);
        when(address.getLocation()).thenReturn(location);
        when(location.getX()).thenReturn(10.0);
        when(location.getY()).thenReturn(20.0);
        when(averageValuesUseCase.execute(20.0, 10.0, radius)).thenReturn(averages); // Ensure radius matches here
        when(recommendationRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        List<RecommendationDTO> result = generateZoneRecommendationsUseCase.execute(certificateId, radius);

        assertNotNull(result);
        verify(certificateRepository).findById(certificateId);
        verify(averageValuesUseCase).execute(20.0, 10.0, radius);
        verify(recommendationRepository).saveAll(anyList());
    }

    @DisplayName("Throws exception when certificate is not found")
    @Test
    void throwsExceptionWhenCertificateIsNotFound() {
        Long certificateId = 1L;
        Integer radius = 5;

        when(certificateRepository.findById(certificateId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> generateZoneRecommendationsUseCase.execute(certificateId, radius));
        verify(certificateRepository).findById(certificateId);
        verifyNoInteractions(averageValuesUseCase);
    }

    @DisplayName("Handles null average values gracefully")
    @Test
    void handlesNullAverageValuesGracefully() {
        Long certificateId = 1L;
        Integer radius = 5000;
        OfficialCertificate certificate = mock(OfficialCertificate.class);
        Address address = mock(Address.class);
        Point location = mock(Point.class);

        when(certificateRepository.findById(certificateId)).thenReturn(Optional.of(certificate));
        when(certificate.getAddress()).thenReturn(address);
        when(address.getLocation()).thenReturn(location);
        when(location.getX()).thenReturn(10.0);
        when(location.getY()).thenReturn(20.0);
        when(averageValuesUseCase.execute(20.0, 10.0, radius)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> generateZoneRecommendationsUseCase.execute(certificateId, radius));
        verify(certificateRepository).findById(certificateId);
        verify(averageValuesUseCase).execute(20.0, 10.0, radius);
    }

    @DisplayName("Handles empty recommendations list gracefully")
    @Test
    void handlesEmptyRecommendationsListGracefully() {
        Long certificateId = 1L;
        Integer radius = 5000;
        OfficialCertificate certificate = mock(OfficialCertificate.class);
        Address address = mock(Address.class);
        Point location = mock(Point.class);
        AverageValuesDTO averages = mock(AverageValuesDTO.class);

        when(certificateRepository.findById(certificateId)).thenReturn(Optional.of(certificate));
        when(certificate.getAddress()).thenReturn(address);
        when(address.getLocation()).thenReturn(location);
        when(location.getX()).thenReturn(10.0);
        when(location.getY()).thenReturn(20.0);
        when(averageValuesUseCase.execute(20.0, 10.0, radius)).thenReturn(averages);
        when(recommendationRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        List<RecommendationDTO> result = generateZoneRecommendationsUseCase.execute(certificateId, radius);

        assertNotNull(result);
        assertTrue(result.size() == 8);
        verify(certificateRepository).findById(certificateId);
        verify(averageValuesUseCase).execute(20.0, 10.0, radius);
        verify(recommendationRepository).saveAll(anyList());
    }
}