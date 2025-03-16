package com.EcoMentor_backend.EcoMentor.Auth.useCases;


import com.EcoMentor_backend.EcoMentor.Auth.infrastructure.jwt.JwtTokenProvider;
import com.EcoMentor_backend.EcoMentor.Auth.useCases.dto.AuthResponse;
import com.EcoMentor_backend.EcoMentor.Auth.useCases.dto.LoginDTO;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginUseCase {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthResponse execute(LoginDTO login) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
        UserDetails user = userRepository.findByEmail(login.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String token = jwtTokenProvider.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();

    }
}
