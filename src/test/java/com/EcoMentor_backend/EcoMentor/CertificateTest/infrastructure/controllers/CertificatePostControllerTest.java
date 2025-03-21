package com.EcoMentor_backend.EcoMentor.CertificateTest.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.controllers.CertificatePostController;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.CreateCertificateUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateBySetOfValuesUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateCertificateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CertificatePostControllerTest {

    @Mock
    private CreateCertificateUseCase createCertificateUseCase;

    @Mock
    private GetCertificateBySetOfValuesUseCase getCertificateBySetOfValuesUseCase;

    @InjectMocks
    private CertificatePostController certificatePostController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCertificate() {
        CreateCertificateDTO dto = new CreateCertificateDTO();
        ResponseEntity<Void> response = certificatePostController.createCertificate(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetCertificateBySetOfValues() {
        CertificateWithoutForeignEntitiesDTO dto = new CertificateWithoutForeignEntitiesDTO();
        when(getCertificateBySetOfValuesUseCase.execute("parameter", Collections.singletonList("value"))).thenReturn(Collections.singletonList(dto));

        ResponseEntity<List<CertificateWithoutForeignEntitiesDTO>> response = certificatePostController.getCertificateBySetOfValues("parameter", Collections.singletonList("value"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }
}