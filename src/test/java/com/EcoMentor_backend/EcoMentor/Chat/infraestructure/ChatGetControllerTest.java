package com.EcoMentor_backend.EcoMentor.Chat.infraestructure;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.controllers.ChatGetController;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.GetChatNamesUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.GetChatUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

class ChatGetControllerTest {

private GetChatNamesUseCase getChatNamesUseCase;
private GetChatUseCase getChatUseCase;
private ChatGetController chatGetController;

@BeforeEach
void setUp() {
    getChatNamesUseCase = mock(GetChatNamesUseCase.class);
    getChatUseCase = mock(GetChatUseCase.class);
    chatGetController = new ChatGetController(getChatNamesUseCase, getChatUseCase);
}

@Test
@DisplayName("Returns chat names when user has chats")
void returnsChatNamesWhenUserHasChats() {
    ArrayList<String> chatNames = new ArrayList<>(List.of("Chat1", "Chat2"));
    when(getChatNamesUseCase.getChatNamesUser(1L)).thenReturn(chatNames);

    ResponseEntity<ArrayList<String>> response = chatGetController.getChatNamesUser(1L);

    assertEquals(chatNames, response.getBody());
    assertEquals(200, response.getStatusCodeValue());
}

@Test
@DisplayName("Returns empty list when user has no chats")
void returnsEmptyListWhenUserHasNoChats() {
    when(getChatNamesUseCase.getChatNamesUser(1L)).thenReturn(new ArrayList<>());

    ResponseEntity<ArrayList<String>> response = chatGetController.getChatNamesUser(1L);

    assertTrue(response.getBody().isEmpty());
    assertEquals(200, response.getStatusCodeValue());
}


@Test
@DisplayName("Returns empty list when no messages are found for the chat")
void returnsEmptyListWhenNoMessagesAreFoundForTheChat() {
    when(getChatUseCase.execute(1L, "Chat1")).thenReturn(List.of());

    ResponseEntity<List<ChatResponseDTO>> response = chatGetController.getChat(1L, "Chat1");

    assertTrue(response.getBody().isEmpty());
    assertEquals(200, response.getStatusCodeValue());
}
}