package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases;

import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.AddCertificateToAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.CreateAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.CreateCertificateUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class CreateCertificateUseCaseTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private CertificateMapper certificateMapper;

    @Mock
    private CreateAddressUseCase createAddressUseCase;

    @Mock
    private AddCertificateToAddressUseCase addCertificateToAddressUseCase;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private CreateCertificateUseCase createCertificateUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() {
        CreateCertificateDTO createCertificateDTO = mock(CreateCertificateDTO.class);
        Certificate certificate = mock(Certificate.class);

        when(certificateMapper.toEntity(createCertificateDTO)).thenReturn(certificate);
        when(addressRepository.existsAddressByAddressNameAndAddressNumber(anyString(), anyString())).thenReturn(false);
        when(createAddressUseCase.execute(any())).thenReturn(1L);

        createCertificateUseCase.execute(createCertificateDTO);

        verify(certificateRepository, times(1)).save(certificate);
        verify(addCertificateToAddressUseCase, times(1)).execute(anyLong(), anyLong());
    }
}