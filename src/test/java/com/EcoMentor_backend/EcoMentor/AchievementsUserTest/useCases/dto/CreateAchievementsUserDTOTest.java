package com.EcoMentor_backend.EcoMentor.AchievementsUserTest.useCases.dto;

import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.dto.CreateAchievementsUserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateAchievementsUserDTOTest {

    @Test
    @DisplayName("Should create CreateAchievementsUserDTO with valid data")
    void shouldCreateCreateAchievementsUserDTOWithValidData() {
        CreateAchievementsUserDTO dto = CreateAchievementsUserDTO.builder()
                .progressStatus("In Progress")
                .build();

        assertNotNull(dto);
        assertEquals("In Progress", dto.getProgressStatus());
    }

    @Test
    @DisplayName("Should handle null progress status")
    void shouldHandleNullProgressStatus() {
        CreateAchievementsUserDTO dto = CreateAchievementsUserDTO.builder()
                .progressStatus(null)
                .build();

        assertNotNull(dto);
        assertEquals(null, dto.getProgressStatus());
    }

    @Test
    @DisplayName("Should update progress status")
    void shouldUpdateProgressStatus() {
        CreateAchievementsUserDTO dto = new CreateAchievementsUserDTO();
        dto.setProgressStatus("Completed");

        assertEquals("Completed", dto.getProgressStatus());
    }
}