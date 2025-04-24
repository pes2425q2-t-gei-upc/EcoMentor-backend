package com.EcoMentor_backend.EcoMentor.Role.infrastructure;

import com.EcoMentor_backend.EcoMentor.Role.infrastructure.controllers.RoleDeleteController;
import com.EcoMentor_backend.EcoMentor.Role.useCases.DeleteRoleUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RoleDeleteControllerTest {

    @Mock
    private DeleteRoleUseCase deleteRoleUseCase;

    @InjectMocks
    private RoleDeleteController roleDeleteController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roleDeleteController).build();
    }

    @Test
    void deleteRoleSuccessfully() throws Exception {
        // Act
        mockMvc.perform(delete("/api/roles/{id}", 1L))
                .andExpect(status().isNoContent());

        // Assert
        verify(deleteRoleUseCase).execute(1L);
    }

    @Test
    void deleteRoleNotFound() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Role not found")).when(deleteRoleUseCase).execute(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/roles/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(deleteRoleUseCase).execute(1L);
    }
}
