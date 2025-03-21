package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.DeleteCertificateUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



public class DeleteCertificateUseCaseTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private Certificate certificate;

    @InjectMocks
    private DeleteCertificateUseCase deleteCertificateUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() {
        when(certificateRepository.findCertificateByCertificateId(1L)).thenReturn(certificate);

        deleteCertificateUseCase.execute(1L);

        verify(certificateRepository, times(1)).delete(certificate);
    }

    @Test
    public void testExecuteCertificateNotFound() {
        when(certificateRepository.findCertificateByCertificateId(1L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> deleteCertificateUseCase.execute(1L));
    }
}