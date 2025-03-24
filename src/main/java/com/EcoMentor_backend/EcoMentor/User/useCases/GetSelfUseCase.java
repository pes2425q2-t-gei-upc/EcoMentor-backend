package com.EcoMentor_backend.EcoMentor.User.useCases;

import com.EcoMentor_backend.EcoMentor.Auth.infrastructure.jwt.JwtTokenProvider;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.UserDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSelfUseCase {

    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserDTO execute(String token) {
        User self = userRepository.findByEmail(tokenProvider.getUsernameFromToken(token))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println(self);
        return userMapper.toDTO(self);
    }
}
