package com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AchievementDTOTest {

    @DisplayName("Successfully creates DTO with valid data")
    @Test
    void successfullyCreatesDTOWithValidData() {
        AchievementDTO dto = new AchievementDTO(1L, "Eco Warrior");
        assertEquals(1L, dto.getAchievementId());
        assertEquals("Eco Warrior", dto.getAchievementName());
    }

    @DisplayName("Creates DTO with null values")
    @Test
    void createsDTOWithNullValues() {
        AchievementDTO dto = new AchievementDTO(null, null);
        assertNull(dto.getAchievementId());
        assertNull(dto.getAchievementName());
    }
}
