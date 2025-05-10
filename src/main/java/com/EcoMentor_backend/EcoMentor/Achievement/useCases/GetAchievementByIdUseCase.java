package com.EcoMentor_backend.EcoMentor.Achievement.useCases;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.repositories.AchievementRepository;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.AchievementDTO;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.mapper.AchievementMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class GetAchievementByIdUseCase {

    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;

    public GetAchievementByIdUseCase(AchievementRepository achievementRepository, AchievementMapper achievementMapper) {
        this.achievementRepository = achievementRepository;
        this.achievementMapper = achievementMapper;
    }

    public AchievementDTO execute(Long achievementId) {
        Achievement achievement = achievementRepository.findByAchievementId(achievementId);
        if (achievement == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Achievement not found");
        }
        return achievementMapper.toDTO(achievement);
    }
}
