package com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.CalculateRecommendationValuesUseCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GetAllRecommendationsUserCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GetRecommendationByIdUserCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.CreateRecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.TotalValuesRecommendationDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
@RequestMapping("/api/recommendation")
public class RecommendationGetController {
    private final GetRecommendationByIdUserCase getRecommendationByIdUserCase;
    private final GetAllRecommendationsUserCase getAllRecommendationsUserCase;
    private final CalculateRecommendationValuesUseCase calculateRecommendationValuesUseCase;

    public RecommendationGetController(GetRecommendationByIdUserCase getRecommendationByIdUserCase,
                                       GetAllRecommendationsUserCase getAllRecommendationsUserCase,
                                       CalculateRecommendationValuesUseCase calculateRecommendationValuesUseCase) {
        this.getRecommendationByIdUserCase = getRecommendationByIdUserCase;
        this.getAllRecommendationsUserCase = getAllRecommendationsUserCase;
        this.calculateRecommendationValuesUseCase = calculateRecommendationValuesUseCase;
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

    @GetMapping("/finalValues/{certificateId}")
    public ResponseEntity<TotalValuesRecommendationDTO> calculateValues(
            @RequestBody List<CreateRecommendationDTO> recommendationDTOs,
            @PathVariable Long certificateId) {

        TotalValuesRecommendationDTO result = calculateRecommendationValuesUseCase
                .calculateValues(recommendationDTOs, certificateId);

        return ResponseEntity.ok(result);
    }
}