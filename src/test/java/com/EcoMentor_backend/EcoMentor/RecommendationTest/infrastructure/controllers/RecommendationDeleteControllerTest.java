package com.EcoMentor_backend.EcoMentor.RecommendationTest.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.DeleteRecommendationUserCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.controllers.RecommendationDeleteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class RecommendationDeleteControllerTest {

    @Mock
    private DeleteRecommendationUserCase deleteRecommendationUserCase;

    @InjectMocks
    private RecommendationDeleteController recommendationDeleteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteRecommendationSuccessfully() {
        Long recommendationId = 1L;

        ResponseEntity<Void> response = recommendationDeleteController.deleteRecommendation(recommendationId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deleteRecommendationUserCase).execute(recommendationId);
    }

    @Test
    void deleteRecommendationNotFound() {
        Long recommendationId = 1L;
        doThrow(new RuntimeException("Recommendation not found")).when(deleteRecommendationUserCase).execute(recommendationId);

        try {
            recommendationDeleteController.deleteRecommendation(recommendationId);
        } catch (RuntimeException e) {
            assertEquals("Recommendation not found", e.getMessage());
        }
    }
}