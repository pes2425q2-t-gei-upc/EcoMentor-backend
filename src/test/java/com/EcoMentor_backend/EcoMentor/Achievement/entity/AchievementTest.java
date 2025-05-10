package com.EcoMentor_backend.EcoMentor.Achievement.entity;

import com.EcoMentor_backend.EcoMentor.Achievements_User.entity.AchievementsUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AchievementTest {

    @DisplayName("Returns achievement name correctly when valid data is provided")
    @Test
    void returnsAchievementNameCorrectlyWhenValidDataIsProvided() {
        Achievement achievement = Achievement.builder()
            .achievementId(1L)
            .achievementName("Eco Warrior")
            .build();

        assertEquals("Eco Warrior", achievement.getAchievementName());
    }


    @DisplayName("Returns empty progresses list when no progresses are associated")
    @Test
    void returnsEmptyProgressesListWhenNoProgressesAreAssociated() {
        Achievement achievement = Achievement.builder()
            .achievementId(1L)
            .achievementName("Eco Warrior")
            .progresses(Collections.emptyList())
            .build();

        assertTrue(achievement.getProgresses().isEmpty());
    }

    @DisplayName("Returns progresses list correctly when progresses are associated")
    @Test
    void returnsProgressesListCorrectlyWhenProgressesAreAssociated() {
        AchievementsUser progress1 = new AchievementsUser();
        AchievementsUser progress2 = new AchievementsUser();
        List<AchievementsUser> progresses = List.of(progress1, progress2);

        Achievement achievement = Achievement.builder()
            .achievementId(1L)
            .achievementName("Eco Warrior")
            .progresses(progresses)
            .build();

        assertEquals(2, achievement.getProgresses().size());
        assertEquals(progress1, achievement.getProgresses().get(0));
        assertEquals(progress2, achievement.getProgresses().get(1));
    }
}