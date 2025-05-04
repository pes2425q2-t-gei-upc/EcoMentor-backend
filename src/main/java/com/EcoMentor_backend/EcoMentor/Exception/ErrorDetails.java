package com.EcoMentor_backend.EcoMentor.Exception;

import java.time.ZonedDateTime;

public record ErrorDetails(ZonedDateTime timeframe,
                           String message,
                           String details,
                           String errorCode) {
}
