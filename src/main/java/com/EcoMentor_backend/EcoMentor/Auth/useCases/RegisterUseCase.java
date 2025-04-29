package com.EcoMentor_backend.EcoMentor.Auth.useCases;


import com.EcoMentor_backend.EcoMentor.Auth.infrastructure.jwt.JwtTokenProvider;
import com.EcoMentor_backend.EcoMentor.Auth.useCases.dto.AuthResponseDTO;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.useCases.CreateUserUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.CreateUserDTO;
import jakarta.mail.MessagingException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterUseCase {

    private final CreateUserUseCase createUserUseCase;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponseDTO execute(CreateUserDTO registrationRequest) throws MessagingException, IOException {
        User user = createUserUseCase.execute(registrationRequest);

        return AuthResponseDTO.builder()
                        .token(jwtTokenProvider.getToken(user))
                        .build();
    }
}
