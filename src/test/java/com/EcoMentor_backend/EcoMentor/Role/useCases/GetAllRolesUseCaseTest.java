package com.EcoMentor_backend.EcoMentor.Role.useCases;

import com.EcoMentor_backend.EcoMentor.Role.entity.Role;
import com.EcoMentor_backend.EcoMentor.Role.entity.RoleName;
import com.EcoMentor_backend.EcoMentor.Role.infrastructure.repositories.RoleRepository;
import com.EcoMentor_backend.EcoMentor.Role.useCases.GetAllRolesUseCase;
import com.EcoMentor_backend.EcoMentor.Role.useCases.dto.RoleDTO;
import com.EcoMentor_backend.EcoMentor.Role.useCases.mapper.RoleMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GetAllRolesUseCaseTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private GetAllRolesUseCase getAllRolesUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_ReturnsListOfRoleDTOs() {
        // Arrange
        Role role1 = new Role();
        role1.setId(1L);
        role1.setName(RoleName.ROLE_USER);

        Role role2 = new Role();
        role2.setId(2L);
        role2.setName(RoleName.ROLE_ADMIN);

        RoleDTO roleDTO1 = new RoleDTO();
        roleDTO1.setId(1L);
        roleDTO1.setName(RoleName.ROLE_USER.toString());

        RoleDTO roleDTO2 = new RoleDTO();
        roleDTO2.setId(2L);
        roleDTO2.setName(RoleName.ROLE_ADMIN.toString());

        List<Role> roles = List.of(role1, role2);

        when(roleRepository.findAll()).thenReturn(roles);
        when(roleMapper.toDTO(role1)).thenReturn(roleDTO1);
        when(roleMapper.toDTO(role2)).thenReturn(roleDTO2);

        // Act
        List<RoleDTO> result = getAllRolesUseCase.execute();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo(RoleName.ROLE_USER.toString());
        assertThat(result.get(1).getName()).isEqualTo(RoleName.ROLE_ADMIN.toString());

        verify(roleRepository, times(1)).findAll();
        verify(roleMapper, times(1)).toDTO(role1);
        verify(roleMapper, times(1)).toDTO(role2);
    }

    @Test
    void testExecute_ReturnsEmptyList_WhenNoRolesExist() {
        // Arrange
        when(roleRepository.findAll()).thenReturn(List.of());

        // Act
        List<RoleDTO> result = getAllRolesUseCase.execute();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(roleRepository, times(1)).findAll();
        verifyNoInteractions(roleMapper);
    }
}
