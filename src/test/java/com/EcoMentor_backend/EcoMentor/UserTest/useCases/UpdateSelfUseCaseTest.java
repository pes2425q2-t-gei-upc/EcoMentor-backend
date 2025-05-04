package com.EcoMentor_backend.EcoMentor.UserTest.useCases;

import com.EcoMentor_backend.EcoMentor.Auth.infrastructure.jwt.JwtTokenProvider;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.UpdateSelfUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.UpdateUserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateSelfUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UpdateSelfUseCase updateSelfUseCase;


    @Test
    void shouldUpdateUser(){
        String token = "valid-token";
        String email = "test@example.com";
        String originalName = "Original Name";
        String newName = "New Name";

        User existingUser = new User();
        existingUser.setEmail(email);
        existingUser.setName(originalName);

        UpdateUserDTO updateUserDTO = new UpdateUserDTO(newName);
        //Mock everything before the update of the user
        when(jwtTokenProvider.getUsernameFromToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(java.util.Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User updatedUser = updateSelfUseCase.execute(updateUserDTO, token);
        assertEquals(newName, updatedUser.getName(), "User name should be updated");
    }

    @Test
    void throwsExceptionWhenTokenUserNotFound() {
        String token = "";
        String invalid = "invalid-username";
        UpdateUserDTO updateUserDTO = new UpdateUserDTO("new-name");

        when(jwtTokenProvider.getUsernameFromToken(token)).thenReturn(invalid);

        assertThrows(ResponseStatusException.class, () -> updateSelfUseCase.execute(updateUserDTO, token));
    }

}
