package com.EcoMentor_backend.EcoMentor.Auth.useCases;

import com.EcoMentor_backend.EcoMentor.Auth.infrastructure.jwt.JwtTokenProvider;
import com.EcoMentor_backend.EcoMentor.Auth.useCases.dto.AuthResponseDTO;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.useCases.CreateUserUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.CreateUserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegisterUseCaseTest {

    @Mock
    private CreateUserUseCase createUserUseCase;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private RegisterUseCase registerUseCase;

    @Test
    void executeWithValidCredentialsReturnsTokenDTO(){
        CreateUserDTO createUserDTO = new CreateUserDTO("validName","validEmail","validPassword");
        User user = new User();
        user.setId(1L);
        user.setEmail("validEmail");
        when(createUserUseCase.execute(createUserDTO)).thenReturn(user);
        when(jwtTokenProvider.getToken(user)).thenReturn("validToken");

        AuthResponseDTO token = registerUseCase.execute(createUserDTO);

        assertThat(token).isNotNull();
        assertThat(token.getToken()).isEqualTo("validToken");

    }

    @Test
    void executeWithExistingCredentialsThrowsException(){
        CreateUserDTO createUserDTO = new CreateUserDTO("existingName","existingEmail","X");
        when(createUserUseCase.execute(createUserDTO)).thenThrow(new RuntimeException());
        assertThatThrownBy( () -> registerUseCase.execute(createUserDTO)).isInstanceOf(RuntimeException.class);
    }


}
