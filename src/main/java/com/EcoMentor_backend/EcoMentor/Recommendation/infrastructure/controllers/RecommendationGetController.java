package com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GetAllRecommendationsUserCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GetRecommendationByIdUserCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
@RequestMapping("/api/recommendation")
public class RecommendationGetController {
    private final GetRecommendationByIdUserCase getRecommendationByIdUserCase;
    private final GetAllRecommendationsUserCase getAllRecommendationsUserCase;

    public RecommendationGetController(GetRecommendationByIdUserCase getRecommendationByIdUserCase,
                                       GetAllRecommendationsUserCase getAllRecommendationsUserCase) {
        this.getRecommendationByIdUserCase = getRecommendationByIdUserCase;
        this.getAllRecommendationsUserCase = getAllRecommendationsUserCase;
    }

    @GetMapping
    public ResponseEntity<List<RecommendationDTO>> getAllRecommendations() {
        List<RecommendationDTO> recommendations = getAllRecommendationsUserCase.execute();
        return ResponseEntity.ok(recommendations);
    }

    @GetMapping("/{recommendationId}")
    public ResponseEntity<RecommendationDTO> getRecommendation(@PathVariable Long recommendationId) {
        RecommendationDTO recommendation = getRecommendationByIdUserCase.execute(recommendationId);
        return ResponseEntity.ok(recommendation);
    }
}