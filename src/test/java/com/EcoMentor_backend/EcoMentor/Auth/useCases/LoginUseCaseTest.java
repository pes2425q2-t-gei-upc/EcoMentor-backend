package com.EcoMentor_backend.EcoMentor.Auth.useCases;

import com.EcoMentor_backend.EcoMentor.Auth.infrastructure.jwt.JwtTokenProvider;
import com.EcoMentor_backend.EcoMentor.Auth.useCases.dto.AuthResponseDTO;
import com.EcoMentor_backend.EcoMentor.Auth.useCases.dto.LoginDTO;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class LoginUseCaseTest {


    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private AuthenticationManager authenticationManager;



    @InjectMocks
    private LoginUseCase loginUseCase;

    @Test
    void executeWithValidCredentialsReturnsTokenDTO(){
        LoginDTO loginDTO = new LoginDTO( "validUser","validPassword");
        Authentication authentication = mock(Authentication.class);
        User user = new User();
        user.setId(1L);
        user.setEmail("validUsername");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(Optional.of(user));
        when(jwtTokenProvider.getToken(user)).thenReturn("token");

        AuthResponseDTO token = loginUseCase.execute(loginDTO);

        assertThat(token).isNotNull();
        assertThat(token.getToken()).isEqualTo("token");

    }

    @Test
    void executeWithInvalidCredentialsThrowsException(){
        LoginDTO loginDTO = new LoginDTO( "invalidUser","invalidPassword");
        Authentication authentication = mock(Authentication.class);
        User user = new User();
        user.setId(1L);
        user.setEmail("validUsername");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(Optional.empty());

        assertThatThrownBy( () -> loginUseCase.execute(loginDTO)).isInstanceOf(RuntimeException.class);
    }

}
