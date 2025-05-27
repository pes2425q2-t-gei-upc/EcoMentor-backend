package com.EcoMentor_backend.EcoMentor.Achievement.useCases;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.repositories.AchievementRepository;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.AchievementDTO;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.mapper.AchievementMapper;
import com.EcoMentor_backend.EcoMentor.Achievements_User.entity.AchievementsUser;
import com.EcoMentor_backend.EcoMentor.Achievements_User.infrastructure.repositories.AchievementsUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetAllAchievementsUseCaseTest {

    private AchievementRepository achievementRepository;
    private AchievementMapper achievementMapper;
    private AchievementsUserRepository achievementsUserRepository;
    private GetAllAchievementsUseCase getAllAchievementsUseCase;

    @BeforeEach
    public void setUp() {
        achievementRepository = mock(AchievementRepository.class);
        achievementMapper = mock(AchievementMapper.class);
        achievementsUserRepository = mock(AchievementsUserRepository.class);
        getAllAchievementsUseCase = new GetAllAchievementsUseCase(
                achievementRepository, achievementMapper, achievementsUserRepository
        );
    }



    @Test
    public void testExecute_ReturnsEmptyListWhenNoAchievements() {
        when(achievementRepository.findAll()).thenReturn(Collections.emptyList());

        List<AchievementDTO> result = getAllAchievementsUseCase.execute(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(achievementRepository, times(1)).findAll();
        verifyNoInteractions(achievementMapper);
        verifyNoInteractions(achievementsUserRepository);
    }
}
