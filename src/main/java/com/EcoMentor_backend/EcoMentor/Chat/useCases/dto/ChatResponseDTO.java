package com.EcoMentor_backend.EcoMentor.Chat.useCases.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatResponseDTO {
    private String message;
    private String response;
    private Date timestamp;
    private boolean isSuspicious;
}
