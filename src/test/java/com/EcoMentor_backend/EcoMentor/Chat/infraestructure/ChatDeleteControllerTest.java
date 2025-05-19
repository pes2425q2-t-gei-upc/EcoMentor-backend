package com.EcoMentor_backend.EcoMentor.Chat.infraestructure;

import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.controllers.ChatDeleteController;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.DeleteChatUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ChatDeleteControllerTest {

    private DeleteChatUseCase deleteChatUseCase;
    private ChatDeleteController controller;

    @BeforeEach
    void setUp() {
        deleteChatUseCase = Mockito.mock(DeleteChatUseCase.class);
        controller = new ChatDeleteController(deleteChatUseCase);
    }

    @Test
    @DisplayName("Should delete chat and return success message")
    void shouldDeleteChatSuccessfully() {
        Long userId = 15L;
        String chatName = "testChat";

        // No need to stub deleteChatUseCase.execute as it returns void
        doNothing().when(deleteChatUseCase).execute(userId, chatName);

        ResponseEntity<String> response = controller.deleteChat(userId, chatName);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Chat deleted successfully", response.getBody());
        verify(deleteChatUseCase, times(1)).execute(userId, chatName);
    }

    @Test
    @DisplayName("Should handle exception from use case gracefully")
    void shouldHandleExceptionGracefully() {
        Long userId = 20L;
        String chatName = "otherChat";

        doThrow(new RuntimeException("Delete failed")).when(deleteChatUseCase).execute(userId, chatName);

        try {
            controller.deleteChat(userId, chatName);
        } catch (RuntimeException ex) {
            assertEquals("Delete failed", ex.getMessage());
        }
        verify(deleteChatUseCase, times(1)).execute(userId, chatName);
    }
}