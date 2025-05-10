package com.EcoMentor_backend.EcoMentor.Achievement.useCases;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.repositories.AchievementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteAchievementUseCase {
    private final AchievementRepository achievementRepository;

    public DeleteAchievementUseCase(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    public void execute(Long id) {
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Achievement not found"));
        achievementRepository.delete(achievement);
        System.out.println("Achievement with " + id + " has been deleted");
    }
}
