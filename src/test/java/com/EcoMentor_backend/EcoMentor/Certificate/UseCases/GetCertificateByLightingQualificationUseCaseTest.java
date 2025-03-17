package com.EcoMentor_backend.EcoMentor.Certificate.UseCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByLightingQualificationUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetCertificateByLightingQualificationUseCaseTest {

    @Mock
    private OfficialCertificateRepository certificateRepository;

    @Mock
    private CertificateMapper certificateMapper;

    @Mock
    private CertificateDTO certificateDTO;

    @Mock
    private List<CertificateDTO> result;

    @Mock
    private Qualification lightingQualification;

    @InjectMocks
    private GetCertificateByLightingQualificationUseCase useCase;

    @Test
    public void testExecute() {
        lightingQualification = Qualification.A;
        when(certificateRepository.findCertificateByLightingQualification(lightingQualification)).thenReturn(Collections.emptyList());
        when(certificateMapper.toDTO(any())).thenReturn(certificateDTO);

        result = useCase.execute(lightingQualification);

        verify(certificateRepository, times(1)).findCertificateByLightingQualification(lightingQualification);
        verify(certificateMapper, times(1)).toDTO(any());
    }
}