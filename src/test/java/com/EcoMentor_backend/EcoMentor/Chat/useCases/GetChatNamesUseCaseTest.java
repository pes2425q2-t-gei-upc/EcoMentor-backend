package com.EcoMentor_backend.EcoMentor.Chat.useCases;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.EcoMentor_backend.EcoMentor.Chat.entity.Chat;
import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.repositories.ChatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

class GetChatNamesUseCaseTest {

private ChatRepository chatRepository;
private GetChatNamesUseCase getChatNamesUseCase;

@BeforeEach
void setUp() {
    chatRepository = mock(ChatRepository.class);
    getChatNamesUseCase = new GetChatNamesUseCase(chatRepository);
}

@Test
@DisplayName("Returns unique chat names when user has multiple chats")
void returnsUniqueChatNamesWhenUserHasMultipleChats() {
    when(chatRepository.findByUserId(1L)).thenReturn(List.of(
            Chat.builder().chatName("Chat1").build(),
            Chat.builder().chatName("Chat2").build(),
            Chat.builder().chatName("Chat1").build()
    ));

    List<String> result = getChatNamesUseCase.getChatNamesUser(1L);

    assertEquals(2, result.size());
    assertTrue(result.contains("Chat1"));
    assertTrue(result.contains("Chat2"));
}

@Test
@DisplayName("Throws exception when user has no chats")
void throwsExceptionWhenUserHasNoChats() {
    when(chatRepository.findByUserId(1L)).thenReturn(List.of());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
            getChatNamesUseCase.getChatNamesUser(1L)
    );

    assertEquals("No chats found for this user", exception.getReason());
}

@Test
@DisplayName("Handles large number of chats efficiently")
void handlesLargeNumberOfChatsEfficiently() {
    List<Chat> largeChatList = List.of(
            Chat.builder().chatName("Chat1").build(),
            Chat.builder().chatName("Chat2").build(),
            Chat.builder().chatName("Chat3").build()
    );
    when(chatRepository.findByUserId(1L)).thenReturn(largeChatList);

    List<String> result = getChatNamesUseCase.getChatNamesUser(1L);

    assertEquals(3, result.size());
    assertTrue(result.contains("Chat1"));
    assertTrue(result.contains("Chat2"));
    assertTrue(result.contains("Chat3"));
}
}