package com.EcoMentor_backend.EcoMentor.Certificate.UseCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByCo2QualificationUseCase;
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
public class GetCertificateByCo2QualificationUseCaseTest {

    @Mock
    private OfficialCertificateRepository certificateRepository;

    @Mock
    private CertificateMapper certificateMapper;

    @Mock
    private Qualification co2Qualification;

    @Mock
    private CertificateDTO certificateDTO;

    @Mock
    private List<CertificateDTO> result;

    @InjectMocks
    private GetCertificateByCo2QualificationUseCase useCase;

    @Test
    public void testExecute() {
        co2Qualification = Qualification.A;
        when(certificateRepository.findCertificateByCo2Qualification(co2Qualification)).thenReturn(Collections.emptyList());
        when(certificateMapper.toDTO(any())).thenReturn(certificateDTO);

        result = useCase.execute(co2Qualification);

        verify(certificateRepository, times(1)).findCertificateByCo2Qualification(co2Qualification);
        verify(certificateMapper, times(1)).toDTO(any());
    }
}