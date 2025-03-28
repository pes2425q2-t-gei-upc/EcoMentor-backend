package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAddressByAddressIdUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAddressByAddressIdUseCaseTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private GetAddressByAddressIdUseCase getAddressByAddressIdUseCase;

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
    void shouldThrowExceptionWhenAddressNotFound() {
        when(addressRepository.findByAddressId(1L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> getAddressByAddressIdUseCase.execute(1L));

        verify(addressRepository).findByAddressId(1L);
        verifyNoMoreInteractions(addressRepository, addressMapper);
    }

    @Test
    void shouldReturnAddressDTOWhenAddressExists() {
        when(addressRepository.findByAddressId(1L)).thenReturn(address);
        when(addressMapper.toDTO(address)).thenReturn(addressDTO);

        AddressDTO result = getAddressByAddressIdUseCase.execute(1L);

        assertEquals(addressDTO, result);
        verify(addressRepository).findByAddressId(1L);
        verify(addressMapper).toDTO(address);
        verifyNoMoreInteractions(addressRepository, addressMapper);
    }
}
