package com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.CreateRecommendationUserCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GenerateRecommendationsUseCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.CreateRecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
@RequestMapping("/api/recommendation")
public class RecommendationPostController {
    private final CreateRecommendationUserCase createRecommendationUserCase;
    private final GenerateRecommendationsUseCase generateRecommendationsUseCase;

    public RecommendationPostController(CreateRecommendationUserCase createRecommendationUserCase,
                                        GenerateRecommendationsUseCase generateRecommendationsUseCase) {
        this.createRecommendationUserCase = createRecommendationUserCase;
        this.generateRecommendationsUseCase = generateRecommendationsUseCase;
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
}