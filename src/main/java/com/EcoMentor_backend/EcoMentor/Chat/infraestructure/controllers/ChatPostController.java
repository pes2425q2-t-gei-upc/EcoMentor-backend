package com.EcoMentor_backend.EcoMentor.Chat.infraestructure.controllers;

import com.EcoMentor_backend.EcoMentor.Chat.useCases.ChatUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.PostContextUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.CreateChatDTO;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.CreateChatWithCertificateDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetUserIdByToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    private final GetUserIdByToken getUserIdByToken;

    @PostMapping
    public ResponseEntity<ChatResponseDTO> newChat(HttpServletRequest request, @RequestBody CreateChatDTO dto) {
        Long userId = getUserIdFromToken(request);
        ChatResponseDTO chatResponseDTO = chatUseCase.execute(dto.getMessage(),  userId, dto.getChatName());
        return new ResponseEntity<>(chatResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/context")
    public ResponseEntity<ChatResponseDTO> newChatWithContext(HttpServletRequest request,
                                                                        @RequestBody CreateChatWithCertificateDTO dto) {
        Long userId = getUserIdFromToken(request);
        ChatResponseDTO chatResponseDTO = postContextUseCase.execute(dto, userId);
        return new ResponseEntity<>(chatResponseDTO, HttpStatus.CREATED);
    }

    private Long getUserIdFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        return getUserIdByToken.execute(token);
    }

}
