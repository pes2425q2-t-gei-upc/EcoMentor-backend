package com.EcoMentor_backend.EcoMentor.Chat.useCases.dto;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BanAndTimeDTO {
    ZonedDateTime banEndTime;
    boolean isBanned;
}
