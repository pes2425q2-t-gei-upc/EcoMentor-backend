package com.EcoMentor_backend.EcoMentor.Recommendation.useCases;

import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.repositories.RecommendationRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.CreateRecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.mapper.RecommendationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateRecommendationUserCase {
    private final RecommendationRepository recommendationRepository;
    private final RecommendationMapper recommendationMapper;


    public CreateRecommendationUserCase(RecommendationRepository recommendationRepository, RecommendationMapper recommendationMapper) {
        this.recommendationRepository = recommendationRepository;
        this.recommendationMapper = recommendationMapper;
    }

    public long execute(CreateRecommendationDTO recommendationDTO) {
        Recommendation recommendation = recommendationMapper.toEntity(recommendationDTO);
        recommendationRepository.save(recommendation);
        return recommendation.getRecommendationId();
    }
}
