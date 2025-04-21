package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.repositories.ChatRepository;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.mapper.ChatMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

class GetChatUseCaseTest {

private ChatRepository chatRepository;
private ChatMapper chatMapper;
private GetChatUseCase getChatUseCase;

@BeforeEach
void setUp() {
    chatRepository = mock(ChatRepository.class);
    chatMapper = mock(ChatMapper.class);
    getChatUseCase = new GetChatUseCase(chatRepository, chatMapper);
}



@Test
@DisplayName("Throws exception when no chats are found for the user and chat name")
void throwsExceptionWhenNoChatsFound() {
    when(chatRepository.findByUserIdAndChatName(1L, "Chat1")).thenReturn(List.of());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
            getChatUseCase.execute(1L, "Chat1")
    );

    assertEquals("No chats found for this user", exception.getReason());
}

@Test
@DisplayName("Handles null chat name gracefully")
void handlesNullChatNameGracefully() {
    when(chatRepository.findByUserIdAndChatName(1L, null)).thenReturn(List.of());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
            getChatUseCase.execute(1L, null)
    );

    assertEquals("No chats found for this user", exception.getReason());
}


}