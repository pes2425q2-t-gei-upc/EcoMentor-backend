package com.EcoMentor_backend.EcoMentor.Chat.useCases;


import com.EcoMentor_backend.EcoMentor.Chat.entity.Chat;
import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.repositories.ChatRepository;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.BanAndTimeDTO;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@AllArgsConstructor
public class CheckBanStatusUseCase {
    private final ChatRepository chatRepository;

    public BanAndTimeDTO execute(Long userId) {
        List<Chat> chat = chatRepository.findByUserIdOrderByTimestampAsc(userId);

        if (chat != null && chat.getLast().isSuspicious()) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime banEndTime = chat.getLast().getTimestamp().plusMinutes(5);
            if (now.isBefore(banEndTime)) {
                return BanAndTimeDTO.builder()
                        .banEndTime(chat.getLast().getTimestamp().plusMinutes(5))
                        .isBanned(true)
                        .build();
            }
        }
        return BanAndTimeDTO.builder()
                .banEndTime(null)
                .isBanned(false)
                .build();

    }
}
