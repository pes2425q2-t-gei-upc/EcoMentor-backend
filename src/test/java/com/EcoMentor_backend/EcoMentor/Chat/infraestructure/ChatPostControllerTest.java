package com.EcoMentor_backend.EcoMentor.Chat.infraestructure;

import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.controllers.ChatPostController;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.ChatUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.PostContextUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.CreateChatDTO;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.CreateChatWithCertificateDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetUserIdByToken;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ChatPostControllerTest {

    private ChatUseCase chatUseCase;
    private PostContextUseCase postContextUseCase;
    private GetUserIdByToken getUserIdByToken;
    private ChatPostController controller;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        chatUseCase = Mockito.mock(ChatUseCase.class);
        postContextUseCase = Mockito.mock(PostContextUseCase.class);
        getUserIdByToken = Mockito.mock(GetUserIdByToken.class);
        controller = new ChatPostController(chatUseCase, postContextUseCase, getUserIdByToken);
        request = Mockito.mock(HttpServletRequest.class);
    }

    @Test
    @DisplayName("Should create new chat and return CREATED status with response body")
    void shouldCreateNewChat() {
        String token = "token123";
        Long userId = 5L;
        CreateChatDTO dto = new CreateChatDTO();
        dto.setMessage("Hello");
        dto.setChatName("testChat");
        ChatResponseDTO responseDTO = ChatResponseDTO.builder().message("Reply").build();

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);
        when(getUserIdByToken.execute(token)).thenReturn(userId);
        when(chatUseCase.execute("Hello", userId, "testChat")).thenReturn(responseDTO);

        ResponseEntity<ChatResponseDTO> response = controller.newChat(request, dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Reply", response.getBody().getMessage());
    }
    

    @Test
    @DisplayName("Should handle missing Authorization header when creating new chat")
    void shouldHandleMissingAuthHeaderOnNewChat() {
        CreateChatDTO dto = new CreateChatDTO();
        dto.setMessage("Hi");
        dto.setChatName("chat");
        ChatResponseDTO responseDTO = ChatResponseDTO.builder().message("NoAuthReply").build();

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);
        when(getUserIdByToken.execute(null)).thenReturn(null);
        when(chatUseCase.execute("Hi", null, "chat")).thenReturn(responseDTO);

        ResponseEntity<ChatResponseDTO> response = controller.newChat(request, dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("NoAuthReply", response.getBody().getMessage());
    }
}