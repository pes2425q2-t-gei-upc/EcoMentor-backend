package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.EcoMentor_backend.EcoMentor.Chat.entity.Chat;
import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.repositories.ChatRepository;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import com.EcoMentor_backend.EcoMentor.Shared.EmailService;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.IncreaseWarningUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

class ChatUseCaseTest {

private GeminiService geminiService;
private ChatRepository chatRepository;
private UserRepository userRepository;
private ChatUseCase chatUseCase;
private IncreaseWarningUseCase increaseWarningUseCase;
private EmailService emailService;

@BeforeEach
void setUp() {
    geminiService = mock(GeminiService.class);
    chatRepository = mock(ChatRepository.class);
    userRepository = mock(UserRepository.class);
    increaseWarningUseCase = mock(IncreaseWarningUseCase.class);
    chatUseCase = new ChatUseCase(geminiService, chatRepository, userRepository, increaseWarningUseCase, emailService);
}

    @Test
    @DisplayName("Returns response when user exists and message is processed successfully")
    void returnsResponseWhenUserExistsAndMessageProcessedSuccessfully() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(chatRepository.findByUserIdAndChatNameOrderByTimestampAsc(1L, "chat1"))
                .thenReturn(List.of());
        when(geminiService.generateContent(anyList())).thenReturn("Generated response");

        ChatResponseDTO result = chatUseCase.execute("Hello", 1L, "chat1");

        assertEquals("Hello", result.getMessage());
        assertEquals("Generated response", result.getResponse());
    }

    @Test
    @DisplayName("Throws exception when user does not exist")
    void throwsExceptionWhenUserDoesNotExist() {
        when(userRepository.existsById(1L)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                chatUseCase.execute("Hello", 1L, "chat1")
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
                chatUseCase.execute("Hello", 1L, "chat1")
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Error generating response", exception.getReason());
    }

    @Nested
    @DisplayName("When there is chat history")
    class ChatHistoryTests {
        private Date now;

        @BeforeEach
        void initHistory() {
            now = new Date();
        }

        @Test
        @DisplayName("Processes chat history and appends new message")
        void processesChatHistoryAndAppendsNewMessage() {
            when(userRepository.existsById(1L)).thenReturn(true);
            Chat prev = Chat.builder()
                    .userId(1L)
                    .chatName("chat1")
                    .message("Hi")
                    .response("Hello")
                    .timestamp(new Date(now.getTime() - 60000)) // 1 min ago
                    .isSuspicious(false)
                    .build();
            when(chatRepository.findByUserIdAndChatNameOrderByTimestampAsc(1L, "chat1"))
                    .thenReturn(List.of(prev));
            when(geminiService.generateContent(anyList())).thenReturn("New response");

            ChatResponseDTO result = chatUseCase.execute("How are you?", 1L, "chat1");

            assertEquals("How are you?", result.getMessage());
            assertEquals("New response", result.getResponse());
        }
    }
}
