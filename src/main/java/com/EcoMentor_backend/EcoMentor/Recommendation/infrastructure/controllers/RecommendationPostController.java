package com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.CalculateRecommendationValuesUseCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.CreateRecommendationUserCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GenerateRecommendationsUseCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GenerateZoneRecommendationsUseCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.CreateRecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.TotalValuesRecommendationDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetUserIdByToken;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;






@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/api/recommendation")
public class RecommendationPostController {
    private final CreateRecommendationUserCase createRecommendationUserCase;
    private final GenerateRecommendationsUseCase generateRecommendationsUseCase;
    private final GenerateZoneRecommendationsUseCase generateZoneRecommendationsUseCase;
    private final CalculateRecommendationValuesUseCase calculateRecommendationValuesUseCase;
    private GetUserIdByToken getUserIdByToken;

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
            @PathVariable Long certificateId,
            HttpServletRequest request) {

        Long userId = getUserIdFromToken(request);

        TotalValuesRecommendationDTO result = calculateRecommendationValuesUseCase
                .calculateValues(recommendationDTOs, certificateId, userId);

        return ResponseEntity.ok(result);
    }

    private Long getUserIdFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        return getUserIdByToken.execute(token);
    }
}