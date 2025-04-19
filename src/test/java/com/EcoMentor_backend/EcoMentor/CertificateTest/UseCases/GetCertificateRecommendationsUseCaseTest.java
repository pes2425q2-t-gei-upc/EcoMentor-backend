package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateRecommendationsUseCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.mapper.RecommendationMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GetCertificateRecommendationsUseCaseTest {

    @Mock
    private OfficialCertificateRepository certificateRepository;

    @Mock
    private RecommendationMapper recommendationMapper;

    @InjectMocks
    private GetCertificateRecommendationsUseCase useCase;

    @Test
    void executeReturnsRecommendationsWhenCertificateExists() {
        Long certificateId = 1L;
        OfficialCertificate certificate = new OfficialCertificate();
        Recommendation recommendation = new Recommendation();
        RecommendationDTO recommendationDTO = new RecommendationDTO();

        certificate.setRecommendations(List.of(recommendation));
        Mockito.when(certificateRepository.findById(certificateId)).thenReturn(Optional.of(certificate));
        Mockito.when(recommendationMapper.toDTO(recommendation)).thenReturn(recommendationDTO);

        List<RecommendationDTO> result = useCase.execute(certificateId);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(recommendationDTO, result.get(0));
    }

    @Test
    void executeThrowsNotFoundWhenCertificateDoesNotExist() {
        Long certificateId = 1L;

        Mockito.when(certificateRepository.findById(certificateId)).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> useCase.execute(certificateId)
        );

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        Assertions.assertEquals("Certificate not found", exception.getReason());
    }

    @Test
    void executeReturnsEmptyListWhenCertificateHasNoRecommendations() {
        Long certificateId = 1L;
        OfficialCertificate certificate = new OfficialCertificate();
        certificate.setRecommendations(List.of());

        Mockito.when(certificateRepository.findById(certificateId)).thenReturn(Optional.of(certificate));

        List<RecommendationDTO> result = useCase.execute(certificateId);

        Assertions.assertTrue(result.isEmpty());
    }
}
