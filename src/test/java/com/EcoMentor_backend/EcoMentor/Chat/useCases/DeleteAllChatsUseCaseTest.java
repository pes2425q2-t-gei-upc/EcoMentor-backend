package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.repositories.ChatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DeleteAllChatsUseCaseTest {

@Mock
private ChatRepository chatRepository;

@InjectMocks
private DeleteAllChatsUseCase deleteAllChatsUseCase;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}

@Test
void deletesAllChatsForExistingUser() {
    Long userId = 1L;

    deleteAllChatsUseCase.execute(userId);

    verify(chatRepository, times(1)).deleteByUserId(userId);
}

@Test
void doesNothingWhenDeletingChatsForNonExistentUser() {
    Long userId = 999L;

    deleteAllChatsUseCase.execute(userId);

    verify(chatRepository, times(1)).deleteByUserId(userId);
}
}
