package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.EcoMentor_backend.EcoMentor.Chat.entity.Chat;
import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.repositories.ChatRepository;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

class ChatUseCaseTest {

private GeminiService geminiService;
private ChatRepository chatRepository;
private UserRepository userRepository;
private ChatUseCase chatUseCase;

@BeforeEach
void setUp() {
    geminiService = mock(GeminiService.class);
    chatRepository = mock(ChatRepository.class);
    userRepository = mock(UserRepository.class);
    chatUseCase = new ChatUseCase(geminiService, chatRepository, userRepository);
}

@Test
@DisplayName("Returns response when user exists and message is processed successfully")
void returnsResponseWhenUserExistsAndMessageProcessedSuccessfully() {
    when(userRepository.existsById(1L)).thenReturn(true);
    when(chatRepository.findByUserIdAndChatNameOrderByTimestampAsc(1L, "chat1"))
            .thenReturn(List.of());
    when(geminiService.generateContent(anyList())).thenReturn("Generated response");

    ChatResponseDTO result = chatUseCase.execute("Hello", 1L, "chat1", LocalDateTime.now());

    assertEquals("Hello", result.getMessage());
    assertEquals("Generated response", result.getResponse());
}

@Test
@DisplayName("Throws exception when user does not exist")
void throwsExceptionWhenUserDoesNotExist() {
    when(userRepository.existsById(1L)).thenReturn(false);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
            chatUseCase.execute("Hello", 1L, "chat1", LocalDateTime.now())
    );

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertEquals("User not found", exception.getReason());
}

@Test
@DisplayName("Throws exception when response generation fails")
void throwsExceptionWhenResponseGenerationFails() {
    when(userRepository.existsById(1L)).thenReturn(true);
    when(chatRepository.findByUserIdAndChatNameOrderByTimestampAsc(1L, "chat1"))
            .thenReturn(List.of());
    when(geminiService.generateContent(anyList())).thenReturn("");

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
            chatUseCase.execute("Hello", 1L, "chat1", LocalDateTime.now())
    );

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
    assertEquals("Error generating response", exception.getReason());
}

@Test
@DisplayName("Processes chat history and appends new message")
void processesChatHistoryAndAppendsNewMessage() {
    when(userRepository.existsById(1L)).thenReturn(true);
    when(chatRepository.findByUserIdAndChatNameOrderByTimestampAsc(1L, "chat1"))
            .thenReturn(List.of(
                    Chat.builder().message("Hi").response("Hello").build()
            ));
    when(geminiService.generateContent(anyList())).thenReturn("New response");

    ChatResponseDTO result = chatUseCase.execute("How are you?", 1L, "chat1", LocalDateTime.now());

    assertEquals("How are you?", result.getMessage());
    assertEquals("New response", result.getResponse());
}
}