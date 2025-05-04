package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import com.EcoMentor_backend.EcoMentor.Chat.entity.Chat;
import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.repositories.ChatRepository;
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
public class GetChatNamesUseCase {
    private final ChatRepository chatRepository;

    public ArrayList<String> getChatNamesUser(long userId) {
        List<Chat> chats = chatRepository.findByUserId(userId);
        if (chats.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No chats found for this user");
        }

        ArrayList<String> chatNames = new ArrayList<>();
        for (Chat chat : chats) {
            String chatName = chat.getChatName();
            if (!chatNames.contains(chatName)) {
                chatNames.add(chatName);
            }
        }
        return chatNames;
    }

}
