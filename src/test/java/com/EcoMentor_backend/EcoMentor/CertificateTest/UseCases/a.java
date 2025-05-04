package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByParameterUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

class GetCertificateByParameterUseCaseTest {

@Mock
private CertificateRepository certificateRepository;

@Mock
private CertificateMapper certificateMapper;

@InjectMocks
private GetCertificateByParameterUseCase getCertificateByParameterUseCase;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}

@Test
void executeReturnsCertificatesWithinBounds() {
    Certificate certificate = new Certificate();
    CertificateWithoutForeignEntitiesDTO dto = new CertificateWithoutForeignEntitiesDTO();
    when(certificateRepository.convertToCorrectType(anyString(), anyString())).thenReturn("correctValue");
    when(certificateRepository.findCertificateByParameter(anyString(), any(), anyDouble(), anyDouble(), anyDouble(),
            anyDouble()))
            .thenReturn(Collections.singletonList(certificate));
    when(certificateMapper.toDTOW(any(Certificate.class))).thenReturn(dto);

    List<CertificateWithoutForeignEntitiesDTO> result = getCertificateByParameterUseCase.execute("parameter",
            "value", 0.0, 10.0, 0.0, 10.0);

    assertEquals(1, result.size());
    assertEquals(dto, result.get(0));
}

@Test
void throwsExceptionWhenNoCertificatesFound() {
    when(certificateRepository.convertToCorrectType(anyString(), anyString())).thenReturn("correctValue");
    when(certificateRepository.findCertificateByParameter(anyString(), any(), anyDouble(), anyDouble(), anyDouble(),
            anyDouble()))
            .thenReturn(Collections.emptyList());

    assertThrows(ResponseStatusException.class, () -> {
        getCertificateByParameterUseCase.execute("parameter", "value", 0.0, 10.0, 0.0, 10.0);
    });
}


@Test
void throwsNotFoundWhenNullParameterAndNoCertificatesFound() {
    when(certificateRepository.convertToCorrectType(anyString(), isNull())).thenReturn(null);
    when(certificateRepository.findCertificateByParameter(anyString(), isNull(), anyDouble(), anyDouble(), anyDouble(),
            anyDouble()))
            .thenReturn(Collections.emptyList());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
        getCertificateByParameterUseCase.execute("parameter", null, 0.0, 10.0, 0.0, 10.0);
    });

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
}


@Test
void throwsNotFoundWhenEmptyParameterAndNoCertificatesFound() {
    when(certificateRepository.convertToCorrectType(anyString(), eq(""))).thenReturn("");
    when(certificateRepository.findCertificateByParameter(anyString(), eq(""), anyDouble(), anyDouble(),
            anyDouble(), anyDouble()))
            .thenReturn(Collections.emptyList());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
        getCertificateByParameterUseCase.execute("parameter", "", 0.0, 10.0, 0.0, 10.0);
    });

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
}

}
