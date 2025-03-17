package com.EcoMentor_backend.EcoMentor.Certificate.infraestructure.controllers;

import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.controllers.CertificatePostController;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.CreateCertificateUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.CreateOfficialCertificateUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateOfficialCertificateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CertificatePostControllerTest {

    @Mock
    private CreateCertificateUseCase createCertificateUseCase;
    @Mock
    private CreateOfficialCertificateUseCase createOfficialCertificateUseCase;

    @InjectMocks
    private CertificatePostController certificatePostController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCertificate() {
        CreateCertificateDTO certificateDTO = new CreateCertificateDTO();
        doNothing().when(createCertificateUseCase).execute(certificateDTO);

        ResponseEntity<Void> response = certificatePostController.createCertificate(certificateDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(createCertificateUseCase, times(1)).execute(certificateDTO);
    }

    @Test
    void createOfficialCertificate() {
        CreateOfficialCertificateDTO officialCertificateDTO = new CreateOfficialCertificateDTO();
        doNothing().when(createOfficialCertificateUseCase).execute(officialCertificateDTO);

        ResponseEntity<Void> response = certificatePostController.createOfficialCertificate(officialCertificateDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(createOfficialCertificateUseCase, times(1)).execute(officialCertificateDTO);
    }
}