package com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.CalculateRecommendationValuesUseCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.CreateRecommendationUserCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GenerateRecommendationsUseCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GenerateZoneRecommendationsUseCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.CreateRecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.TotalValuesRecommendationDTO;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;






@RestController
@Validated
@RequestMapping("/api/recommendation")
public class RecommendationPostController {
    private final CreateRecommendationUserCase createRecommendationUserCase;
    private final GenerateRecommendationsUseCase generateRecommendationsUseCase;
    private final GenerateZoneRecommendationsUseCase generateZoneRecommendationsUseCase;
    private final CalculateRecommendationValuesUseCase calculateRecommendationValuesUseCase;

    public RecommendationPostController(CreateRecommendationUserCase createRecommendationUserCase,
                                        GenerateRecommendationsUseCase generateRecommendationsUseCase,
                                        GenerateZoneRecommendationsUseCase generateZoneRecommendationsUseCase,
                                        CalculateRecommendationValuesUseCase calculateRecommendationValuesUseCase) {
        this.createRecommendationUserCase = createRecommendationUserCase;
        this.generateRecommendationsUseCase = generateRecommendationsUseCase;
        this.generateZoneRecommendationsUseCase = generateZoneRecommendationsUseCase;
        this.calculateRecommendationValuesUseCase = calculateRecommendationValuesUseCase;
    }

    @PostMapping
    public ResponseEntity<Long> createRecommendation(@RequestBody @Validated
                                                         CreateRecommendationDTO recommendationDTO) {
        Long id = createRecommendationUserCase.execute(recommendationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PostMapping("/generate/{certificateId}")
    public ResponseEntity<List<RecommendationDTO>> generateRecommendations(@PathVariable Long certificateId) {
        List<RecommendationDTO> recommendations = generateRecommendationsUseCase.execute(certificateId);
        return ResponseEntity.ok(recommendations);
    }

    @PostMapping("/generate/zone/{certificateId}")
    public ResponseEntity<List<RecommendationDTO>> generateZoneRecommendations(
            @PathVariable Long certificateId,
            @RequestParam(name = "radius", defaultValue = "1") Integer radius) {
        List<RecommendationDTO> recommendations = generateZoneRecommendationsUseCase.execute(certificateId, radius);
        return ResponseEntity.ok(recommendations);
    }

    @PostMapping("/finalValues/{certificateId}")
    public ResponseEntity<TotalValuesRecommendationDTO> calculateValues(
            @RequestBody List<CreateRecommendationDTO> recommendationDTOs,
            @PathVariable Long certificateId) {

        TotalValuesRecommendationDTO result = calculateRecommendationValuesUseCase
                .calculateValues(recommendationDTOs, certificateId);

        return ResponseEntity.ok(result);
    }
}