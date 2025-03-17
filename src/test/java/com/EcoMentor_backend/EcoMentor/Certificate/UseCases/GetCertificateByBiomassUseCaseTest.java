package com.EcoMentor_backend.EcoMentor.Certificate.UseCases;

import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByBiomassUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

public class GetCertificateByBiomassUseCaseTest {

    @Mock
    private OfficialCertificateRepository certificateRepository;

    @Mock
    private CertificateMapper certificateMapper;

    @Mock
    List<CertificateDTO> result;

    @Mock
    CertificateDTO certificateDTO;

    @InjectMocks
    private GetCertificateByBiomassUseCase getCertificateByBiomassUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() {
        boolean biomass = true;
        when(certificateRepository.findCertificateByBiomass(biomass)).thenReturn(List.of());
        when(certificateMapper.toDTO(any())).thenReturn(certificateDTO);

        result = getCertificateByBiomassUseCase.execute(biomass);

        verify(certificateRepository, times(1)).findCertificateByBiomass(biomass);
        verify(certificateMapper, times(1)).toDTO(any());
    }
}