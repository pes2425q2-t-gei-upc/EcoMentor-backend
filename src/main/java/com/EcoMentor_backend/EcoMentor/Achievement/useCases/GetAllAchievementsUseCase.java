package com.EcoMentor_backend.EcoMentor.Achievement.useCases;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.repositories.AchievementRepository;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.AchievementDTO;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.mapper.AchievementMapper;
import com.EcoMentor_backend.EcoMentor.Achievements_User.entity.AchievementsUser;
import com.EcoMentor_backend.EcoMentor.Achievements_User.infrastructure.repositories.AchievementsUserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@AllArgsConstructor
public class GetAllAchievementsUseCase {
    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;
    private final AchievementsUserRepository achievementsUserRepository;


    public List<AchievementDTO> execute(long userId) {
        List<Achievement> achievements = achievementRepository.findAll();
        List<AchievementDTO> achievementDTOList = new ArrayList<>();
        for (Achievement achievement : achievements) {
            AchievementDTO achievementDTO = achievementMapper.toDTO(achievement);

            AchievementsUser achievementsUser = achievementsUserRepository
                .findByUserProgressIdAndAchievementProgressAchievementId(userId, achievement.getAchievementId());

            achievementDTO.setActualProgress(achievementsUser.getProgressStatus());
            achievementDTOList.add(achievementDTO);
        }
        return achievementDTOList;
    }
}
