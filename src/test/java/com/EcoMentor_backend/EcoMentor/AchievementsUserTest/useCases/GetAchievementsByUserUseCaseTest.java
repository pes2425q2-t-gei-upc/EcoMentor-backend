package com.EcoMentor_backend.EcoMentor.AchievementsUserTest.useCases;

        import com.EcoMentor_backend.EcoMentor.Achievements_User.infrastructure.repositories.AchievementsUserRepository;
        import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.GetAchievementsByUserUseCase;
        import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.dto.AchievementsUserDTO;
        import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.mapper.AchievementsUserMapper;
        import com.EcoMentor_backend.EcoMentor.Achievements_User.entity.AchievementsUser;
        import com.EcoMentor_backend.EcoMentor.User.entity.User;
        import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
        import org.junit.jupiter.api.DisplayName;
        import org.junit.jupiter.api.Test;
        import org.mockito.Mockito;
        import org.springframework.web.server.ResponseStatusException;

        import java.util.Collections;
        import java.util.List;
        import java.util.Optional;

        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.junit.jupiter.api.Assertions.assertThrows;
        import static org.mockito.Mockito.*;

        class GetAchievementsByUserUseCaseTest {

            private final UserRepository userRepository = Mockito.mock(UserRepository.class);
            private final AchievementsUserRepository achievementsUserRepository = Mockito.mock(AchievementsUserRepository.class);
            private final AchievementsUserMapper achievementsUserMapper = Mockito.mock(AchievementsUserMapper.class);
            private final GetAchievementsByUserUseCase useCase = new GetAchievementsByUserUseCase(userRepository, achievementsUserRepository, achievementsUserMapper);

            @Test
            @DisplayName("Should return achievements for a valid user")
            void shouldReturnAchievementsForValidUser() {
                User user = new User();
                user.setId(1L);

                AchievementsUser achievementsUser = new AchievementsUser();
                AchievementsUserDTO dto = AchievementsUserDTO.builder()
                        .progressId(1L)
                        .progressStatus("Completed")
                        .achievementId(2L)
                        .achievementName("Achievement Name")
                        .build();

                when(userRepository.findById(1L)).thenReturn(Optional.of(user));
                when(achievementsUserRepository.findByUserProgress(user)).thenReturn(Collections.singletonList(achievementsUser));
                when(achievementsUserMapper.toDTO(achievementsUser)).thenReturn(dto);

                List<AchievementsUserDTO> result = useCase.execute(1L);

                assertEquals(1, result.size());
                assertEquals("Achievement Name", result.get(0).getAchievementName());
            }

            @Test
            @DisplayName("Should throw exception when user is not found")
            void shouldThrowExceptionWhenUserIsNotFound() {
                when(userRepository.findById(1L)).thenReturn(Optional.empty());

                assertThrows(ResponseStatusException.class, () -> useCase.execute(1L));
            }

            @Test
            @DisplayName("Should return empty list when user has no achievements")
            void shouldReturnEmptyListWhenUserHasNoAchievements() {
                User user = new User();
                user.setId(1L);

                when(userRepository.findById(1L)).thenReturn(Optional.of(user));
                when(achievementsUserRepository.findByUserProgress(user)).thenReturn(Collections.emptyList());

                List<AchievementsUserDTO> result = useCase.execute(1L);

                assertEquals(0, result.size());
            }
        }