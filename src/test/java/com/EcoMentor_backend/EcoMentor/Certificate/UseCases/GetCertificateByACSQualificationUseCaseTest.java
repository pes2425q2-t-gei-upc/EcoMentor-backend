package com.EcoMentor_backend.EcoMentor.Certificate.UseCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByACSQualificationUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

public class GetCertificateByACSQualificationUseCaseTest {

    @Mock
    private OfficialCertificateRepository certificateRepository;

    @Mock
    private CertificateMapper certificateMapper;

    @Mock
    private Qualification acsQualification;

    @Mock
    private List<CertificateDTO> result;

    @Mock
    private CertificateDTO certificateDTO;

    @InjectMocks
    private GetCertificateByACSQualificationUseCase getCertificateByACSQualificationUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() {
        acsQualification = Qualification.A;
        when(certificateRepository.findCertificateByAcsQualification(acsQualification)).thenReturn(List.of());
        when(certificateMapper.toDTO(any())).thenReturn(certificateDTO);

        result = getCertificateByACSQualificationUseCase.execute(acsQualification);

        verify(certificateRepository, times(1)).findCertificateByAcsQualification(acsQualification);
        verify(certificateMapper, times(1)).toDTO(any());
    }
}