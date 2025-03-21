package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByCertificateIdUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetCertificateByCertificateIdUseCaseTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private CertificateMapper certificateMapper;

    @InjectMocks
    private GetCertificateByCertificateIdUseCase getCertificateByCertificateIdUseCase;

    @Mock
    private Certificate certificate;

    @Mock
    private CertificateDTO certificateDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() {

        when(certificateRepository.findCertificateByCertificateId(1L)).thenReturn(certificate);
        when(certificateMapper.toDTO(certificate)).thenReturn(certificateDTO);

        CertificateDTO result = getCertificateByCertificateIdUseCase.execute(1L);

        assertNotNull(result);
        verify(certificateRepository, times(1)).findCertificateByCertificateId(1L);
    }

    @Test
    public void testExecuteCertificateNotFound() {
        when(certificateRepository.findCertificateByCertificateId(1L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> getCertificateByCertificateIdUseCase.execute(1L));
    }
}