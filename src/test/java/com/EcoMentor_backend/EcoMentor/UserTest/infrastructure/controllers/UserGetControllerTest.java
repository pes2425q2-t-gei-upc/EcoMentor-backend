package com.EcoMentor_backend.EcoMentor.UserTest.infrastructure.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.controllers.UserGetController;
import com.EcoMentor_backend.EcoMentor.User.useCases.*;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.UserDTO;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class UserGetControllerTest {

    @Mock
    private GetAllUsersUseCase getAllUsersUseCase;

    @Mock
    private GetByEmailUseCase getByEmailUseCase;

    @Mock
    private GetUserByIdUseCase getUserByIdUseCase;

    @Mock
    private GetUsersCertificatesUseCase getUsersCertificatesUseCase;

    @Mock
    private GetSelfUseCase getSelfUseCase;

    @InjectMocks
    private UserGetController userGetController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsersReturnsListOfUsers() {
        List<UserDTO> users = List.of(new UserDTO());
        when(getAllUsersUseCase.execute()).thenReturn(users);

        List<UserDTO> result = userGetController.getAllUsers();

        assertEquals(users, result);
    }

    @Test
    void getAllUsersReturnsEmptyListWhenNoUsers() {
        when(getAllUsersUseCase.execute()).thenReturn(Collections.emptyList());

        List<UserDTO> result = userGetController.getAllUsers();

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void getUserByEmailReturnsUser() {
        String email = "test@example.com";
        UserDTO user = new UserDTO();
        when(getByEmailUseCase.execute(email)).thenReturn(user);

        UserDTO result = userGetController.getUserByEmail(email);

        assertEquals(user, result);
    }

    @Test
    void getUserByIdReturnsUser() {
        long id = 1L;
        UserDTO user = new UserDTO();
        when(getUserByIdUseCase.execute(id)).thenReturn(user);

        UserDTO result = userGetController.getUserById(id);

        assertEquals(user, result);
    }

    @Test
    void getSelfReturnsUser() {
        String token = "valid-token";
        String authorizationHeader = "Bearer " + token;
        UserDTO user = new UserDTO();
        when(getSelfUseCase.execute(token)).thenReturn(user);

        UserDTO result = userGetController.getMe(authorizationHeader);

        assertEquals(user, result);
    }

    @Test
    void getUsersCertificatesReturnsListOfCertificates() {
        long id = 1L;
        List<CertificateDTO> certificates = List.of(new CertificateDTO());
        when(getUsersCertificatesUseCase.execute(id)).thenReturn(certificates);

        List<CertificateDTO> result = userGetController.getUsersCertificates(id);

        assertEquals(certificates, result);
    }

    @Test
    void getUsersCertificatesReturnsEmptyListWhenNoCertificates() {
        long id = 1L;
        when(getUsersCertificatesUseCase.execute(id)).thenReturn(Collections.emptyList());

        List<CertificateDTO> result = userGetController.getUsersCertificates(id);

        assertEquals(Collections.emptyList(), result);
    }
}