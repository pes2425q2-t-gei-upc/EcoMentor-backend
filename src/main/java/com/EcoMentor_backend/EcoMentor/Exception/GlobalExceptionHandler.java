package com.EcoMentor_backend.EcoMentor.Exception;

import jakarta.persistence.PersistenceException;
import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException(Exception e, WebRequest req) {
        ErrorDetails errorDetails = new ErrorDetails(
                ZonedDateTime.now(),
                e.getMessage(),
                req.getDescription(false),
                "INTERNAL_SERVER_ERROR"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<ErrorDetails> handleJPAException(PersistenceException e, WebRequest req) {
        ErrorDetails errorDetails = new ErrorDetails(
                ZonedDateTime.now(),
                e.getMessage(),
                req.getDescription(false),
                "INTERNAL_SERVER_ERROR: JPA Exception"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> handleBadCredentialsException(BadCredentialsException e, WebRequest req) {
        ErrorDetails errorDetails = new ErrorDetails(
                ZonedDateTime.now(),
                "Invalid username or password",
                req.getDescription(false),
                "UNAUTHORIZED"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleBadCredentialsException(UsernameNotFoundException e, WebRequest req) {
        ErrorDetails errorDetails = new ErrorDetails(
                ZonedDateTime.now(),
                "User not found",
                req.getDescription(false),
                "INTERNAL_SERVER_ERROR: Database Exception"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(org.springframework.web.server.ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(
                                                            org.springframework.web.server.ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    }

}
