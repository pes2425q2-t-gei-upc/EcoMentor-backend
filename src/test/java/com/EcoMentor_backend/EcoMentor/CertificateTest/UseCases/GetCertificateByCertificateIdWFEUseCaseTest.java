package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByCertificateIdWFEUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



public class GetCertificateByCertificateIdWFEUseCaseTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private CertificateMapper certificateMapper;

    @InjectMocks
    private GetCertificateByCertificateIdWFEUseCase getCertificateByCertificateIdWFEUseCase;

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
        when(certificateRepository.findCertificateByCertificateId(1L)).thenReturn(certificate);
        when(certificateMapper.toDTOW(certificate)).thenReturn(certificateWithoutForeignEntitiesDTO);

        CertificateWithoutForeignEntitiesDTO result = getCertificateByCertificateIdWFEUseCase.execute(1L);

        assertNotNull(result);
        verify(certificateRepository, times(1)).findCertificateByCertificateId(1L);
        verify(certificateMapper, times(1)).toDTOW(certificate);
    }

    @Test
    public void testExecuteCertificateNotFound() {
        when(certificateRepository.findCertificateByCertificateId(1L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> getCertificateByCertificateIdWFEUseCase.execute(1L));
    }
}