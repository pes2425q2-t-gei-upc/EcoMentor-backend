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

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    void testGetChat() {
        long userId = 10L;
        String chatName = "testChat";
        // Timestamps for tests
        Date ts1 = Date.from(Instant.parse("2025-04-26T10:00:00Z"));
        Date ts2 = Date.from(Instant.parse("2025-04-26T10:01:00Z"));

        List<ChatResponseDTO> expectedList = Arrays.asList(
                ChatResponseDTO.builder()
                        .message("hello")
                        .response("world")
                        .timestamp(ts1)
                        .isSuspicious(false)
                        .build(),
                ChatResponseDTO.builder()
                        .message("foo")
                        .response("bar")
                        .timestamp(ts2)
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
        Date banTime = Date.from(Instant.parse("2025-04-26T10:01:00Z"));
        BanAndTimeDTO dto = BanAndTimeDTO.builder()
                .isBanned(true)
                .banEndTime(banTime)
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
