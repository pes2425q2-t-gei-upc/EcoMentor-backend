package com.EcoMentor_backend.EcoMentor.UserTest.infrastructure.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.controllers.UserGetController;
import com.EcoMentor_backend.EcoMentor.User.useCases.*;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Collections;
import java.util.List;

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
void testGetAllUsers_ReturnsUserPage() {
    int page = 0;
    int size = 10;
    String sort = "name";
    Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
    UserDTO userDTO = new UserDTO();
    Page<UserDTO> pageResult = new PageImpl<>(List.of(userDTO), pageable, 1);

    when(getAllUsersUseCase.execute(page, size, sort)).thenReturn(pageResult);

    ResponseEntity<Page<UserDTO>> response = userGetController.getAllUsers(page, size, sort);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(1, response.getBody().getTotalElements());
    verify(getAllUsersUseCase).execute(page, size, sort);
}

@Test
void testGetUserByEmail_ReturnsUserDTO() {
    String email = "test@example.com";
    UserDTO expected = new UserDTO();

    when(getByEmailUseCase.execute(email)).thenReturn(expected);

    UserDTO result = userGetController.getUserByEmail(email);

    assertEquals(expected, result);
    verify(getByEmailUseCase).execute(email);
}

@Test
void testGetUserById_ReturnsUserDTO() {
    long userId = 1L;
    UserDTO expected = new UserDTO();

    when(getUserByIdUseCase.execute(userId)).thenReturn(expected);

    UserDTO result = userGetController.getUserById(userId);

    assertEquals(expected, result);
    verify(getUserByIdUseCase).execute(userId);
}

@Test
void testGetUsersCertificates_ReturnsCertificateList() {
    long userId = 1L;
    CertificateDTO cert = new CertificateDTO();

    when(getUsersCertificatesUseCase.execute(userId)).thenReturn(List.of(cert));

    List<CertificateDTO> result = userGetController.getUsersCertificates(userId);

    assertEquals(1, result.size());
    assertEquals(cert, result.get(0));
    verify(getUsersCertificatesUseCase).execute(userId);
}

@Test
void testGetMe_ReturnsUserDTO() {
    String token = "token123";
    String header = "Bearer " + token;
    UserDTO expected = new UserDTO();

    when(getSelfUseCase.execute(token)).thenReturn(expected);

    UserDTO result = userGetController.getMe(header);

    assertEquals(expected, result);
    verify(getSelfUseCase).execute(token);
}

@Test
void testGetMe_ThrowsBadCredentialsException() {
    String invalidHeader = "InvalidHeader";

    try {
        userGetController.getMe(invalidHeader);
    } catch (BadCredentialsException ex) {
        assertEquals("Invalid token", ex.getMessage());
    }

    verifyNoInteractions(getSelfUseCase);
}
}
