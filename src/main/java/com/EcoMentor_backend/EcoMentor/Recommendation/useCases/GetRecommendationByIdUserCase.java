package com.EcoMentor_backend.EcoMentor.Recommendation.useCases;

import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.repositories.RecommendationRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.mapper.RecommendationMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class GetRecommendationByIdUserCase {
    private final RecommendationRepository recommendationRepository;
    private final RecommendationMapper recommendationMapper;

    public GetRecommendationByIdUserCase(RecommendationRepository recommendationRepository,
                                         RecommendationMapper recommendationMapper) {
        this.recommendationRepository = recommendationRepository;
        this.recommendationMapper = recommendationMapper;
    }

    public RecommendationDTO execute(Long recommendationId) {
        Recommendation recommendation = recommendationRepository.findByRecommendationId(recommendationId);
        if (recommendation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recommendation not found");
        }
        return recommendationMapper.toDTO(recommendation);
    }
}