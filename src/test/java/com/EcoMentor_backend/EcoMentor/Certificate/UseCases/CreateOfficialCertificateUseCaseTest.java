package com.EcoMentor_backend.EcoMentor.Certificate.UseCases;


import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.CreateOfficialCertificateUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateOfficialCertificateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class CreateOfficialCertificateUseCaseTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private OfficialCertificateRepository officialCertificateRepository;

    @Mock
    private CreateOfficialCertificateDTO createOfficialCertificateDTO;

    @InjectMocks
    private CreateOfficialCertificateUseCase createOfficialCertificateUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() {
        when(officialCertificateRepository.existsOfficialCertificateByDocumentId(anyString())).thenReturn(false);
        createOfficialCertificateUseCase.execute(createOfficialCertificateDTO);
        verify(certificateRepository, times(1)).save(any());
    }
}