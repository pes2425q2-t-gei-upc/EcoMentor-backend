package com.EcoMentor_backend.EcoMentor.Chat.infraestructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.controllers.ChatPostController;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.ChatUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.PostContextUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.CreateChatDTO;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.CreateChatWithCertificateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

class ChatPostControllerTest {

private ChatUseCase chatUseCase;
private PostContextUseCase postContextUseCase;
private ChatPostController chatPostController;

@BeforeEach
void setUp() {
    chatUseCase = mock(ChatUseCase.class);
    postContextUseCase = mock(PostContextUseCase.class);
    chatPostController = new ChatPostController(chatUseCase, postContextUseCase);
}


@Test
@DisplayName("Handles invalid certificate ID in new chat with context")
void handlesInvalidCertificateIdInNewChatWithContext() {
    CreateChatWithCertificateDTO dto = new CreateChatWithCertificateDTO(1L, "Chat1", LocalDateTime.now(), "Message", 999L);
    when(postContextUseCase.execute(dto)).thenThrow(new RuntimeException("Certificate not found"));

    RuntimeException exception = assertThrows(RuntimeException.class, () -> chatPostController.newChatWithContext(dto));

    assertEquals("Certificate not found", exception.getMessage());
}
}