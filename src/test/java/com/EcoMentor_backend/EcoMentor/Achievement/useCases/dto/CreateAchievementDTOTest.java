package com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateAchievementDTOTest {

    @DisplayName("Successfully creates DTO with valid data")
    @Test
    void successfullyCreatesDTOWithValidData() {
        CreateAchievementDTO dto = new CreateAchievementDTO("Eco Warrior");
        assertEquals("Eco Warrior", dto.getAchievementName());
    }

}