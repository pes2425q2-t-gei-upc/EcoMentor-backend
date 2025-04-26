package com.EcoMentor_backend.EcoMentor.Chat.infraestructure.controllers;

import com.EcoMentor_backend.EcoMentor.Chat.useCases.CheckBanStatusUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.GetChatNamesUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.GetChatUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.BanAndTimeDTO;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/chat")
public class ChatGetController {
    private GetChatNamesUseCase getChatNamesUseCase;
    private GetChatUseCase getChatUseCase;
    private CheckBanStatusUseCase checkBanStatusUseCase;


    @GetMapping("/{userId}")
    public ResponseEntity<ArrayList<String>> getChatNamesUser(@PathVariable long userId) {
        return ResponseEntity.ok(getChatNamesUseCase.getChatNamesUser(userId));
    }

    @GetMapping
    public ResponseEntity<List<ChatResponseDTO>> getChat(@RequestParam long userId, @RequestParam String chatName) {
        return ResponseEntity.ok(getChatUseCase.execute(userId, chatName));
    }

    @GetMapping("/{userId}/ban-status")
    public ResponseEntity<BanAndTimeDTO> getBanStatus(@PathVariable Long userId) {
        BanAndTimeDTO dto = checkBanStatusUseCase.execute(userId);
        return ResponseEntity.ok(dto);
    }


}
