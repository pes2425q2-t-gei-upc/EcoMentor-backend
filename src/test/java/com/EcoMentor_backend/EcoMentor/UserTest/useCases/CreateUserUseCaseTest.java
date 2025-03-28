package com.EcoMentor_backend.EcoMentor.UserTest.useCases;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.CreateUserUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.CreateUserDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;


public class CreateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void successfullyCreatesUser() {
        CreateUserDTO userDTO = new CreateUserDTO("test@example.com", "password", "Test User");
        User user = new User();
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");

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