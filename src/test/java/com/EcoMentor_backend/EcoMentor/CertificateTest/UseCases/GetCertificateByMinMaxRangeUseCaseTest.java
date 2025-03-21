package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByMinMaxRangeUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
    public void testExecute() {
        List<Certificate> certificates = new ArrayList<>();
        List<CertificateWithoutForeignEntitiesDTO> certificateDTOS = new ArrayList<>();

        when(certificateRepository.convertToCorrectType("parameter", "min")).thenReturn(1);
        when(certificateRepository.convertToCorrectType("parameter", "max")).thenReturn(10);
        when(certificateRepository.findCertificateByMinMaxRange("parameter", 1, 10)).thenReturn(certificates);
        when(certificateMapper.toDTOW(certificate)).thenReturn(certificateWithoutForeignEntitiesDTO);

        List<CertificateWithoutForeignEntitiesDTO> result = getCertificateByMinMaxRangeUseCase.execute("parameter", "min", "max");

        assertEquals(certificateDTOS.size(), result.size());
        verify(certificateRepository, times(1)).findCertificateByMinMaxRange("parameter", 1, 10);
    }
}