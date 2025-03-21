package com.EcoMentor_backend.EcoMentor.CertificateTest.infrastructure.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.controllers.CertificateGetController;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetAllCertificatesUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByCertificateIdUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;





public class CertificateGetControllerTest {

    @Mock
    private GetAllCertificatesUseCase getAllCertificatesUseCase;

    @Mock
    private GetCertificateByCertificateIdUseCase getCertificateByIdUseCase;

    @InjectMocks
    private CertificateGetController certificateGetController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCertificates() {
        CertificateWithoutForeignEntitiesDTO dto = new CertificateWithoutForeignEntitiesDTO();
        when(getAllCertificatesUseCase.execute()).thenReturn(Collections.singletonList(dto));

        List<CertificateWithoutForeignEntitiesDTO> response = certificateGetController.getAllCertificateUseCase();
        assertEquals(1, response.size());
    }

    @Test
    public void testGetCertificateById() {
        CertificateDTO dto = new CertificateDTO();
        when(getCertificateByIdUseCase.execute(1L)).thenReturn(dto);

        CertificateDTO response = certificateGetController.getCertificateByIdUseCase(1L);
        assertEquals(dto, response);
    }
}