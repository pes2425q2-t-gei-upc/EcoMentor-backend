package com.EcoMentor_backend.EcoMentor.UserTest.useCases;

import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetAllUsersUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.UserDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GetAllUsersUseCaseTest {

@Mock
private UserRepository userRepository;

@Mock
private UserMapper userMapper;

@InjectMocks
private GetAllUsersUseCase getAllUsersUseCase;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}

@Test
void testExecute_ReturnsPageOfUserDTOs() {
    // Arrange
    User user = new User();
    user.setId(1L);
    user.setName("Test User");

    UserDTO userDTO = new UserDTO();
    userDTO.setId(1L);
    userDTO.setName("Test User");

    Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
    Page<User> userPage = new PageImpl<>(List.of(user), pageable, 1);

    when(userRepository.findAll(pageable)).thenReturn(userPage);
    when(userMapper.toDTO(user)).thenReturn(userDTO);

    // Act
    Page<UserDTO> result = getAllUsersUseCase.execute(0, 10, "name");

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getTotalElements()).isEqualTo(1);
    assertThat(result.getContent().get(0).getName()).isEqualTo("Test User");

    verify(userRepository, times(1)).findAll(pageable);
    verify(userMapper, times(1)).toDTO(user);
}
}
