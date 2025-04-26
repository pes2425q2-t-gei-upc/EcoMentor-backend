package com.EcoMentor_backend.EcoMentor.Chat.useCases.dto;


import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateChatDTO {
    private Long userId;

    @NotBlank
    private String chatName;

    @NotBlank
    LocalDateTime dateTime;

    @NotBlank
    private String message;
}

