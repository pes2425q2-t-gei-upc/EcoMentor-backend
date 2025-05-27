package com.EcoMentor_backend.EcoMentor.Recommendation.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CustomCertificateRepositoryImpl;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculatorResultsDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.repositories.RecommendationRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenerateRecommendationsUseCaseTest {

    @Mock
    private RecommendationRepository recommendationRepository;

    @Mock
    private OfficialCertificateRepository certificateRepository;

    @Mock
    private CustomCertificateRepositoryImpl customCertificateRepository;

    @InjectMocks
    private GenerateRecommendationsUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_WhenCertificateExists_ReturnsRecommendationDTOs() {
        Long certificateId = 1L;
        OfficialCertificate certificate = mock(OfficialCertificate.class);

        when(certificateRepository.findById(certificateId)).thenReturn(Optional.of(certificate));
        when(certificate.getRecommendations()).thenReturn(new ArrayList<>());

        Recommendation recommendation = Recommendation.builder()
                .recommendationId(1L)
                .name("Insulation Upgrade")
                .description("Improve insulation")
                .recommendationType("INSULATION")
                .upgradePercentage(10.0f)
                .upgradedICEE("B")
                .upgradedAnualCost(100.0f)
                .totalPrice(4000.0f)
                .build();

        // Simular que `generate` devuelve una lista con una recomendación
        GenerateRecommendationsUseCase spyUseCase = Mockito.spy(useCase);
        doReturn(List.of(recommendation)).when(spyUseCase).generate(certificate);

        // Ejecutar
        List<RecommendationDTO> result = spyUseCase.execute(certificateId);

        // Verificaciones
        verify(recommendationRepository).deleteAll(anyList());
        verify(recommendationRepository).saveAll(anyList());

        assertEquals(1, result.size());
        RecommendationDTO dto = result.get(0);
        assertEquals("Insulation Upgrade", dto.getName());
        assertEquals("INSULATION", dto.getRecommendationType());
    }

    @Test
    void testExecute_WhenCertificateDoesNotExist_ThrowsException() {
        Long certificateId = 99L;

        when(certificateRepository.findById(certificateId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> useCase.execute(certificateId));

        assertEquals("404 NOT_FOUND \"Certificate not found\"", exception.getMessage());
    }


}
