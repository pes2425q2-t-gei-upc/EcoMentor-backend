package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import com.EcoMentor_backend.EcoMentor.Chat.entity.Chat;
import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.repositories.ChatRepository;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.BanAndTimeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CheckBanStatusUseCaseTest {

@Mock
private ChatRepository chatRepository;

@InjectMocks
private CheckBanStatusUseCase checkBanStatusUseCase;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}

@Test
void returnsBanStatusWhenUserIsBanned() {
    Long userId = 1L;
    Chat suspiciousChat = Chat.builder()
            .timestamp(LocalDateTime.now().minusMinutes(3))
            .isSuspicious(true)
            .build();
    when(chatRepository.findByUserIdOrderByTimestampAsc(userId))
            .thenReturn(List.of(suspiciousChat));

    BanAndTimeDTO result = checkBanStatusUseCase.execute(userId);

    assertTrue(result.isBanned());
    assertNotNull(result.getBanEndTime());
}

@Test
void returnsNotBannedWhenUserHasNoChats() {
    Long userId = 1L;
    when(chatRepository.findByUserIdOrderByTimestampAsc(userId))
            .thenReturn(List.of());

    BanAndTimeDTO result = checkBanStatusUseCase.execute(userId);

    assertFalse(result.isBanned());
    assertNull(result.getBanEndTime());
}

@Test
void returnsNotBannedWhenLastChatIsNotSuspicious() {
    Long userId = 1L;
    Chat nonSuspiciousChat = Chat.builder()
            .timestamp(LocalDateTime.now().minusMinutes(10))
            .isSuspicious(false)
            .build();
    when(chatRepository.findByUserIdOrderByTimestampAsc(userId))
            .thenReturn(List.of(nonSuspiciousChat));

    BanAndTimeDTO result = checkBanStatusUseCase.execute(userId);

    assertFalse(result.isBanned());
    assertNull(result.getBanEndTime());
}

@Test
void returnsNotBannedWhenBanTimeHasExpired() {
    Long userId = 1L;
    Chat expiredBanChat = Chat.builder()
            .timestamp(LocalDateTime.now().minusMinutes(10))
            .isSuspicious(true)
            .build();
    when(chatRepository.findByUserIdOrderByTimestampAsc(userId))
            .thenReturn(List.of(expiredBanChat));

    BanAndTimeDTO result = checkBanStatusUseCase.execute(userId);

    assertFalse(result.isBanned());
    assertNull(result.getBanEndTime());
}
}
