package com.EcoMentor_backend.EcoMentor.User.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.User.useCases.CreateUserUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.CreateUserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Validated
@RequestMapping("/api/users")
public class UserPostController {
    private final CreateUserUseCase createUserUseCase;

    public UserPostController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Validated CreateUserDTO user) {
        createUserUseCase.execute(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
