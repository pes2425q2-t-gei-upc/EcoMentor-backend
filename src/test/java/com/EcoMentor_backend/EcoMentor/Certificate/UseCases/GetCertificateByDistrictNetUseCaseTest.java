package com.EcoMentor_backend.EcoMentor.Certificate.UseCases;

import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByDistrictNetUseCase;
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
public class GetCertificateByDistrictNetUseCaseTest {

    @Mock
    private OfficialCertificateRepository certificateRepository;

    @Mock
    private CertificateMapper certificateMapper;

    @Mock
    private CertificateDTO certificateDTO;

    @Mock
    private List<CertificateDTO> result;

    @InjectMocks
    private GetCertificateByDistrictNetUseCase useCase;

    @Test
    public void testExecute() {
        boolean districtNet = true;
        when(certificateRepository.findCertificateByDistrictNet(districtNet)).thenReturn(Collections.emptyList());
        when(certificateMapper.toDTO(any())).thenReturn(certificateDTO);

        result = useCase.execute(districtNet);

        verify(certificateRepository, times(1)).findCertificateByDistrictNet(districtNet);
        verify(certificateMapper, times(1)).toDTO(any());
    }
}