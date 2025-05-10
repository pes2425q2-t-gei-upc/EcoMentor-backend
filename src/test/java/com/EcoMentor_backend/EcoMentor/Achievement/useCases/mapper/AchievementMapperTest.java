package com.EcoMentor_backend.EcoMentor.Achievement.useCases.mapper;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.AchievementDTO;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.CreateAchievementDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AchievementMapperTest {

    @DisplayName("Maps entity to DTO successfully")
    @Test
    void mapsEntityToDTOSuccessfully() {
        Achievement achievement = Achievement.builder()
                .achievementId(1L)
                .achievementName("Eco Warrior")
                .build();

        AchievementMapper mapper = new AchievementMapper();
        AchievementDTO dto = mapper.toDTO(achievement);

        assertEquals(1L, dto.getAchievementId());
        assertEquals("Eco Warrior", dto.getAchievementName());
    }

    @DisplayName("Maps DTO to entity successfully")
    @Test
    void mapsDTOToEntitySuccessfully() {
        CreateAchievementDTO createAchievementDTO = CreateAchievementDTO.builder()
                .achievementName("Eco Warrior")
                .build();

        AchievementMapper mapper = new AchievementMapper();
        Achievement entity = mapper.toEntity(createAchievementDTO);

        assertNull(entity.getAchievementId());
        assertEquals("Eco Warrior", entity.getAchievementName());
    }

}