package com.EcoMentor_backend.EcoMentor.Chat.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "chats")
public class Chat {
    @Id
    private String id;

    private Long userId;

    private String chatName;

    private String message;

    private String response;

    private LocalDateTime timestamp;
}

