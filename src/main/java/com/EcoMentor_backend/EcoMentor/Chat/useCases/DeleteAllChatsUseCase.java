package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.repositories.ChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AllArgsConstructor
@Service
public class DeleteAllChatsUseCase {
    private final ChatRepository chatRepository;

    public void execute(Long userId) {
        chatRepository.deleteByUserId(userId);
    }
}
