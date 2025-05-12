package com.EcoMentor_backend.EcoMentor.AchievementsUserTest.useCases;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.repositories.AchievementRepository;
import com.EcoMentor_backend.EcoMentor.Achievements_User.entity.AchievementsUser;
import com.EcoMentor_backend.EcoMentor.Achievements_User.infrastructure.repositories.AchievementsUserRepository;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.AssignAllAchievementsToUserUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AssignAllAchievementsToUserUseCaseTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final AchievementRepository achievementRepository = Mockito.mock(AchievementRepository.class);
    private final AchievementsUserRepository achievementsUserRepository = Mockito.mock(AchievementsUserRepository.class);
    private final AssignAllAchievementsToUserUseCase useCase = new AssignAllAchievementsToUserUseCase(userRepository, achievementRepository, achievementsUserRepository);

    @Test
    @DisplayName("Should assign all achievements to user when user and achievements exist")
    void shouldAssignAllAchievementsToUserWhenUserAndAchievementsExist() {
        User user = new User();
        user.setId(1L);
        user.setProgresses(Collections.emptyList());

        Achievement achievement = new Achievement();
        achievement.setAchievementId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(achievementRepository.findAll()).thenReturn(List.of(achievement));

        useCase.execute(1L);

        verify(achievementsUserRepository, times(1)).save(any(AchievementsUser.class));
    }

    @Test
    @DisplayName("Should throw exception when user does not exist")
    void shouldThrowExceptionWhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> useCase.execute(1L));
    }

    @Test
    @DisplayName("Should not assign duplicate achievements to user")
    void shouldNotAssignDuplicateAchievementsToUser() {
        User user = new User();
        user.setId(1L);

        Achievement achievement = new Achievement();
        achievement.setAchievementId(1L);

        AchievementsUser existingProgress = AchievementsUser.builder()
                .userProgress(user)
                .achievementProgress(achievement)
                .progressStatus("0")
                .build();

        user.setProgresses(List.of(existingProgress));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(achievementRepository.findAll()).thenReturn(List.of(achievement));

        useCase.execute(1L);

        verify(achievementsUserRepository, never()).save(any(AchievementsUser.class));
    }

    @Test
    @DisplayName("Should handle empty achievements list")
    void shouldHandleEmptyAchievementsList() {
        User user = new User();
        user.setId(1L);
        user.setProgresses(Collections.emptyList());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(achievementRepository.findAll()).thenReturn(Collections.emptyList());

        useCase.execute(1L);

        verify(achievementsUserRepository, never()).save(any(AchievementsUser.class));
    }
}