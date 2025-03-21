package com.EcoMentor_backend.EcoMentor.Recommendation.useCases;

import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.repositories.RecommendationRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.mapper.RecommendationMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class GetAllRecommendationsUserCase {
    private final RecommendationRepository recommendationRepository;
    private final RecommendationMapper recommendationMapper;

    public GetAllRecommendationsUserCase(RecommendationRepository recommendationRepository,
                                         RecommendationMapper recommendationMapper) {
        this.recommendationRepository = recommendationRepository;
        this.recommendationMapper = recommendationMapper;
    }

    public List<RecommendationDTO> execute() {
        List<Recommendation> recommendations = recommendationRepository.findAll();
        List<RecommendationDTO> listRecommendationDTO = new ArrayList<>();
        for (Recommendation r : recommendations) {
            listRecommendationDTO.add(recommendationMapper.toDTO(r));
        }
        return listRecommendationDTO;
    }
}

