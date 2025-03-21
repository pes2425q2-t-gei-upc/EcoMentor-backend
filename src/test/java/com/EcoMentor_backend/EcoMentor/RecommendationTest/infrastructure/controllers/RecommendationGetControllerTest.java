package com.EcoMentor_backend.EcoMentor.RecommendationTest.infrastructure.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.controllers.RecommendationGetController;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GetAllRecommendationsUserCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GetRecommendationByIdUserCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;





public class RecommendationGetControllerTest {

    @Mock
    private GetRecommendationByIdUserCase getRecommendationByIdUserCase;

    @Mock
    private GetAllRecommendationsUserCase getAllRecommendationsUserCase;

    @InjectMocks
    private RecommendationGetController recommendationGetController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRecommendationsSuccessfully() {
        RecommendationDTO recommendationDTO = new RecommendationDTO();
        when(getAllRecommendationsUserCase.execute()).thenReturn(Collections.singletonList(recommendationDTO));

        ResponseEntity<List<RecommendationDTO>> response = recommendationGetController.getAllRecommendations();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getRecommendationByIdSuccessfully() {
        Long recommendationId = 1L;
        RecommendationDTO recommendationDTO = new RecommendationDTO();
        when(getRecommendationByIdUserCase.execute(recommendationId)).thenReturn(recommendationDTO);

        ResponseEntity<RecommendationDTO> response = recommendationGetController.getRecommendation(recommendationId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recommendationDTO, response.getBody());
    }

    @Test
    void getRecommendationByIdNotFound() {
        Long recommendationId = 1L;
        when(getRecommendationByIdUserCase.execute(recommendationId))
                .thenThrow(new RuntimeException("Recommendation not found"));

        try {
            recommendationGetController.getRecommendation(recommendationId);
        } catch (RuntimeException e) {
            assertEquals("Recommendation not found", e.getMessage());
        }
    }
}