package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import com.EcoMentor_backend.EcoMentor.Chat.entity.Chat;
import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.repositories.ChatRepository;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.mapper.ChatMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
@AllArgsConstructor
public class GetChatUseCase {
    private final ChatRepository chatRepository;
    private ChatMapper chatMapper;

    public List<ChatResponseDTO> execute(Long userId, String chatName) {
        List<Chat> chats = chatRepository.findByUserIdAndChatName(userId, chatName);
        if (chats.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No chats found for this user");
        }

        List<ChatResponseDTO> chatResponseDTOS = new ArrayList<>();

        for (Chat chat : chats) {
            ChatResponseDTO chatResponseDTO = chatMapper.toChatResponseDTO(chat);
            chatResponseDTOS.add(chatResponseDTO);
        }
        return chatResponseDTOS;
    }

}
