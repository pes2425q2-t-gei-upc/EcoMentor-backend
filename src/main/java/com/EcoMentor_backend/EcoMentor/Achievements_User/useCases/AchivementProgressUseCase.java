package com.EcoMentor_backend.EcoMentor.Achievements_User.useCases;


import com.EcoMentor_backend.EcoMentor.Achievements_User.entity.AchievementsUser;
import com.EcoMentor_backend.EcoMentor.Achievements_User.infrastructure.repositories.AchievementsUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
@AllArgsConstructor
public class AchivementProgressUseCase {
    private final AchievementsUserRepository achievementsUserRepository;

    public void execute(Long userId, Long achievementId) {
        AchievementsUser achivement = achievementsUserRepository
            .findByUserProgressIdAndAchievementProgressAchievementId(userId, achievementId);

        if (achivement == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User or Achievement not found");
        }

        if (achivement.getProgressStatus() < achivement.getAchievementProgress().getMaxProgress()) {
            achivement.setProgressStatus(achivement.getProgressStatus() + 1);
            achievementsUserRepository.save(achivement);
        }
    }
}
