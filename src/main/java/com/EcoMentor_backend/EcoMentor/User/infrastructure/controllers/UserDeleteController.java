package com.EcoMentor_backend.EcoMentor.User.infrastructure.controllers;


import com.EcoMentor_backend.EcoMentor.User.useCases.DeleteUserUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.RemoveCertificateFromUserUseCase;
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
    private final RemoveCertificateFromUserUseCase removeCertificateFromUserUseCase;

    public UserDeleteController(DeleteUserUseCase deleteUserUseCase,
                                RemoveCertificateFromUserUseCase removeCertificateFromUserUseCase) {

        this.removeCertificateFromUserUseCase = removeCertificateFromUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            deleteUserUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{userId}/certificates/{certificateId}")
    public ResponseEntity<Void> removeCertificateFromUser(@PathVariable Long userId, @PathVariable Long certificateId) {
        removeCertificateFromUserUseCase.execute(userId, certificateId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
