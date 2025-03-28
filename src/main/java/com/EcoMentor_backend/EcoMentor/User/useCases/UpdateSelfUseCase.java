package com.EcoMentor_backend.EcoMentor.User.useCases;

import com.EcoMentor_backend.EcoMentor.Auth.infrastructure.jwt.JwtTokenProvider;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.UpdateUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateSelfUseCase {

    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;

    public User execute(UpdateUserDTO user, String token) {

        User self = userRepository.findByEmail(tokenProvider.getUsernameFromToken(token))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        self.setName(user.getName());
        userRepository.save(self);
        System.out.println("User with email" + self.getEmail() + " has been updated");
        return self;
    }

}
