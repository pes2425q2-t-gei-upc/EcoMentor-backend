package com.EcoMentor_backend.EcoMentor.Certificate.UseCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.DeleteCertificateUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

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
        Long certificateId = 1L;
        when(certificateRepository.findCertificateByCertificateId(certificateId)).thenReturn(certificate);
        deleteCertificateUseCase.execute(certificateId);
        verify(certificateRepository, times(1)).delete(any());
    }
}