package com.EcoMentor_backend.EcoMentor.Chat.useCases.mapper;

import com.EcoMentor_backend.EcoMentor.Chat.entity.Chat;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {
    public static ChatResponseDTO toChatResponseDTO(Chat chat) {
        return ChatResponseDTO.builder()
                .message(chat.getMessage())
                .response(chat.getResponse())
                .timestamp(chat.getTimestamp())
                .build();
    }
}