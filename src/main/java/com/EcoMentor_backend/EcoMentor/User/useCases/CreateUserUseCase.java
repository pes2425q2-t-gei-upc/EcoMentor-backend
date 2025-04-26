package com.EcoMentor_backend.EcoMentor.User.useCases;

import com.EcoMentor_backend.EcoMentor.Role.entity.Role;
import com.EcoMentor_backend.EcoMentor.Role.entity.RoleName;
import com.EcoMentor_backend.EcoMentor.Role.infrastructure.repositories.RoleRepository;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.CreateUserDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.mapper.UserMapper;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User execute(CreateUserDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        User user = userMapper.toEntity(userDTO);
        Role role = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow();
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(Set.of(role));

        userRepository.save(user);
        System.out.println("User created " + user);
        return user;
    }
}
