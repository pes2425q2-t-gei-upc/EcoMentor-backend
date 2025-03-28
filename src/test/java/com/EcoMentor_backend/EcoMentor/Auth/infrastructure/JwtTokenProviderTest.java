package com.EcoMentor_backend.EcoMentor.Auth.infrastructure;

import com.EcoMentor_backend.EcoMentor.Auth.infrastructure.jwt.JwtTokenProvider;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private UserDetails mockUser;

    // To test behaviour we setup a mock (and valid) user
    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        mockUser = mock(User.class);
    }

    @Test
    void generatesValidToken(){
        String token = jwtTokenProvider.getToken(mockUser);
        assertThat(token).isNotNull().isNotEmpty();
    }

    @Test
    void validatesTokenSuccessfully(){
        String token = jwtTokenProvider.getToken(mockUser);
        boolean isValid = jwtTokenProvider.validateToken(token);
        assertThat(isValid).isTrue();
    }

    @Test
    void extractsUsernameFromToken() {
        String token = jwtTokenProvider.getToken(mockUser);
        String username = jwtTokenProvider.getUsernameFromToken(token);
        assertThat(username).isEqualTo(mockUser.getUsername());
    }

    @Test
    void returnFalseForInvaidToken(){
        String invalidToken = "invalid-token";
        boolean isValid = jwtTokenProvider.validateToken(invalidToken);
        assertThat(isValid).isFalse();
    }


}
