package com.EcoMentor_backend.EcoMentor.RecommendationTest.useCases;

import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.repositories.RecommendationRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GetRecommendationByIdUserCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.mapper.RecommendationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class GetRecommendationByIdUserCaseTest {

    @Mock
    private RecommendationRepository recommendationRepository;

    @Mock
    private RecommendationMapper recommendationMapper;

    @InjectMocks
    private GetRecommendationByIdUserCase getRecommendationByIdUserCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRecommendationByIdSuccessfully() {
        Long recommendationId = 1L;
        Recommendation recommendation = new Recommendation();
        recommendation.setRecommendationId(recommendationId);
        RecommendationDTO recommendationDTO = new RecommendationDTO();
        recommendationDTO.setRecommendationId(recommendationId);

        when(recommendationRepository.findByRecommendationId(recommendationId)).thenReturn(recommendation);
        when(recommendationMapper.toDTO(recommendation)).thenReturn(recommendationDTO);

        RecommendationDTO result = getRecommendationByIdUserCase.execute(recommendationId);

        assertEquals(recommendationDTO, result);
    }

    @Test
    void getRecommendationByIdNotFound() {
        Long recommendationId = 2L;

        when(recommendationRepository.findByRecommendationId(recommendationId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> getRecommendationByIdUserCase.execute(recommendationId));
    }
}