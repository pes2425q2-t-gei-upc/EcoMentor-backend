package com.EcoMentor_backend.EcoMentor.Certificate.UseCases;

import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetAllCertificatesUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class GetAllCertificatesUseCaseTest {

    @Mock
    private CertificateRepository certificateRepository;

    @InjectMocks
    private GetAllCertificatesUseCase getAllCertificatesUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() {
        getAllCertificatesUseCase.execute();
        verify(certificateRepository, times(1)).findAll();
    }
}