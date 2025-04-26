package com.EcoMentor_backend.EcoMentor.UserTest.infrastructure.controllers;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.EcoMentor_backend.EcoMentor.User.infrastructure.controllers.UserDeleteController;
import com.EcoMentor_backend.EcoMentor.User.useCases.DeleteUserUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.RemoveCertificateFromUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class UserDeleteControllerTest {

    @Mock
    private DeleteUserUseCase deleteUserUseCase;

    @Mock
    private RemoveCertificateFromUserUseCase removeCertificateFromUserUseCase;

    @InjectMocks
    private UserDeleteController userDeleteController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userDeleteController).build();
    }

    @Test
    void deleteUserSuccessfully() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(deleteUserUseCase).execute(1L);
    }

    @Test
    void removeCertificateFromUserSuccessfully() throws Exception {
        mockMvc.perform(delete("/api/users/{userId}/certificates/{certificateId}", 1L, 100L))
                .andExpect(status().isOk());

        verify(removeCertificateFromUserUseCase).execute(1L, 100L);
    }
}