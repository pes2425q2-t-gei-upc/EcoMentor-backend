package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import com.EcoMentor_backend.EcoMentor.Chat.entity.Chat;
import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.repositories.ChatRepository;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatUseCaseTest {

@Mock
private GeminiService gemini;

@Mock
private ChatRepository repo;

@Mock
private UserRepository userRepository;

private ChatUseCase chatUseCase;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
    chatUseCase = new ChatUseCase(gemini, repo, userRepository);
}

@Test
@DisplayName("execute throws exception when user does not exist")
void executeThrowsExceptionWhenUserDoesNotExist() {
    when(userRepository.existsById(1L)).thenReturn(false);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
            chatUseCase.execute("Hello", 1L));

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertTrue(exception.getReason().contains("User not found"));
}

@Test
@DisplayName("execute throws exception when generated content is empty")
void executeThrowsExceptionWhenGeneratedContentIsEmpty() {
    when(userRepository.existsById(1L)).thenReturn(true);
    when(gemini.generateContent("Hello")).thenReturn("");

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
            chatUseCase.execute("Hello", 1L));

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
    assertTrue(exception.getReason().contains("Error generating response"));
}


}