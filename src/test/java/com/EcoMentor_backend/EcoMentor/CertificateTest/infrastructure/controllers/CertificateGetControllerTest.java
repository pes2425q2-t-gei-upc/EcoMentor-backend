package com.EcoMentor_backend.EcoMentor.CertificateTest.infrastructure.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.controllers.CertificateGetController;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.*;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class CertificateGetControllerTest {

@Mock private GetAllCertificatesUseCase getAllCertificatesUseCase;
@Mock private GetCertificateByCertificateIdUseCase getCertificateByIdUseCase;
@Mock private GetCertificateByAddressUseCase getCertificateByAddressUseCase;
@Mock private GetCertificateByParameterUseCase getCertificateByParameterUseCase;
@Mock private GetCertificateByMinMaxRangeUseCase getCertificateByMinMaxRangeUseCase;
@Mock private GetCertificateByCertificateIdWFEUseCase getCertificateByCertificateIdWithoutUseCase;

@InjectMocks
private CertificateGetController certificateGetController;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}

@Test
void testGetAllCertificateUseCase_ReturnsPage() {
    int pageNo = 0, pageSize = 10;
    String sortBy = "certificateId";
    CertificateWithoutForeignEntitiesDTO dto = new CertificateWithoutForeignEntitiesDTO();
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    Page<CertificateWithoutForeignEntitiesDTO> page = new PageImpl<>(List.of(dto), pageable, 1);

    when(getAllCertificatesUseCase.execute(pageNo, pageSize, sortBy)).thenReturn(page);

    ResponseEntity<Page<CertificateWithoutForeignEntitiesDTO>> response =
            certificateGetController.getAllCertificateUseCase(pageNo, pageSize, sortBy);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(1, response.getBody().getContent().size());
    verify(getAllCertificatesUseCase).execute(pageNo, pageSize, sortBy);
}

@Test
void testGetCertificateByIdUseCase_ReturnsCertificateDTO() {
    long certId = 1L;
    CertificateDTO dto = new CertificateDTO();

    when(getCertificateByIdUseCase.execute(certId)).thenReturn(dto);

    CertificateDTO result = certificateGetController.getCertificateByIdUseCase(certId);

    assertEquals(dto, result);
    verify(getCertificateByIdUseCase).execute(certId);
}

@Test
void testGetCertificateByAddressUseCase_ReturnsList() {
    long addressId = 5L;
    CertificateWithoutForeignEntitiesDTO dto = new CertificateWithoutForeignEntitiesDTO();

    when(getCertificateByAddressUseCase.execute(addressId)).thenReturn(List.of(dto));

    List<CertificateWithoutForeignEntitiesDTO> result =
            certificateGetController.getCertificateByAddressUseCase(addressId);

    assertEquals(1, result.size());
    assertEquals(dto, result.get(0));
    verify(getCertificateByAddressUseCase).execute(addressId);
}

@Test
void testGetCertificateByParameter_ReturnsList() {
    String param = "type";
    String value = "eco";
    double minLat = 1.0, maxLat = 2.0, minLong = 3.0, maxLong = 4.0;
    CertificateWithoutForeignEntitiesDTO dto = new CertificateWithoutForeignEntitiesDTO();

    when(getCertificateByParameterUseCase.execute(param, value, minLat, maxLat, minLong, maxLong))
            .thenReturn(List.of(dto));

    List<CertificateWithoutForeignEntitiesDTO> result = certificateGetController.getCertificateByParameter(
            param, value, minLat, maxLat, minLong, maxLong);

    assertEquals(1, result.size());
    verify(getCertificateByParameterUseCase)
            .execute(param, value, minLat, maxLat, minLong, maxLong);
}

@Test
void testGetCertificateByMinMaxRange_ReturnsList() {
    String param = "score";
    String min = "10", max = "100";
    CertificateWithoutForeignEntitiesDTO dto = new CertificateWithoutForeignEntitiesDTO();

    when(getCertificateByMinMaxRangeUseCase.execute(param, min, max)).thenReturn(List.of(dto));

    List<CertificateWithoutForeignEntitiesDTO> result =
            certificateGetController.getCertificateByMinMaxRange(param, min, max);

    assertEquals(1, result.size());
    verify(getCertificateByMinMaxRangeUseCase).execute(param, min, max);
}

@Test
void testGetCertificateByCertificateIdWithoutUseCase_ReturnsDTO() {
    long certId = 99L;
    CertificateWithoutForeignEntitiesDTO dto = new CertificateWithoutForeignEntitiesDTO();

    when(getCertificateByCertificateIdWithoutUseCase.execute(certId)).thenReturn(dto);

    CertificateWithoutForeignEntitiesDTO result =
            certificateGetController.getCertificateByCertificateIdWithoutUseCase(certId);

    assertEquals(dto, result);
    verify(getCertificateByCertificateIdWithoutUseCase).execute(certId);
}
}
