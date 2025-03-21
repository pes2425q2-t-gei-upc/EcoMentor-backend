package com.EcoMentor_backend.EcoMentor.CertificateTest.infrastructure.controllers;

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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class CertificateDeleteControllerTest {

    @Mock
    private DeleteCertificateUseCase deleteCertificateUseCase;

    @InjectMocks
    private CertificateDeleteController certificateDeleteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeleteCertificateSuccess() {
        ResponseEntity<Void> response = certificateDeleteController.deleteCertificate(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deleteCertificateUseCase).execute(1L);
    }

    @Test
    public void testDeleteCertificateNotFound() {
        doThrow(new RuntimeException("Certificate not found")).when(deleteCertificateUseCase).execute(1L);
        ResponseEntity<Void> response = certificateDeleteController.deleteCertificate(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}