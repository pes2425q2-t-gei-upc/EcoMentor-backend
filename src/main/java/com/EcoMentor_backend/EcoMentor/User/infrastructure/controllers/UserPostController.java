package com.EcoMentor_backend.EcoMentor.User.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.User.useCases.AddCertificateToUserUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.CreateUserUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.CreateUserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Validated
@RequestMapping("/api/users")
public class UserPostController {
    private final CreateUserUseCase createUserUseCase;
    private final AddCertificateToUserUseCase addCertificateToUserUseCase;

    public UserPostController(CreateUserUseCase createUserUseCase,
                              AddCertificateToUserUseCase addCertificateToUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.addCertificateToUserUseCase = addCertificateToUserUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Validated CreateUserDTO user) {
        createUserUseCase.execute(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{userId}/certificates/{certificateId}")
    public ResponseEntity<Void> addCertificateToUser(@PathVariable Long userId, @PathVariable Long certificateId) {
        addCertificateToUserUseCase.execute(userId, certificateId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
