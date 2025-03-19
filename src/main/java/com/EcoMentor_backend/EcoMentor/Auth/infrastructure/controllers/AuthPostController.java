package com.EcoMentor_backend.EcoMentor.Auth.infrastructure.controllers;


import com.EcoMentor_backend.EcoMentor.Auth.useCases.LoginUseCase;
import com.EcoMentor_backend.EcoMentor.Auth.useCases.RegisterUseCase;
import com.EcoMentor_backend.EcoMentor.Auth.useCases.dto.AuthResponseDTO;
import com.EcoMentor_backend.EcoMentor.Auth.useCases.dto.LoginDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.CreateUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthPostController {


    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;


    @PostMapping(value="login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Validated LoginDTO loginDTO) {
        return ResponseEntity.ok(loginUseCase.execute(loginDTO));
    }


    @PostMapping(value = "register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Validated CreateUserDTO user) {
        return ResponseEntity.ok(registerUseCase.execute(user));
    }
}
