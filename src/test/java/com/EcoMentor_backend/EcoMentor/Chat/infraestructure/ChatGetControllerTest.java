package com.EcoMentor_backend.EcoMentor.Chat.infraestructure;

import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.controllers.ChatGetController;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.CheckBanStatusUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.GetChatNamesUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.GetChatUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.BanAndTimeDTO;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ChatGetControllerTest {

    @Mock
    private GetChatNamesUseCase getChatNamesUseCase;

    @Mock
    private GetChatUseCase getChatUseCase;

    @Mock
    private CheckBanStatusUseCase checkBanStatusUseCase;

    @InjectMocks
    private ChatGetController chatGetController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetChatNamesUser() {
        long userId = 123L;
        ArrayList<String> expectedNames = new ArrayList<>(List.of("chat1", "chat2"));
        when(getChatNamesUseCase.getChatNamesUser(userId)).thenReturn(expectedNames);

        ResponseEntity<ArrayList<String>> response = chatGetController.getChatNamesUser(userId);

        assertEquals(ResponseEntity.ok(expectedNames), response);
        verify(getChatNamesUseCase).getChatNamesUser(userId);
    }

    @Test
    void testGetChat() {
        long userId = 10L;
        String chatName = "testChat";
        List<ChatResponseDTO> expectedList = List.of(
                ChatResponseDTO.builder()
                        .message("hello")
                        .response("world")
                        .timestamp(ZonedDateTime.of(2025, 4, 26, 12, 0, 0, 0, ZoneId.systemDefault()))
                        .isSuspicious(false)
                        .build(),
                ChatResponseDTO.builder()
                        .message("foo")
                        .response("bar")
                        .timestamp(ZonedDateTime.of(2025, 4, 26, 12, 1, 0, 0, ZoneId.systemDefault()))
                        .isSuspicious(true)
                        .build()
        );
        when(getChatUseCase.execute(userId, chatName)).thenReturn(expectedList);

        ResponseEntity<List<ChatResponseDTO>> response = chatGetController.getChat(userId, chatName);

        assertEquals(ResponseEntity.ok(expectedList), response);
        verify(getChatUseCase).execute(userId, chatName);
    }

    @Test
    void testGetBanStatus_WhenBanned() {
        long userId = 55L;
        BanAndTimeDTO dto = BanAndTimeDTO.builder()
                .isBanned(true)
                .banEndTime(ZonedDateTime.of(2025, 4, 26, 12, 1, 0, 0, ZoneId.systemDefault()))
                .build();
        when(checkBanStatusUseCase.execute(userId)).thenReturn(dto);

        ResponseEntity<BanAndTimeDTO> response = chatGetController.getBanStatus(userId);

        assertEquals(ResponseEntity.ok(dto), response);
        verify(checkBanStatusUseCase).execute(userId);
    }

    @Test
    void testGetBanStatus_WhenNotBanned() {
        long userId = 56L;
        BanAndTimeDTO dto = BanAndTimeDTO.builder()
                .isBanned(false)
                .banEndTime(null)
                .build();
        when(checkBanStatusUseCase.execute(userId)).thenReturn(dto);

        ResponseEntity<BanAndTimeDTO> response = chatGetController.getBanStatus(userId);

        assertEquals(ResponseEntity.ok(dto), response);
        verify(checkBanStatusUseCase).execute(userId);
    }
}
