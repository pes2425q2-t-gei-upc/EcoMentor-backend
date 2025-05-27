package com.EcoMentor_backend.EcoMentor.CertificateTest.infrastructure.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.controllers.CertificatePostController;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.CalculateUnofficialCertificateUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.CreateCertificateUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateBySetOfValuesUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculateUnofficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculatorResultsDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateCertificateDTO;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;



class CertificatePostControllerTest {

    @Mock
    private CreateCertificateUseCase createCertificateUseCase;

    @Mock
    private GetCertificateBySetOfValuesUseCase getCertificateBySetOfValuesUseCase;

    @Mock
    private CalculateUnofficialCertificateUseCase calculateUnofficialCertificateUseCase;

    @InjectMocks
    private CertificatePostController certificatePostController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCertificate() {
        // Arrange
        CreateCertificateDTO createCertificateDTO = new CreateCertificateDTO();
        doNothing().when(createCertificateUseCase).execute(createCertificateDTO);

        // Act
        ResponseEntity<Void> response = certificatePostController.createCertificate(createCertificateDTO);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        verify(createCertificateUseCase, times(1)).execute(createCertificateDTO);
    }

    @Test
    void testGetCertificateBySetOfValues() {
        // Arrange
        String parameter = "buildingUse";
        List<Object> values = Arrays.asList("residential", "commercial");
        List<CertificateWithoutForeignEntitiesDTO> expectedCertificates = Arrays.asList(
                new CertificateWithoutForeignEntitiesDTO(),
                new CertificateWithoutForeignEntitiesDTO()
        );
        when(getCertificateBySetOfValuesUseCase.execute(parameter, values)).thenReturn(expectedCertificates);

        // Act
        ResponseEntity<List<CertificateWithoutForeignEntitiesDTO>> response =
                certificatePostController.getCertificateBySetOfValues(parameter, values);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedCertificates, response.getBody());
        verify(getCertificateBySetOfValuesUseCase, times(1)).execute(parameter, values);
    }


}