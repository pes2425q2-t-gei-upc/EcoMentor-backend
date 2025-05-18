package com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.repositories;


import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    Achievement findByAchievementId(Long achievementId);

    Optional<Achievement> findByAchievementName(String achievementName);


    List<Achievement> findAll();


}
