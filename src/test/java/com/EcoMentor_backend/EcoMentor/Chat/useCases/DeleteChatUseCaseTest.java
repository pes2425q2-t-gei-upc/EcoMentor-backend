package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import com.EcoMentor_backend.EcoMentor.Chat.entity.Chat;
import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.repositories.ChatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteChatUseCaseTest {

@Mock
private ChatRepository chatRepository;

@InjectMocks
private DeleteChatUseCase useCase;

private final Long USER_ID = 42L;
private final String CHAT_NAME = "myChat";

@Test
void whenNoChatFound_thenThrowsNotFound() {
    // dado: repositorio devuelve lista vacía
    when(chatRepository.findByUserIdAndChatName(USER_ID, CHAT_NAME))
            .thenReturn(Collections.emptyList());

    // when + then
    ResponseStatusException ex = catchThrowableOfType(
            () -> useCase.execute(USER_ID, CHAT_NAME),
            ResponseStatusException.class
    );

    assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(ex.getReason()).isEqualTo("Chat not found");

    // no debe llamar al delete
    verify(chatRepository, never()).deleteByUserIdAndChatName(anyLong(), anyString());
}

@Test
void whenChatExists_thenDeletesWithoutException() {
    // dado: repositorio devuelve lista con un Chat simulado
    Chat fakeChat = mock(Chat.class);
    when(chatRepository.findByUserIdAndChatName(USER_ID, CHAT_NAME))
            .thenReturn(List.of(fakeChat));

    // when
    useCase.execute(USER_ID, CHAT_NAME);

    // then: delete debe haberse invocado una vez
    verify(chatRepository, times(1)).deleteByUserIdAndChatName(USER_ID, CHAT_NAME);
}
}
