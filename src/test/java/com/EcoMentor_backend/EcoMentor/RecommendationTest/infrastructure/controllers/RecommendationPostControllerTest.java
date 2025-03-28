package com.EcoMentor_backend.EcoMentor.RecommendationTest.infrastructure.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.controllers.RecommendationPostController;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.CreateRecommendationUserCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.CreateRecommendationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class RecommendationPostControllerTest {

    @Mock
    private CreateRecommendationUserCase createRecommendationUserCase;

    @InjectMocks
    private RecommendationPostController recommendationPostController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRecommendationSuccessfully() {
        CreateRecommendationDTO createRecommendationDTO = new CreateRecommendationDTO();
        Long recommendationId = 1L;
        when(createRecommendationUserCase.execute(createRecommendationDTO)).thenReturn(recommendationId);

        ResponseEntity<Long> response = recommendationPostController.createRecommendation(createRecommendationDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(recommendationId, response.getBody());
    }

    @Test
    void createRecommendationWithInvalidData() {
        CreateRecommendationDTO createRecommendationDTO = new CreateRecommendationDTO();
        when(createRecommendationUserCase.execute(createRecommendationDTO))
                .thenThrow(new RuntimeException("Invalid data"));

        try {
            recommendationPostController.createRecommendation(createRecommendationDTO);
        } catch (RuntimeException e) {
            assertEquals("Invalid data", e.getMessage());
        }
    }
}