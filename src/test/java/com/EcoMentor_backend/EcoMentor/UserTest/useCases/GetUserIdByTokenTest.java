package com.EcoMentor_backend.EcoMentor.UserTest.useCases;

import com.EcoMentor_backend.EcoMentor.Auth.infrastructure.jwt.JwtTokenProvider;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetUserIdByToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetUserIdByTokenTest {

    private JwtTokenProvider tokenProvider;
    private UserRepository userRepository;
    private GetUserIdByToken getUserIdByToken;

    @BeforeEach
    void setUp() {
        tokenProvider = mock(JwtTokenProvider.class);
        userRepository = mock(UserRepository.class);
        getUserIdByToken = new GetUserIdByToken(tokenProvider, userRepository);
    }

    @Test
    @DisplayName("Should return user ID when token is valid and user exists")
    void shouldReturnUserIdWhenValidToken() {
        String token = "validToken";
        String email = "user@example.com";
        Long expectedId = 123L;
        User user = new User();
        user.setId(expectedId);
        user.setEmail(email);

        when(tokenProvider.getUsernameFromToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Long actualId = getUserIdByToken.execute(token);

        assertEquals(expectedId, actualId);
        verify(tokenProvider, times(1)).getUsernameFromToken(token);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("Should throw ResponseStatusException NOT_FOUND when user does not exist")
    void shouldThrowWhenUserNotFound() {
        String token = "invalidToken";
        String email = "nouser@example.com";

        when(tokenProvider.getUsernameFromToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> getUserIdByToken.execute(token));

        assertEquals("User not found", exception.getReason());
        assertEquals(404, exception.getStatusCode().value());
        verify(tokenProvider, times(1)).getUsernameFromToken(token);
        verify(userRepository, times(1)).findByEmail(email);
    }
}
