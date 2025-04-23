package com.EcoMentor_backend.EcoMentor.Chat.infraestructure.repositories;

import com.EcoMentor_backend.EcoMentor.Chat.entity.Chat;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ChatRepository extends MongoRepository<Chat, String> {
    List<Chat> findByUserId(Long userId);

    List<Chat> findByUserIdOrderByTimestampAsc(Long userId);

    List<Chat> findByUserIdAndChatName(Long userId, String chatName);

    List<Chat> findByUserIdAndChatNameOrderByTimestampAsc(Long userId, String chatName);
}
