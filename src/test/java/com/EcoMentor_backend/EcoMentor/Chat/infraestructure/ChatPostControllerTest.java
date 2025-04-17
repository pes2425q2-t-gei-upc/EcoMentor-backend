package com.EcoMentor_backend.EcoMentor.Chat.infraestructure;

import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.controllers.ChatPostController;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.ChatUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.CreateChatDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatPostControllerTest {

@Mock
private ChatUseCase chatUseCase;

private ChatPostController chatPostController;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
    chatPostController = new ChatPostController(chatUseCase);
}

@Test
@DisplayName("newChat returns CREATED status and response when input is valid")
void newChatReturnsCreatedStatusAndResponseWhenInputIsValid() {
    CreateChatDTO dto = new CreateChatDTO(1L, "Hello");
    ChatResponseDTO responseDTO = ChatResponseDTO.builder()
            .message("Hello")
            .response("Hi there!")
            .timestamp(null)
            .build();

    when(chatUseCase.execute(dto.getMessage(), dto.getUserId())).thenReturn(responseDTO);

    ResponseEntity<ChatResponseDTO> response = chatPostController.newChat(dto);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(responseDTO, response.getBody());
}


}