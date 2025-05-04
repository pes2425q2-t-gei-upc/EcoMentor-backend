package com.EcoMentor_backend.EcoMentor.Role.infrastructure;

import com.EcoMentor_backend.EcoMentor.Exception.GlobalExceptionHandler;
import com.EcoMentor_backend.EcoMentor.Role.entity.RoleName;
import com.EcoMentor_backend.EcoMentor.Role.infrastructure.controllers.RoleGetController;
import com.EcoMentor_backend.EcoMentor.Role.useCases.GetAllRolesUseCase;
import com.EcoMentor_backend.EcoMentor.Role.useCases.GetRoleUseCase;
import com.EcoMentor_backend.EcoMentor.Role.useCases.dto.RoleDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RoleGetControllerTest {

    @Mock
    private GetAllRolesUseCase getAllRolesUseCase;

    @Mock
    private GetRoleUseCase getRoleUseCase;

    @InjectMocks
    private RoleGetController roleGetController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roleGetController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getRoleByIdSuccessfully() throws Exception {
        RoleDTO role = new RoleDTO(1L, RoleName.ROLE_USER.toString());

        when(getRoleUseCase.execute(1L)).thenReturn(role);

        mockMvc.perform(get("/api/roles/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("ROLE_USER"));

        verify(getRoleUseCase, times(1)).execute(1L);
    }

    @Test
    void getRoleByIdNotFound() throws Exception {
        when(getRoleUseCase.execute(1L)).thenThrow(new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Role not found"));

        mockMvc.perform(get("/api/roles/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(getRoleUseCase).execute(1L);
    }
}
