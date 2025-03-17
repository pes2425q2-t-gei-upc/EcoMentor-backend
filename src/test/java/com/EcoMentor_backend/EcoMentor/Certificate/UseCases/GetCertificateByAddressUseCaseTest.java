package com.EcoMentor_backend.EcoMentor.Certificate.UseCases;

import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

public class GetCertificateByAddressUseCaseTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private CertificateMapper certificateMapper;

    @Mock
    private List<CertificateDTO> result;

    @Mock
    private CertificateDTO certificateDTO;

    @InjectMocks
    private GetCertificateByAddressUseCase getCertificateByAddressUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() {
        Long addressId = 1L;
        when(certificateRepository.findCertificateByAddress_AddressId(addressId)).thenReturn(List.of());
        when(certificateMapper.toDTO(any())).thenReturn(certificateDTO);

        result = getCertificateByAddressUseCase.execute(addressId);

        verify(certificateRepository, times(1)).findCertificateByAddress_AddressId(addressId);
        verify(certificateMapper, times(1)).toDTO(any());
    }
}