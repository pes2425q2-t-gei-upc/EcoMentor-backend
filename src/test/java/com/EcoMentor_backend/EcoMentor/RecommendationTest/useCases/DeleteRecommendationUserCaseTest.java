package com.EcoMentor_backend.EcoMentor.RecommendationTest.useCases;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.repositories.RecommendationRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.DeleteRecommendationUserCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class DeleteRecommendationUserCaseTest {

    @Mock
    private RecommendationRepository recommendationRepository;

    @InjectMocks
    private DeleteRecommendationUserCase deleteRecommendationUserCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteExistingRecommendation() {
        Long recommendationId = 1L;
        Recommendation recommendation = new Recommendation();
        recommendation.setRecommendationId(recommendationId);

        when(recommendationRepository.findById(recommendationId)).thenReturn(java.util.Optional.of(recommendation));
        doNothing().when(recommendationRepository).delete(recommendation);

        deleteRecommendationUserCase.execute(recommendationId);
    }

    @Test
    void deleteNonExistingRecommendation() {
        Long recommendationId = 2L;

        when(recommendationRepository.findById(recommendationId)).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> deleteRecommendationUserCase.execute(recommendationId));
    }

    @Test
    void deleteRecommendationWithRepositoryException() {
        Long recommendationId = 3L;
        Recommendation recommendation = new Recommendation();
        recommendation.setRecommendationId(recommendationId);

        when(recommendationRepository.findById(recommendationId)).thenReturn(java.util.Optional.of(recommendation));
        doThrow(new RuntimeException("Repository exception")).when(recommendationRepository).delete(recommendation);

        assertThrows(RuntimeException.class, () -> deleteRecommendationUserCase.execute(recommendationId));
    }
}