package com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.DeleteRecommendationUserCase;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/recommendation")
public class RecommendationDeleteController {
    private final DeleteRecommendationUserCase deleteRecommendationUserCase;

    public RecommendationDeleteController(DeleteRecommendationUserCase deleteRecommendationUserCase) {
        this.deleteRecommendationUserCase = deleteRecommendationUserCase;
    }

    @DeleteMapping("/{recommendationId}")
    public ResponseEntity<Void> deleteRecommendation(@PathVariable Long recommendationId) {
        deleteRecommendationUserCase.execute(recommendationId);
        return ResponseEntity.noContent().build();
    }
}