package com.EcoMentor_backend.EcoMentor.Chat.infraestructure.controllers;

import com.EcoMentor_backend.EcoMentor.Chat.useCases.ChatUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.PostContextUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.CreateChatDTO;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.CreateChatWithCertificateDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/api/chat")
public class ChatPostController {
    private final ChatUseCase chatUseCase;
    private final PostContextUseCase postContextUseCase;

    @PostMapping
    public ResponseEntity<ChatResponseDTO> newChat(@RequestBody CreateChatDTO dto) {
        ChatResponseDTO chatResponseDTO = chatUseCase.execute(dto.getMessage(),  dto.getUserId(), dto.getChatName(),
                                                                                                    dto.getDateTime());
        return new ResponseEntity<>(chatResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/context")
    public ResponseEntity<ChatResponseDTO> newChatWithContext(@RequestBody CreateChatWithCertificateDTO dto) {
        ChatResponseDTO chatResponseDTO = postContextUseCase.execute(dto);
        return new ResponseEntity<>(chatResponseDTO, HttpStatus.CREATED);
    }




}
