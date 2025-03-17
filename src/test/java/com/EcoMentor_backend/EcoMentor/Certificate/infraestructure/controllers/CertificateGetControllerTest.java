package com.EcoMentor_backend.EcoMentor.Certificate.infraestructure.controllers;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.controllers.CertificateGetController;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.*;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CertificateGetControllerTest {

    @Mock
    private GetAllCertificatesUseCase getAllCertificatesUseCase;
    @Mock
    private GetCertificateByCertificateIdUseCase getCertificateByIdUseCase;
    @Mock
    private GetCertificateByAddressUseCase getCertificateByAddressUseCase;
    @Mock
    private GetCertificateByClimateZoneUseCase getCertificateByClimateZoneUseCase;
    @Mock
    private GetCertificateByNonRenewablePrimaryQualificationUseCase getCertificateByNonRenewablePrimaryQualificationUseCase;
    @Mock
    private GetCertificateByCo2QualificationUseCase getCertificateByCo2Emissions;
    @Mock
    private GetCertificateByACSQualificationUseCase getCertificateByACSQualification;
    @Mock
    private GetCertificateByHeatingQualificationUseCase getCertificateByHeatingQualification;
    @Mock
    private GetCertificateByLightingQualificationUseCase getCertificateByLightingQualification;
    @Mock
    private GetCertificateByRefrigerationQualificationUseCase getCertificateByRefrigerationQualification;
    @Mock
    private GetCertificateBySolarThermalUseCase getCertificateBySolarThermal;
    @Mock
    private GetCertificateByPhotovoltaicSolarUseCase getCertificateByPhotovoltaicSolar;
    @Mock
    private GetCertificateByEnergeticRehabilitationUseCase getCertificateByEnergeticRehabilitation;
    @Mock
    private GetCertificateByDistrictNetUseCase getCertificateByDistrictNet;
    @Mock
    private GetCertificateByElectricVehicleUseCase getCertificateByElectricVehicle;
    @Mock
    private GetCertificateByGeothermalUseCase getCertificateByGeothermal;
    @Mock
    private GetCertificateByBiomassUseCase getCertificateByBiomass;

    @InjectMocks
    private CertificateGetController certificateGetController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCertificateUseCase() {
        List<CertificateDTO> certificates = Collections.singletonList(new CertificateDTO());
        when(getAllCertificatesUseCase.execute()).thenReturn(certificates);

        ResponseEntity<List<CertificateDTO>> response = certificateGetController.getAllCertificateUseCase();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(certificates, response.getBody());
        verify(getAllCertificatesUseCase, times(1)).execute();
    }

    @Test
    void getCertificateByIdUseCase() {
        CertificateDTO certificate = new CertificateDTO();
        when(getCertificateByIdUseCase.execute(1L)).thenReturn(certificate);

        ResponseEntity<CertificateDTO> response = certificateGetController.getCertificateByIdUseCase(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(certificate, response.getBody());
        verify(getCertificateByIdUseCase, times(1)).execute(1L);
    }

    @Test
    void getCertificateByAddressUseCase() {
        List<CertificateDTO> certificates = Collections.singletonList(new CertificateDTO());
        when(getCertificateByAddressUseCase.execute(1L)).thenReturn(certificates);

        ResponseEntity<List<CertificateDTO>> response = certificateGetController.getCertificateByAddressUseCase(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(certificates, response.getBody());
        verify(getCertificateByAddressUseCase, times(1)).execute(1L);
    }

    @Test
    void getCertificateByClimateZoneUseCase() {
        List<CertificateDTO> certificates = Collections.singletonList(new CertificateDTO());
        when(getCertificateByClimateZoneUseCase.execute("ZoneA")).thenReturn(certificates);

        ResponseEntity<List<CertificateDTO>> response = certificateGetController.getCertificateByClimateZoneUseCase("ZoneA");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(certificates, response.getBody());
        verify(getCertificateByClimateZoneUseCase, times(1)).execute("ZoneA");
    }

    @Test
    void getCertificateByNonRenewablePrimaryQualificationUseCase() {
        List<CertificateDTO> certificates = Collections.singletonList(new CertificateDTO());
        Qualification qualification = Qualification.A;
        when(getCertificateByNonRenewablePrimaryQualificationUseCase.execute(qualification)).thenReturn(certificates);

        ResponseEntity<List<CertificateDTO>> response = certificateGetController.getCertificateByNonRenewablePrimaryQualificationUseCase(qualification);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(certificates, response.getBody());
        verify(getCertificateByNonRenewablePrimaryQualificationUseCase, times(1)).execute(qualification);
    }
}