package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAddressByProvinceUseCase;
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
public class GetAddressByProvinceUseCaseTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private GetAddressByProvinceUseCase getAddressByProvinceUseCase;

    private Address address;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .addressId(1L)
                .addressName("Test Address")
                .addressNumber("123")
                .province("Test Province")
                .build();

        addressDTO = AddressDTO.builder()
                .addressId(1L)
                .addressName("Test Address")
                .addressNumber("123")
                .province("Test Province")
                .build();
    }

    @Test
    void shouldReturnEmptyListWhenNoAddressesFound() {
        when(addressRepository.findByProvince("Nonexistent Province")).thenReturn(Collections.emptyList());

        List<AddressDTO> result = getAddressByProvinceUseCase.execute("Nonexistent Province");

        assertEquals(Collections.emptyList(), result);
        verify(addressRepository).findByProvince("Nonexistent Province");
        verifyNoMoreInteractions(addressRepository, addressMapper);
    }

    @Test
    void shouldReturnAddressDTOListWhenAddressesFound() {
        when(addressRepository.findByProvince("Test Province")).thenReturn(Arrays.asList(address));
        when(addressMapper.toDTO(address)).thenReturn(addressDTO);

        List<AddressDTO> result = getAddressByProvinceUseCase.execute("Test Province");

        assertEquals(Arrays.asList(addressDTO), result);
        verify(addressRepository).findByProvince("Test Province");
        verify(addressMapper).toDTO(address);
        verifyNoMoreInteractions(addressRepository, addressMapper);
    }
}