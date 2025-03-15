package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;


import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.DeleteAddressUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteAddressUseCaseTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private DeleteAddressUseCase deleteAddressUseCase;

    private Address address;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .addressId(1L)
                .addressName("Test Address")
                .addressNumber("123")
                .build();
    }

    @Test
    void shouldThrowExceptionWhenAddressNotFound() {
        when(addressRepository.findByAddressId(1L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> deleteAddressUseCase.execute(1L));

        verify(addressRepository).findByAddressId(1L);
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    void shouldDeleteAddressWhenAddressExists() {
        when(addressRepository.findByAddressId(1L)).thenReturn(address);

        deleteAddressUseCase.execute(1L);

        verify(addressRepository).findByAddressId(1L);
        verify(addressRepository).delete(address);
        verifyNoMoreInteractions(addressRepository);
    }
}
