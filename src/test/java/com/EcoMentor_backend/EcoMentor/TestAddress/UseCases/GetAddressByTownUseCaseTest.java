package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAddressByTownUseCase;
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
public class GetAddressByTownUseCaseTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private GetAddressByTownUseCase getAddressByTownUseCase;

    private Address address;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .addressId(1L)
                .addressName("Test Address")
                .addressNumber("123")
                .town("Test Town")
                .build();

        addressDTO = AddressDTO.builder()
                .addressId(1L)
                .addressName("Test Address")
                .addressNumber("123")
                .town("Test Town")
                .build();
    }

    @Test
    void shouldReturnEmptyListWhenNoAddressesFound() {
        when(addressRepository.findByTown("Nonexistent Town")).thenReturn(Collections.emptyList());

        List<AddressDTO> result = getAddressByTownUseCase.execute("Nonexistent Town");

        assertEquals(Collections.emptyList(), result);
        verify(addressRepository).findByTown("Nonexistent Town");
        verifyNoMoreInteractions(addressRepository, addressMapper);
    }

    @Test
    void shouldReturnAddressDTOListWhenAddressesFound() {
        when(addressRepository.findByTown("Test Town")).thenReturn(Arrays.asList(address));
        when(addressMapper.toDTO(address)).thenReturn(addressDTO);

        List<AddressDTO> result = getAddressByTownUseCase.execute("Test Town");

        assertEquals(Arrays.asList(addressDTO), result);
        verify(addressRepository).findByTown("Test Town");
        verify(addressMapper).toDTO(address);
        verifyNoMoreInteractions(addressRepository, addressMapper);
    }
}