package com.EcoMentor_backend.EcoMentor.Auth;

import com.EcoMentor_backend.EcoMentor.Auth.useCases.dto.LoginDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.CreateUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerIT {

    //Test that authenticated users can perform calls and viceversa

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void registerAndLoginShouldReturnToken() throws Exception {
        // Crear un nuevo usuario con los datos necesarios
        CreateUserDTO newUser = new CreateUserDTO("mockedUser", "test@example.com", "Password123");
        String requestBody = objectMapper.writeValueAsString(newUser);

        // Realizar la solicitud de registro
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists()); // Verificar que se devuelve un token

        // Iniciar sesión con el mismo email y la misma contraseña (asegúrate que la contraseña coincide)
        LoginDTO loginRequest = new LoginDTO("test@example.com", "Password123"); // Asegúrate de que la contraseña coincida
        String loginBody = objectMapper.writeValueAsString(loginRequest);

        // Realizar la solicitud de login
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists()); // Verificar que se devuelve un token
    }


    @Test
    void loginWithInvalidCredentialsShouldReturnUnauthorized() throws Exception {
        LoginDTO invalidLogin = new LoginDTO("mockedUser", "invalidPassword");
        String requestBody = objectMapper.writeValueAsString(invalidLogin);

        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isUnauthorized());
    }
}
