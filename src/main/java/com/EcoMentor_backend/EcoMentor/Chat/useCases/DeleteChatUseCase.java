package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.repositories.ChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@AllArgsConstructor
public class DeleteChatUseCase {
    private final ChatRepository chatRepository;

    public void execute(Long userId, String chatName) {
        if (chatRepository.findByUserIdAndChatName(userId, chatName).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found");
        }
        chatRepository.deleteByUserIdAndChatName(userId, chatName);
    }
}
