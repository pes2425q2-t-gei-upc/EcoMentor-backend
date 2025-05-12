package com.EcoMentor_backend.EcoMentor.AchievementsUserTest.useCases.mapper;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievements_User.entity.AchievementsUser;
import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.dto.AchievementsUserDTO;
import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.dto.CreateAchievementsUserDTO;
import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.mapper.AchievementsUserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AchievementsUserMapperTest {

    private final AchievementsUserMapper mapper = new AchievementsUserMapper();

    @Test
    @DisplayName("Should map AchievementsUser to AchievementsUserDTO correctly")
    void shouldMapAchievementsUserToDTOCorrectly() {
        AchievementsUser achievementsUser = AchievementsUser.builder()
                .progressId(1L)
                .progressStatus("In Progress")
                .achievementProgress(Achievement.builder().achievementId(2L).achievementName("Test Achievement").build())
                .build();

        AchievementsUserDTO dto = mapper.toDTO(achievementsUser);

        assertNotNull(dto);
        assertEquals(1L, dto.getProgressId());
        assertEquals("In Progress", dto.getProgressStatus());
        assertEquals(2L, dto.getAchievementId());
        assertEquals("Test Achievement", dto.getAchievementName());
    }

    @Test
    @DisplayName("Should map CreateAchievementsUserDTO to AchievementsUser correctly")
    void shouldMapCreateAchievementsUserDTOToEntityCorrectly() {
        CreateAchievementsUserDTO createDTO = CreateAchievementsUserDTO.builder()
                .progressStatus("Completed")
                .build();

        AchievementsUser entity = mapper.toEntity(createDTO);

        assertNotNull(entity);
        assertEquals("Completed", entity.getProgressStatus());
    }
}