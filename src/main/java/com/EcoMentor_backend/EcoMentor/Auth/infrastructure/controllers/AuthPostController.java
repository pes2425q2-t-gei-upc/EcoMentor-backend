package com.EcoMentor_backend.EcoMentor.Auth.infrastructure.controllers;


import com.EcoMentor_backend.EcoMentor.Auth.useCases.GoogleLoginUseCase;
import com.EcoMentor_backend.EcoMentor.Auth.useCases.LoginUseCase;
import com.EcoMentor_backend.EcoMentor.Auth.useCases.RegisterUseCase;
import com.EcoMentor_backend.EcoMentor.Auth.useCases.dto.AuthResponseDTO;
import com.EcoMentor_backend.EcoMentor.Auth.useCases.dto.GoogleAuthRequestDTO;
import com.EcoMentor_backend.EcoMentor.Auth.useCases.dto.LoginDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.CreateUserDTO;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
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
    private final GoogleLoginUseCase googleLoginUseCase;


    @PostMapping(value = "login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Validated LoginDTO loginDTO) {
        return ResponseEntity.ok(loginUseCase.execute(loginDTO));
    }


    @PostMapping(value = "register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Validated CreateUserDTO user)
            throws MessagingException, IOException {
        return ResponseEntity.ok(registerUseCase.execute(user));
    }

    @PostMapping(value = "google")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Validated GoogleAuthRequestDTO request)
            throws GeneralSecurityException, IOException, MessagingException {
        return ResponseEntity.ok(googleLoginUseCase.execute(request));
    }
}
