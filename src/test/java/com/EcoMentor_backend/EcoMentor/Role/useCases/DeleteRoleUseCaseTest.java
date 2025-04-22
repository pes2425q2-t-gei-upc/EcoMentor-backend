package com.EcoMentor_backend.EcoMentor.Role.useCases;

import com.EcoMentor_backend.EcoMentor.Role.entity.Role;
import com.EcoMentor_backend.EcoMentor.Role.entity.RoleName;
import com.EcoMentor_backend.EcoMentor.Role.infrastructure.repositories.RoleRepository;
import com.EcoMentor_backend.EcoMentor.Role.useCases.DeleteRoleUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class DeleteRoleUseCaseTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private DeleteRoleUseCase deleteRoleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_DeletesRole_WhenRoleExists() {
        // Arrange
        Role role = new Role();
        role.setId(1L);
        role.setName(RoleName.ROLE_USER);

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        // Act
        deleteRoleUseCase.execute(1L);

        // Assert
        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).delete(role);
    }

    @Test
    void testExecute_ThrowsException_WhenRoleNotFound() {
        // Arrange
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> deleteRoleUseCase.execute(1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Role not found");

        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, never()).delete(any());
    }
}
