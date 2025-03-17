package com.EcoMentor_backend.EcoMentor.RecommendationTest.useCases;

import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.repositories.RecommendationRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GetAllRecommendationsUserCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.mapper.RecommendationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GetAllRecommendationsUserCaseTest {

    @Mock
    private RecommendationRepository recommendationRepository;

    @Mock
    private RecommendationMapper recommendationMapper;

    @InjectMocks
    private GetAllRecommendationsUserCase getAllRecommendationsUserCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRecommendationsSuccessfully() {
        Recommendation recommendation = new Recommendation();
        RecommendationDTO recommendationDTO = new RecommendationDTO();
        when(recommendationRepository.findAll()).thenReturn(Collections.singletonList(recommendation));
        when(recommendationMapper.toDTO(recommendation)).thenReturn(recommendationDTO);

        List<RecommendationDTO> recommendations = getAllRecommendationsUserCase.execute();

        assertEquals(1, recommendations.size());
        assertEquals(recommendationDTO, recommendations.get(0));
    }

    @Test
    void getAllRecommendationsWhenNoneExist() {
        when(recommendationRepository.findAll()).thenReturn(Collections.emptyList());

        List<RecommendationDTO> recommendations = getAllRecommendationsUserCase.execute();

        assertEquals(0, recommendations.size());
    }
}