package com.EcoMentor_backend.EcoMentor.Achievements_User.useCases;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.repositories.AchievementRepository;
import com.EcoMentor_backend.EcoMentor.Achievements_User.entity.AchievementsUser;
import com.EcoMentor_backend.EcoMentor.Achievements_User.infrastructure.repositories.AchievementsUserRepository;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
public class AssignAllAchievementsToUserUseCase {

    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;
    private final AchievementsUserRepository achievementsUserRepository;

    public AssignAllAchievementsToUserUseCase(UserRepository userRepository,
                                              AchievementRepository achievementRepository,
                                              AchievementsUserRepository achievementsUserRepository) {
        this.userRepository = userRepository;
        this.achievementRepository = achievementRepository;
        this.achievementsUserRepository = achievementsUserRepository;
    }

    public void execute(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        List<Achievement> achievements = achievementRepository.findAll();

        for (Achievement achievement : achievements) {
            // Puedes validar si ya existe para evitar duplicados
            boolean alreadyExists = user.getProgresses().stream()
                    .anyMatch(p -> p.getAchievementProgress().getAchievementId()
                            .equals(achievement.getAchievementId()));
            if (!alreadyExists) {
                AchievementsUser progress = AchievementsUser.builder()
                        .userProgress(user)
                        .achievementProgress(achievement)
                        .progressStatus(0)
                        .build();

                achievementsUserRepository.save(progress);
            }
        }
    }
}
