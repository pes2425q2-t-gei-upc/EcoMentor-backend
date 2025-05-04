package com.EcoMentor_backend.EcoMentor.UserTest.useCases;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Role.entity.Role;
import com.EcoMentor_backend.EcoMentor.Role.entity.RoleName;
import com.EcoMentor_backend.EcoMentor.Shared.EmailService;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.Role.infrastructure.repositories.RoleRepository;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.CreateUserUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.CreateUserDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.mapper.UserMapper;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;


public class CreateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void successfullyCreatesUser() throws MessagingException, IOException {
        CreateUserDTO userDTO = new CreateUserDTO("test@example.com", "password", "Test User");
        User user = new User();
        Role userRole = new Role();
        userRole.setName(RoleName.ROLE_USER);
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName(RoleName.ROLE_USER)).thenReturn(Optional.of(userRole));

            createUserUseCase.execute(userDTO);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void throwsExceptionWhenUserAlreadyExists() {
        CreateUserDTO userDTO = new CreateUserDTO("test@example.com", "password", "Test User");
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> createUserUseCase.execute(userDTO));
    }
}