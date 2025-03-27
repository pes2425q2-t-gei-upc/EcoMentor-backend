package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetFilterAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTOSimple;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;

class GetFilterAddressUseCaseTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private GetFilterAddressUseCase getFilterAddressUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executeReturnsAddressesWithinBounds() {
        Address address = new Address();
        AddressDTOSimple dto = new AddressDTOSimple();
        when(addressRepository.findAddresByCertificateByParameter(anyString(), any(), anyDouble(), anyDouble(),
                anyDouble(),
                anyDouble()))
                .thenReturn(Collections.singletonList(address));
        when(addressMapper.toDTOSimple(any(Address.class))).thenReturn(dto);

        List<AddressDTOSimple> result = getFilterAddressUseCase.execute("parameter", "value",
                0.0,
                10.0, 0.0, 10.0);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void executeReturnsEmptyListWhenNoAddressesFound() {
        when(addressRepository.findAddresByCertificateByParameter(anyString(), any(), anyDouble(), anyDouble(),
                anyDouble(),
                anyDouble()))
                .thenReturn(Collections.emptyList());

        List<AddressDTOSimple> result = getFilterAddressUseCase.execute("parameter", "value",
                0.0,
                10.0, 0.0, 10.0);

        assertTrue(result.isEmpty());
    }

    @Test
    void executeHandlesNullParameter() {
        when(addressRepository.findAddresByCertificateByParameter(anyString(), isNull(), anyDouble(), anyDouble(),
                anyDouble(), anyDouble()))
                .thenReturn(Collections.emptyList());

        List<AddressDTOSimple> result = getFilterAddressUseCase.execute("parameter", null, 0.0,
                10.0, 0.0, 10.0);

        assertTrue(result.isEmpty());
    }


}

