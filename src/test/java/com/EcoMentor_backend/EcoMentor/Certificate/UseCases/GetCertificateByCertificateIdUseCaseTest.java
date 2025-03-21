package com.EcoMentor_backend.EcoMentor.Certificate.UseCases;

import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByCertificateIdUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class GetCertificateByCertificateIdUseCaseTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private CertificateMapper certificateMapper;

    @Mock
    private CertificateDTO certificateDTO;

    @Mock
    private CertificateDTO result;

    @InjectMocks
    private GetCertificateByCertificateIdUseCase getCertificateByCertificateIdUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() {
        Long certificateId = 1L;
        when(certificateRepository.findCertificateByCertificateId(certificateId)).thenReturn(null);
        when(certificateMapper.toDTO(any())).thenReturn(certificateDTO);

        result = getCertificateByCertificateIdUseCase.execute(certificateId);

        verify(certificateRepository, times(1)).findCertificateByCertificateId(certificateId);
        verify(certificateMapper, times(1)).toDTO(any());
    }
}