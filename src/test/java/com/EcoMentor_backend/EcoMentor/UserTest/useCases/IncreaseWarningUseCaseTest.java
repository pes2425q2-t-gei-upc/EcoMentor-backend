package com.EcoMentor_backend.EcoMentor.UserTest.useCases;

import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.IncreaseWarningUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IncreaseWarningUseCaseTest {

    private UserRepository userRepository;
    private IncreaseWarningUseCase increaseWarningUseCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        increaseWarningUseCase = new IncreaseWarningUseCase(userRepository);
    }

    @Test
    void shouldIncreaseUserWarnings() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setWarnings(2);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        int result = increaseWarningUseCase.execute(userId);

        // Then
        assertEquals(3, result); // Verifica que devuelve el nuevo valor

        // Verifica que se actualizó el valor internamente
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertEquals(3, userCaptor.getValue().getWarnings());

        // Verifica que se llamó a findById
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowExceptionIfUserNotFound() {
        // Given
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Then
        org.junit.jupiter.api.Assertions.assertThrows(
                java.util.NoSuchElementException.class,
                () -> increaseWarningUseCase.execute(userId)
        );

        verify(userRepository, never()).save(any());
    }
}
