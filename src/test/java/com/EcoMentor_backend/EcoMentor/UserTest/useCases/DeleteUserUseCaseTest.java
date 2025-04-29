package com.EcoMentor_backend.EcoMentor.UserTest.useCases;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Chat.useCases.DeleteAllChatsUseCase;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.DeleteUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;


public class DeleteUserUseCaseTest {

@Mock
private UserRepository userRepository;

@Mock
private DeleteAllChatsUseCase deleteAllChatsUseCase;

@InjectMocks
private DeleteUserUseCase deleteUserUseCase;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}

@Test
void deletesUserAndAssociatedChatsSuccessfully() {
    Long userId = 1L;
    User user = new User();
    when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

    deleteUserUseCase.execute(userId);

    verify(userRepository, times(1)).delete(user);
    verify(deleteAllChatsUseCase, times(1)).execute(userId);
}

@Test
void throwsExceptionWhenDeletingNonExistentUser() {
    Long userId = 1L;
    when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

    assertThrows(ResponseStatusException.class, () -> deleteUserUseCase.execute(userId));
    verify(deleteAllChatsUseCase, times(0)).execute(userId);
}
}