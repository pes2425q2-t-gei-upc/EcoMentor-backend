package com.EcoMentor_backend.EcoMentor.Chat.useCases.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.EcoMentor_backend.EcoMentor.Chat.entity.Chat;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

class ChatMapperTest {

@Test
@DisplayName("Maps Chat entity to ChatResponseDTO successfully")
void mapsChatEntityToChatResponseDTOSuccessfully() {
    Chat chat = Chat.builder()
            .message("Test message")
            .response("Test response")
            .timestamp(ZonedDateTime.now())
            .build();

    ChatResponseDTO result = ChatMapper.toChatResponseDTO(chat);

    assertEquals(chat.getMessage(), result.getMessage());
    assertEquals(chat.getResponse(), result.getResponse());
    assertEquals(chat.getTimestamp(), result.getTimestamp());
}


@Test
@DisplayName("Handles Chat entity with null fields")
void handlesChatEntityWithNullFields() {
    Chat chat = Chat.builder()
            .message(null)
            .response(null)
            .timestamp(null)
            .build();

    ChatResponseDTO result = ChatMapper.toChatResponseDTO(chat);

    assertNull(result.getMessage());
    assertNull(result.getResponse());
    assertNull(result.getTimestamp());
}
}