package com.EcoMentor_backend.EcoMentor.TestAddress.infraestructure.controllers;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.CreateAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateAddressUseCaseTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private CreateAddressUseCase createAddressUseCase;

    private CreateAddressDTO createAddressDTO;

    @BeforeEach
    void setUp() {
        createAddressDTO = CreateAddressDTO.builder()
                .addressName("Test Address")
                .addressNumber("123")
                .zipcode(12345)
                .town("Test Town")
                .region("Test Region")
                .province("Test Province")
                .longitude(1.0f)
                .latitude(1.0f)
                .certificates(Arrays.asList(1L, 2L))
                .build();
    }

    @Test
    void shouldThrowExceptionWhenAddressAlreadyExists() {
        when(addressRepository.existsAddressByAddressNameAndAddressNumber(anyString(), anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> createAddressUseCase.execute(createAddressDTO));

        verify(addressRepository).existsAddressByAddressNameAndAddressNumber(anyString(), anyString());
        verifyNoMoreInteractions(addressRepository, certificateRepository, addressMapper);
    }


}