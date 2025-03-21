package com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.repositories;



import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    Recommendation findByRecommendationId(Long recommendationId);

    List<Recommendation> findAll();
}
