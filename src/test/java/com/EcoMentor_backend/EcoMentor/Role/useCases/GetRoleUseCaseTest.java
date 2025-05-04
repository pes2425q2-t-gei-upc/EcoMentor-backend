package com.EcoMentor_backend.EcoMentor.Role.useCases;

import com.EcoMentor_backend.EcoMentor.Role.entity.Role;
import com.EcoMentor_backend.EcoMentor.Role.entity.RoleName;
import com.EcoMentor_backend.EcoMentor.Role.infrastructure.repositories.RoleRepository;
import com.EcoMentor_backend.EcoMentor.Role.useCases.GetRoleUseCase;
import com.EcoMentor_backend.EcoMentor.Role.useCases.dto.RoleDTO;
import com.EcoMentor_backend.EcoMentor.Role.useCases.mapper.RoleMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class GetRoleUseCaseTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private GetRoleUseCase getRoleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_ReturnsRoleDTO_WhenRoleExists() {
        // Arrange
        Role role = new Role();
        role.setId(1L);
        role.setName(RoleName.ROLE_USER);

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(RoleName.ROLE_USER.toString());

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleMapper.toDTO(role)).thenReturn(roleDTO);

        // Act
        RoleDTO result = getRoleUseCase.execute(1L);

        // Assert
        assertThat(result).isNotNull();
        System.out.println(result);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo(RoleName.ROLE_USER.toString());

        verify(roleRepository, times(1)).findById(1L);
        verify(roleMapper, times(1)).toDTO(role);
    }

    @Test
    void testExecute_ThrowsException_WhenRoleNotFound() {
        // Arrange
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> getRoleUseCase.execute(1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Role not found");

        verify(roleRepository, times(1)).findById(1L);
        verifyNoInteractions(roleMapper);
    }
}
