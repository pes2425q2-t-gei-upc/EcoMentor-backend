package com.EcoMentor_backend.EcoMentor.User.useCases;

import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.UserDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.mapper.UserMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GetByEmailUseCase {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public GetByEmailUseCase(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO execute(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found") );
        return userMapper.toDTO(user);
    }
}
