package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByMinMaxRangeUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class GetCertificateByMinMaxRangeUseCaseTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private CertificateMapper certificateMapper;

    @InjectMocks
    private GetCertificateByMinMaxRangeUseCase getCertificateByMinMaxRangeUseCase;

    @Mock
    private Certificate certificate;

    @Mock
    private CertificateWithoutForeignEntitiesDTO certificateWithoutForeignEntitiesDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testThrowsNotFoundWhenNoCertificatesInRange() {
        when(certificateRepository.convertToCorrectType("parameter", "min")).thenReturn(1);
        when(certificateRepository.convertToCorrectType("parameter", "max")).thenReturn(10);
        when(certificateRepository.findCertificateByMinMaxRange("parameter", 1, 10))
                .thenReturn(Collections.emptyList());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            getCertificateByMinMaxRangeUseCase.execute("parameter", "min", "max");
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

}