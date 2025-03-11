package com.EcoMentor_backend.EcoMentor.Exception;

import jakarta.persistence.PersistenceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
        System.out.println(e.getMessage());
    }

    @ExceptionHandler(PersistenceException.class)
    public void handleJPAException(PersistenceException e) {
        System.out.println(e.getMessage());
    }
}
