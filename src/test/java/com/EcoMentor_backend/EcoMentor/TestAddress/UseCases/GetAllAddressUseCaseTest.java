package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAllAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAllAddressUseCaseTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private GetAllAddressUseCase getAllAddressUseCase;

    private Address address;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .addressId(1L)
                .addressName("Test Address")
                .addressNumber("123")
                .build();

        addressDTO = AddressDTO.builder()
                .addressId(1L)
                .addressName("Test Address")
                .addressNumber("123")
                .build();
    }

    @Test
    void shouldReturnEmptyListWhenNoAddressesFound() {
        when(addressRepository.findAll()).thenReturn(Collections.emptyList());

        List<AddressDTO> result = getAllAddressUseCase.execute();

        assertEquals(Collections.emptyList(), result);
        verify(addressRepository).findAll();
        verifyNoMoreInteractions(addressRepository, addressMapper);
    }

    @Test
    void shouldReturnAddressDTOListWhenAddressesFound() {
        when(addressRepository.findAll()).thenReturn(Arrays.asList(address));
        when(addressMapper.toDTO(address)).thenReturn(addressDTO);

        List<AddressDTO> result = getAllAddressUseCase.execute();

        assertEquals(Arrays.asList(addressDTO), result);
        verify(addressRepository).findAll();
        verify(addressMapper).toDTO(address);
        verifyNoMoreInteractions(addressRepository, addressMapper);
    }
}