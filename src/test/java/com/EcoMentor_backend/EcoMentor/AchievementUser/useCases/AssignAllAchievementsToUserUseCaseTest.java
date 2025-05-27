package com.EcoMentor_backend.EcoMentor.AchievementUser.useCases;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.repositories.AchievementRepository;
import com.EcoMentor_backend.EcoMentor.Achievements_User.entity.AchievementsUser;
import com.EcoMentor_backend.EcoMentor.Achievements_User.infrastructure.repositories.AchievementsUserRepository;
import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.AssignAllAchievementsToUserUseCase;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class AssignAllAchievementsToUserUseCaseTest {

    private UserRepository userRepository;
    private AchievementRepository achievementRepository;
    private AchievementsUserRepository achievementsUserRepository;
    private AssignAllAchievementsToUserUseCase assignAllAchievementsToUserUseCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        achievementRepository = mock(AchievementRepository.class);
        achievementsUserRepository = mock(AchievementsUserRepository.class);
        assignAllAchievementsToUserUseCase = new AssignAllAchievementsToUserUseCase(
                userRepository, achievementRepository, achievementsUserRepository
        );
    }

    @Test
    void shouldAssignAllAchievementsToUser() {
        // Arrange
        User user = new User();
        Achievement achievement1 = new Achievement();
        Achievement achievement2 = new Achievement();
        List<Achievement> achievements = Arrays.asList(achievement1, achievement2);

        when(achievementRepository.findAll()).thenReturn(achievements);

        // Act
        assignAllAchievementsToUserUseCase.execute(user);

        // Assert
        verify(achievementsUserRepository, times(2)).save(any(AchievementsUser.class));
    }
}
