package com.EcoMentor_backend.EcoMentor.Auth.useCases;

import com.EcoMentor_backend.EcoMentor.Auth.infrastructure.jwt.JwtTokenProvider;
import com.EcoMentor_backend.EcoMentor.Auth.useCases.dto.AuthResponseDTO;
import com.EcoMentor_backend.EcoMentor.Auth.useCases.dto.GoogleAuthRequestDTO;
import com.EcoMentor_backend.EcoMentor.Config.GoogleConfig;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.CreateUserUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.CreateUserDTO;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GoogleLoginUseCaseTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private CreateUserUseCase createUserUseCase;
    @Mock
    private GoogleConfig googleConfig;



    @Mock
    private GoogleIdTokenVerifier.Builder verifierBuilder;
    @Mock
    private GoogleIdTokenVerifier verifier;

    @Mock
    private GoogleIdToken idToken;
    @Mock
    private Payload payload;

    @InjectMocks
    private GoogleLoginUseCase googleLoginUseCase;

    @BeforeEach
    void setUp() {
        when(verifierBuilder.setAudience(anyList())).thenReturn(verifierBuilder);
        when(verifierBuilder.build()).thenReturn(verifier);
    }

    @Test
    void executeWithValidNewGoogleIdTokenCreatesNewUserAndReturnsToken() throws GeneralSecurityException, IOException, MessagingException {
        // Arrange
        String testIdToken = "validGoogleIdToken";
        String testEmail = "newuser@example.com";
        String testName = "New User";
        String testJwtToken = "newJwtToken";

        GoogleAuthRequestDTO request = new GoogleAuthRequestDTO(testIdToken);

        when(googleConfig.getClientId()).thenReturn("testClientId");

        // The 'verifier' mock is now the one used by googleLoginUseCase
        when(verifier.verify(testIdToken)).thenReturn(idToken);
        when(idToken.getPayload()).thenReturn(payload);
        when(payload.getEmail()).thenReturn(testEmail);
        when(payload.get("name")).thenReturn(testName);
        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.empty());

        User newUser = new User();
        newUser.setEmail(testEmail);
        newUser.setName(testName);
        newUser.setId(1L);

        when(createUserUseCase.execute(any(CreateUserDTO.class))).thenReturn(newUser);
        when(jwtTokenProvider.getToken(newUser)).thenReturn(testJwtToken);

        // Act
        AuthResponseDTO response = googleLoginUseCase.execute(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo(testJwtToken);

        // Verify interactions with the mocked dependencies
        verify(googleConfig, times(1)).getClientId();
        verify(verifierBuilder, times(1)).setAudience(Collections.singletonList("testClientId"));
        verify(verifierBuilder, times(1)).build();
        verify(verifier, times(1)).verify(testIdToken);
        verify(userRepository, times(1)).findByEmail(testEmail);
        verify(createUserUseCase, times(1)).execute(any(CreateUserDTO.class));
        verify(jwtTokenProvider, times(1)).getToken(newUser);
    }

    @Test
    void executeWithValidExistingGoogleIdTokenReturnsTokenForExistingUser() throws GeneralSecurityException, IOException, MessagingException {
        // Arrange
        String testIdToken = "validGoogleIdToken";
        String testEmail = "existinguser@example.com";
        String testName = "Existing User";
        String testJwtToken = "existingJwtToken";

        GoogleAuthRequestDTO request = new GoogleAuthRequestDTO(testIdToken);

        when(googleConfig.getClientId()).thenReturn("testClientId");
        when(verifier.verify(testIdToken)).thenReturn(idToken);
        when(idToken.getPayload()).thenReturn(payload);
        when(payload.getEmail()).thenReturn(testEmail);
        when(payload.get("name")).thenReturn(testName);

        User existingUser = new User();
        existingUser.setEmail(testEmail);
        existingUser.setName(testName);
        existingUser.setId(2L);

        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.of(existingUser));
        when(jwtTokenProvider.getToken(existingUser)).thenReturn(testJwtToken);

        // Act
        AuthResponseDTO response = googleLoginUseCase.execute(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo(testJwtToken);
        verify(googleConfig, times(1)).getClientId();
        verify(verifierBuilder, times(1)).setAudience(Collections.singletonList("testClientId"));
        verify(verifierBuilder, times(1)).build();
        verify(verifier, times(1)).verify(testIdToken);
        verify(userRepository, times(1)).findByEmail(testEmail);
        verify(createUserUseCase, never()).execute(any(CreateUserDTO.class));
        verify(jwtTokenProvider, times(1)).getToken(existingUser);
    }

    @Test
    void executeWithInvalidGoogleIdTokenThrowsBadCredentialsException() throws GeneralSecurityException, IOException, MessagingException {
        // Arrange
        String invalidIdToken = "invalidGoogleIdToken";
        GoogleAuthRequestDTO request = new GoogleAuthRequestDTO(invalidIdToken);

        when(googleConfig.getClientId()).thenReturn("testClientId");
        when(verifier.verify(invalidIdToken)).thenReturn(null); // Simulate invalid token

        // Act & Assert
        assertThatThrownBy(() -> googleLoginUseCase.execute(request))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Invalid ID token");

        verify(googleConfig, times(1)).getClientId();
        verify(verifierBuilder, times(1)).setAudience(Collections.singletonList("testClientId"));
        verify(verifierBuilder, times(1)).build();
        verify(verifier, times(1)).verify(invalidIdToken);
        verify(userRepository, never()).findByEmail(anyString());
        verify(createUserUseCase, never()).execute(any(CreateUserDTO.class));
        verify(jwtTokenProvider, never()).getToken(any(User.class));
    }

    @Test
    void executeHandlesGeneralSecurityException() throws GeneralSecurityException, IOException {
        // Arrange
        String testIdToken = "tokenWithError";
        GoogleAuthRequestDTO request = new GoogleAuthRequestDTO(testIdToken);

        when(googleConfig.getClientId()).thenReturn("testClientId");
        when(verifier.verify(testIdToken)).thenThrow(new GeneralSecurityException("Security error"));

        // Act & Assert
        assertThatThrownBy(() -> googleLoginUseCase.execute(request))
                .isInstanceOf(GeneralSecurityException.class)
                .hasMessage("Security error");

        verify(googleConfig, times(1)).getClientId();
        verify(verifierBuilder, times(1)).setAudience(Collections.singletonList("testClientId"));
        verify(verifierBuilder, times(1)).build();
        verify(verifier, times(1)).verify(testIdToken);
    }

    @Test
    void executeHandlesIOException() throws GeneralSecurityException, IOException {
        // Arrange
        String testIdToken = "tokenWithError";
        GoogleAuthRequestDTO request = new GoogleAuthRequestDTO(testIdToken);

        when(googleConfig.getClientId()).thenReturn("testClientId");
        when(verifier.verify(testIdToken)).thenThrow(new IOException("IO error"));

        // Act & Assert
        assertThatThrownBy(() -> googleLoginUseCase.execute(request))
                .isInstanceOf(IOException.class)
                .hasMessage("IO error");

        verify(googleConfig, times(1)).getClientId();
        verify(verifierBuilder, times(1)).setAudience(Collections.singletonList("testClientId"));
        verify(verifierBuilder, times(1)).build();
        verify(verifier, times(1)).verify(testIdToken);
    }
}