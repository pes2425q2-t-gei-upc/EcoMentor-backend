package com.EcoMentor_backend.EcoMentor.Achievements_User.useCases;


import com.EcoMentor_backend.EcoMentor.Achievements_User.entity.AchievementsUser;
import com.EcoMentor_backend.EcoMentor.Achievements_User.infrastructure.repositories.AchievementsUserRepository;
import com.EcoMentor_backend.EcoMentor.Shared.EmailService;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import jakarta.mail.MessagingException;
import java.io.IOException;
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
    private final EmailService emailService;
    private final UserRepository userRepository;

    public void execute(Long userId, Long achievementId) {
        AchievementsUser achivement = achievementsUserRepository
            .findByUserProgressIdAndAchievementProgressAchievementId(userId, achievementId);

        if (achivement == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User or Achievement not found");
        }

        if (achivement.getProgressStatus() < achivement.getAchievementProgress().getMaxProgress()) {
            achivement.setProgressStatus(achivement.getProgressStatus() + 1);
            achievementsUserRepository.save(achivement);

            if (achivement.getProgressStatus() == achivement.getAchievementProgress().getMaxProgress()) {
                User user = userRepository.findById(userId).orElseThrow();
                try {
                    emailService.sendHtmlEmail(user.getEmail(), "[ECOMENTOR] -  Achievement Unlocked",
                            "email/achievement.html");
                } catch (MessagingException | IOException e) {
                    System.out.println("Error sending email: " + e.getMessage());
                }
            }

        }
    }
}
