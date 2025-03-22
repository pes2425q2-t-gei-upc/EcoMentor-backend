package com.EcoMentor_backend.EcoMentor.User.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.User.useCases.DeleteUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/users")
public class UserDeleteController {

    private final DeleteUserUseCase deleteUserUseCase;

    public UserDeleteController(DeleteUserUseCase deleteUserUseCase) {
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            deleteUserUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) { //! Hay que modificalo por una exeption personalizada
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
