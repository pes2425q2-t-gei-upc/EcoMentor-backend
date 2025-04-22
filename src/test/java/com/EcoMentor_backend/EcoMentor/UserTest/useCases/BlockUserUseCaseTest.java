package com.EcoMentor_backend.EcoMentor.UserTest.useCases;

import com.EcoMentor_backend.EcoMentor.Role.entity.Role;
import com.EcoMentor_backend.EcoMentor.Role.entity.RoleName;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.Role.infrastructure.repositories.RoleRepository;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.BlockUserUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlockUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private BlockUserUseCase blockUserUseCase;

    @Test
    void executeSuccessfullyBlocksUser() {
        Long userId = 1L;
        User user = new User();
        Role blockedRole = new Role();
        blockedRole.setName(RoleName.ROLE_BLOCKED);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findByName(RoleName.ROLE_BLOCKED)).thenReturn(Optional.of(blockedRole));

        blockUserUseCase.execute(userId);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void executeWithUserNotFoundThrowsException() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> blockUserUseCase.execute(userId));
    }

    @Test
    void executeWithRoleNotFoundThrowsException() {
        Long userId = 1L;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findByName(RoleName.ROLE_BLOCKED)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> blockUserUseCase.execute(userId));
    }
}