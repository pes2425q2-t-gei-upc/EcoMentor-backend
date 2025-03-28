package com.EcoMentor_backend.EcoMentor.Exception;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timeframe,
                           String message,
                           String details,
                           String errorCode) {
}
