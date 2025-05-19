package com.EcoMentor_backend.EcoMentor.Chat.infraestructure;

import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.controllers.ChatGetController;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.CheckBanStatusUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.GetChatNamesUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.GetChatUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.BanAndTimeDTO;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetUserIdByToken;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ChatGetControllerTest {

    private GetChatNamesUseCase getChatNamesUseCase;
    private GetChatUseCase getChatUseCase;
    private CheckBanStatusUseCase checkBanStatusUseCase;
    private GetUserIdByToken getUserIdByToken;
    private ChatGetController controller;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        getChatNamesUseCase = Mockito.mock(GetChatNamesUseCase.class);
        getChatUseCase = Mockito.mock(GetChatUseCase.class);
        checkBanStatusUseCase = Mockito.mock(CheckBanStatusUseCase.class);
        getUserIdByToken = Mockito.mock(GetUserIdByToken.class);
        controller = new ChatGetController(getChatNamesUseCase, getChatUseCase, checkBanStatusUseCase, getUserIdByToken);
        request = Mockito.mock(HttpServletRequest.class);
    }

    @Test
    @DisplayName("Should return chat names for valid token")
    void shouldReturnChatNamesForValidToken() {
        String token = "token123";
        Long userId = 42L;
        ArrayList<String> names = new ArrayList<>();
        names.add("chat1"); names.add("chat2");

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(getUserIdByToken.execute(token)).thenReturn(userId);
        when(getChatNamesUseCase.getChatNamesUser(userId)).thenReturn(names);

        ResponseEntity<ArrayList<String>> response = controller.getChatNamesUser(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("chat1", response.getBody().get(0));
    }

    @Test
    @DisplayName("Should return chat messages for given chatName and valid token")
    void shouldReturnChatMessagesForChatName() {
        String token = "tokenABC";
        Long userId = 7L;
        String chatName = "general";
        List<ChatResponseDTO> messages = Collections.singletonList(
                ChatResponseDTO.builder().message("hello").build()
        );

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(getUserIdByToken.execute(token)).thenReturn(userId);
        when(getChatUseCase.execute(userId, chatName)).thenReturn(messages);

        ResponseEntity<List<ChatResponseDTO>> response = controller.getChat(request, chatName);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("hello", response.getBody().get(0).getMessage());
    }

}