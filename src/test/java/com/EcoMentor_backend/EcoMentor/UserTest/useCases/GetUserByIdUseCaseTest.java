package com.EcoMentor_backend.EcoMentor.UserTest.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetUserByIdUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.UserDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



class GetUserByIdUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private GetUserByIdUseCase getUserByIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return UserDTO when user is found")
    void shouldReturnUserDTOWhenUserIsFound() {
        User user = new User();
        user.setId(1L);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        when(userRepository.findUserById(1L)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserDTO result = getUserByIdUseCase.execute(1L);

        assertEquals(userDTO, result);
    }

    @Test
    @DisplayName("Should throw exception when user is not found")
    void shouldThrowExceptionWhenUserIsNotFound() {
        when(userRepository.findUserById(1L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> getUserByIdUseCase.execute(1L));
    }
}