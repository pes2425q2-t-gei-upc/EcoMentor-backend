package com.EcoMentor_backend.EcoMentor.Chat.useCases;


import com.EcoMentor_backend.EcoMentor.Chat.entity.Chat;
import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.repositories.ChatRepository;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.BanAndTimeDTO;
import java.time.ZonedDateTime;
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
        List<Chat> chats = chatRepository.findByUserIdOrderByTimestampAsc(userId);

        if (chats != null && !chats.isEmpty()) {
            Chat last = chats.get(chats.size() - 1);

            if (last.isSuspicious()) {
                ZonedDateTime now = ZonedDateTime.now();
                ZonedDateTime banEndTime = last.getTimestamp().plusMinutes(5);
                if (now.isBefore(banEndTime)) {
                    return BanAndTimeDTO.builder()
                            .banEndTime(banEndTime)
                            .isBanned(true)
                            .build();
                }
            }
        }
        return BanAndTimeDTO.builder()
                .banEndTime(null)
                .isBanned(false)
                .build();

    }
}
