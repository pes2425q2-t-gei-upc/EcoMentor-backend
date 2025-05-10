package com.EcoMentor_backend.EcoMentor.Achievement.useCases;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.repositories.AchievementRepository;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.AchievementDTO;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.mapper.AchievementMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class GetAllAchievementsUseCase {
    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;

    public GetAllAchievementsUseCase(AchievementRepository achievementRepository,
                                     AchievementMapper achievementMapper) {
        this.achievementRepository = achievementRepository;
        this.achievementMapper = achievementMapper;
    }

    public List<AchievementDTO> execute() {
        List<Achievement> achievements = achievementRepository.findAll();
        List<AchievementDTO> achievementDTOList = new ArrayList<>();
        for (Achievement achievement : achievements) {
            achievementDTOList.add(achievementMapper.toDTO(achievement));
        }
        return achievementDTOList;
    }
}
