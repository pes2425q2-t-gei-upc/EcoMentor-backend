package com.EcoMentor_backend.EcoMentor.RecommendationTest.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.CalculateRecommendationValuesUseCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.CreateRecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.TotalValuesRecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.mapper.RecommendationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalculateRecommendationValuesUseCaseTest {

    @Mock
    private RecommendationMapper recommendationMapper;

    @Mock
    private OfficialCertificateRepository certificateRepository;

    @InjectMocks
    private CalculateRecommendationValuesUseCase calculateRecommendationValuesUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Calcula correctamente los valores totales para recomendaciones válidas")
    @Test
    void calculaCorrectamenteValoresTotalesParaRecomendacionesValidas() {
        long certificateId = 1L;
        OfficialCertificate certificate = mock(OfficialCertificate.class);
        CreateRecommendationDTO recommendationDTO1 = mock(CreateRecommendationDTO.class);
        CreateRecommendationDTO recommendationDTO2 = mock(CreateRecommendationDTO.class);
        Recommendation recommendation1 = mock(Recommendation.class);
        Recommendation recommendation2 = mock(Recommendation.class);

        when(certificateRepository.findById(certificateId)).thenReturn(java.util.Optional.of(certificate));
        when(certificate.getAnnualCost()).thenReturn(5000.0f);
        when(recommendationMapper.toEntity(recommendationDTO1)).thenReturn(recommendation1);
        when(recommendationMapper.toEntity(recommendationDTO2)).thenReturn(recommendation2);
        when(recommendation1.getTotalPrice()).thenReturn(1000.0f);
        when(recommendation2.getTotalPrice()).thenReturn(2000.0f);
        when(recommendation1.getUpgradedAnualCost()).thenReturn(1500.0f);
        when(recommendation2.getUpgradedAnualCost()).thenReturn(1000.0f);

        TotalValuesRecommendationDTO result = calculateRecommendationValuesUseCase.calculateValues(
                List.of(recommendationDTO1, recommendationDTO2), certificateId);

        assertNotNull(result);
        assertEquals(3000.0f, result.getTotalCost());
        assertEquals(2500.0f, result.getTotalNewAnnualCost());
        assertEquals(5000.0f, result.getTotalOldAnnualCost());
        assertEquals(2500.0f, result.getTotalSavings());
        assertEquals("A", result.getQualificationNew());
        verify(certificateRepository).findById(certificateId);
        verify(recommendationMapper, times(2)).toEntity(any(CreateRecommendationDTO.class));
    }

    @DisplayName("Lanza excepción cuando el certificado no se encuentra")
    @Test
    void lanzaExcepcionCuandoCertificadoNoSeEncuentra() {
        long certificateId = 1L;

        when(certificateRepository.findById(certificateId)).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> calculateRecommendationValuesUseCase.calculateValues(
                List.of(mock(CreateRecommendationDTO.class)), certificateId));
        verify(certificateRepository).findById(certificateId);
        verifyNoInteractions(recommendationMapper);
    }

    @DisplayName("Maneja correctamente una lista vacía de recomendaciones")
    @Test
    void manejaCorrectamenteListaVaciaDeRecomendaciones() {
        long certificateId = 1L;
        OfficialCertificate certificate = mock(OfficialCertificate.class);

        when(certificateRepository.findById(certificateId)).thenReturn(java.util.Optional.of(certificate));
        when(certificate.getAnnualCost()).thenReturn(5000.0f);

        TotalValuesRecommendationDTO result = calculateRecommendationValuesUseCase.calculateValues(
                List.of(), certificateId);

        assertNotNull(result);
        assertEquals(0.0f, result.getTotalCost());
        assertEquals(0.0f, result.getTotalNewAnnualCost());
        assertEquals(5000.0f, result.getTotalOldAnnualCost());
        assertEquals(5000.0f, result.getTotalSavings());
        assertEquals("A", result.getQualificationNew());
        verify(certificateRepository).findById(certificateId);
        verifyNoInteractions(recommendationMapper);
    }
}