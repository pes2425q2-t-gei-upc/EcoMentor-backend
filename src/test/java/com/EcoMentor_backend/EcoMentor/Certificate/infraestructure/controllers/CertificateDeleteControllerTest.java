package com.EcoMentor_backend.EcoMentor.Certificate.infraestructure.controllers;

import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.controllers.CertificateDeleteController;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.DeleteCertificateUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CertificateDeleteControllerTest {

    @Mock
    private DeleteCertificateUseCase deleteCertificateUseCase;

    @InjectMocks
    private CertificateDeleteController certificateDeleteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteCertificate() {
        doNothing().when(deleteCertificateUseCase).execute(1L);

        ResponseEntity<Void> response = certificateDeleteController.deleteCertificate(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deleteCertificateUseCase, times(1)).execute(1L);
    }

    @Test
    void deleteCertificateNotFound() {
        doThrow(new RuntimeException("Certificate not found")).when(deleteCertificateUseCase).execute(1L);

        ResponseEntity<Void> response = certificateDeleteController.deleteCertificate(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(deleteCertificateUseCase, times(1)).execute(1L);
    }
}