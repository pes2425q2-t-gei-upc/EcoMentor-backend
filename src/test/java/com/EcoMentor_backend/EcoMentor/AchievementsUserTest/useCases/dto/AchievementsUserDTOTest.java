package com.EcoMentor_backend.EcoMentor.AchievementsUserTest.useCases.dto;

import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.dto.AchievementsUserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AchievementsUserDTOTest {

    @Test
    @DisplayName("Should create AchievementsUserDTO with valid data")
    void shouldCreateAchievementsUserDTOWithValidData() {
        AchievementsUserDTO dto = AchievementsUserDTO.builder()
                .progressId(1L)
                .progressStatus("Completed")
                .achievementId(2L)
                .achievementName("Achievement Name")
                .build();

        assertNotNull(dto);
        assertEquals(1L, dto.getProgressId());
        assertEquals("Completed", dto.getProgressStatus());
        assertEquals(2L, dto.getAchievementId());
        assertEquals("Achievement Name", dto.getAchievementName());
    }

    @Test
    @DisplayName("Should handle null values in AchievementsUserDTO")
    void shouldHandleNullValuesInAchievementsUserDTO() {
        AchievementsUserDTO dto = AchievementsUserDTO.builder()
                .progressId(null)
                .progressStatus(null)
                .achievementId(null)
                .achievementName(null)
                .build();

        assertNotNull(dto);
        assertEquals(null, dto.getProgressId());
        assertEquals(null, dto.getProgressStatus());
        assertEquals(null, dto.getAchievementId());
        assertEquals(null, dto.getAchievementName());
    }

    @Test
    @DisplayName("Should update fields in AchievementsUserDTO")
    void shouldUpdateFieldsInAchievementsUserDTO() {
        AchievementsUserDTO dto = new AchievementsUserDTO();
        dto.setProgressId(3L);
        dto.setProgressStatus("In Progress");
        dto.setAchievementId(4L);
        dto.setAchievementName("Updated Achievement");

        assertEquals(3L, dto.getProgressId());
        assertEquals("In Progress", dto.getProgressStatus());
        assertEquals(4L, dto.getAchievementId());
        assertEquals("Updated Achievement", dto.getAchievementName());
    }
}