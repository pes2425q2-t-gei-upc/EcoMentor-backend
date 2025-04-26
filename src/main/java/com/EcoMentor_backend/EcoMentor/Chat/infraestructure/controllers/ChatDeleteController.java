package com.EcoMentor_backend.EcoMentor.Chat.infraestructure.controllers;

import com.EcoMentor_backend.EcoMentor.Chat.useCases.DeleteChatUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/chat")
public class ChatDeleteController {
    private DeleteChatUseCase deleteChatUseCase;

    @DeleteMapping("/{userId}/{chatName}")
    public ResponseEntity<String> deleteChat(@PathVariable Long userId, @PathVariable String chatName) {
        deleteChatUseCase.execute(userId, chatName);
        return ResponseEntity.ok("Chat deleted successfully");
    }
}
