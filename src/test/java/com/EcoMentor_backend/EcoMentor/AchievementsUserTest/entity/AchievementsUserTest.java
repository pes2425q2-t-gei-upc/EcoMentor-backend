package com.EcoMentor_backend.EcoMentor.AchievementsUserTest.entity;

import com.EcoMentor_backend.EcoMentor.Achievements_User.entity.AchievementsUser;
import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AchievementsUserTest {

    private AchievementsUser achievementsUser;
    private Achievement achievement;
    private User user;

    @BeforeEach
    void setUp() {
        achievement = new Achievement();
        achievement.setAchievementId(1L);

        user = new User();
        user.setId(1L);

        achievementsUser = AchievementsUser.builder()
                .progressId(1L)
                .progressStatus("In Progress")
                .achievementProgress(achievement)
                .userProgress(user)
                .build();
    }

    @Test
    @DisplayName("Should create AchievementsUser with valid data")
    void shouldCreateAchievementsUserWithValidData() {
        assertNotNull(achievementsUser);
        assertEquals(1L, achievementsUser.getProgressId());
        assertEquals("In Progress", achievementsUser.getProgressStatus());
        assertEquals(achievement, achievementsUser.getAchievementProgress());
        assertEquals(user, achievementsUser.getUserProgress());
    }

    @Test
    @DisplayName("Should update progress status")
    void shouldUpdateProgressStatus() {
        achievementsUser.setProgressStatus("Completed");
        assertEquals("Completed", achievementsUser.getProgressStatus());
    }

    @Test
    @DisplayName("Should handle null achievement")
    void shouldHandleNullAchievement() {
        achievementsUser.setAchievementProgress(null);
        assertEquals(null, achievementsUser.getAchievementProgress());
    }

    @Test
    @DisplayName("Should handle null user")
    void shouldHandleNullUser() {
        achievementsUser.setUserProgress(null);
        assertEquals(null, achievementsUser.getUserProgress());
    }
}