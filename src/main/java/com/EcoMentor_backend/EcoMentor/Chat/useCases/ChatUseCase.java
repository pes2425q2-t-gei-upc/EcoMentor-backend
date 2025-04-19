package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import com.EcoMentor_backend.EcoMentor.Chat.entity.Chat;
import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.repositories.ChatRepository;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ChatUseCase {
    private final GeminiService gemini;
    private final ChatRepository repo;
    private final UserRepository userRepository;

    public ChatUseCase(GeminiService gemini, ChatRepository repo, UserRepository userRepository) {
        this.gemini = gemini;
        this.repo = repo;
        this.userRepository = userRepository;
    }

    public ChatResponseDTO execute(String message, Long userId, String chatName) {
        String answer = gemini.generateContent(message);
        LocalDateTime now = LocalDateTime.now();

        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        if (answer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error generating response");
        }


        Chat chat = Chat.builder()
                .userId(userId)
                .message(message)
                .response(answer)
                .timestamp(now)
                .chatName(chatName)
                .build();

        repo.save(chat);

        return ChatResponseDTO.builder()
                .message(message)
                .response(answer)
                .timestamp(now)
                .build();
    }
}
