package com.EcoMentor_backend.EcoMentor.Auth.useCases;

import com.EcoMentor_backend.EcoMentor.Auth.infrastructure.jwt.JwtTokenProvider;
import com.EcoMentor_backend.EcoMentor.Auth.useCases.dto.AuthResponseDTO;
import com.EcoMentor_backend.EcoMentor.Auth.useCases.dto.GoogleAuthRequestDTO;
import com.EcoMentor_backend.EcoMentor.Config.GoogleConfig;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.CreateUserUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.CreateUserDTO;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
@Transactional
public class GoogleLoginUseCase {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final CreateUserUseCase createUserUseCase;
    private final GoogleConfig googleConfig;
    private final GoogleIdTokenVerifier.Builder verifierBuilder;

    public AuthResponseDTO execute(GoogleAuthRequestDTO request)
            throws GeneralSecurityException, IOException, IllegalArgumentException, MessagingException {
        GoogleIdTokenVerifier verifier = verifierBuilder
                .setAudience(Collections.singletonList(googleConfig.getClientId()))
                .build();


        GoogleIdToken idToken = verifier.verify(request.getIdToken());
        if (idToken != null) {
            Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");

            User user = userRepository.findByEmail(email).orElse(null);

            if (user == null) {
                CreateUserDTO createUserDTO = CreateUserDTO.builder()
                        .email(email)
                        .name(name)
                        .password(UUID.randomUUID().toString())
                        .build();
                user = createUserUseCase.execute(createUserDTO);
            }

            String token = jwtTokenProvider.getToken(user);
            Set<String> roles = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            return AuthResponseDTO.builder()
                    .token(token)
                    .roles(roles)
                    .build();

        } else {
            System.out.println("Invalid ID token.");
            throw new BadCredentialsException("Invalid ID token");
        }
    }

}
