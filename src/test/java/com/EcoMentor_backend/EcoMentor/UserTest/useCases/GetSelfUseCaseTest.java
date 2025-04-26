package com.EcoMentor_backend.EcoMentor.UserTest.useCases;

import com.EcoMentor_backend.EcoMentor.Auth.infrastructure.jwt.JwtTokenProvider;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetSelfUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.UserDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetSelfUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private GetSelfUseCase getSelfUseCase;

    @Test
    void returnsUserWhenTokenIsValid() {
        //Arrange
        String token = "valid-token";
        String email = "test@example.com";
        String name = "Original Name";

        User selfEntity = User.builder()
                .name(name)
                .email(email)
                .password("password")
                .build();
        when(userRepository.findByEmail(jwtTokenProvider.getUsernameFromToken(token))).thenReturn(java.util.Optional.of(selfEntity));
        when(userMapper.toDTO(selfEntity)).thenReturn(new UserDTO(name,1L,email,"password",new ArrayList<>(), new ArrayList<>()));

        //Act
        UserDTO self = getSelfUseCase.execute(token);


        //Assert
        assertEquals(email, self.getEmail());
        assertEquals(email, self.getEmail());

    }

    @Test
    void throwsExceptionWhenTokenNotValid() {
        String token = "";
        String invalid = "invalid-username";
        when(jwtTokenProvider.getUsernameFromToken(token)).thenReturn(invalid);

        assertThrows(ResponseStatusException.class, () -> getSelfUseCase.execute(token));
    }
}
