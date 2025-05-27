package com.EcoMentor_backend.EcoMentor.Achievement.useCases;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.repositories.AchievementRepository;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.CreateAchievementDTO;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.mapper.AchievementMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateAchievementUseCase {
    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;


    public CreateAchievementUseCase(AchievementRepository achievementRepository, AchievementMapper achievementMapper) {
        this.achievementRepository = achievementRepository;
        this.achievementMapper = achievementMapper;
    }

    public long execute(CreateAchievementDTO achievementDTO) {
        Achievement achievement = achievementMapper.toEntity(achievementDTO);
        achievementRepository.save(achievement);
        return achievement.getAchievementId();
    }
}