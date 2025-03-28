package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAddressByBoundingBoxUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTOSimple;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GetAddressByBoundingBoxUseCaseTest {

@Mock
private AddressRepository addressRepository;

@Mock
private AddressMapper addressMapper;

@InjectMocks
private GetAddressByBoundingBoxUseCase getAddressByBoundingBoxUseCase;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}

@Test
void executeReturnsAddressesWithinBoundingBox() {
    Address address = new Address();
    AddressDTOSimple addressDTOSimple = new AddressDTOSimple();
    when(addressRepository.findAddressesWithinBoundingBox(10.0, 20.0, 30.0, 40.0))
            .thenReturn(Collections.singletonList(address));
    when(addressMapper.toDTOSimple(address)).thenReturn(addressDTOSimple);

    List<AddressDTOSimple> result = getAddressByBoundingBoxUseCase.execute(10.0, 20.0, 30.0, 40.0);

    assertEquals(1, result.size());
    assertEquals(addressDTOSimple, result.get(0));
}

@Test
void executeReturnsEmptyListWhenNoAddressesFound() {
    when(addressRepository.findAddressesWithinBoundingBox(10.0, 20.0, 30.0, 40.0))
            .thenReturn(Collections.emptyList());

    List<AddressDTOSimple> result = getAddressByBoundingBoxUseCase.execute(10.0, 20.0, 30.0, 40.0);

    assertEquals(0, result.size());
}

@Test
void executeHandlesNegativeCoordinates() {
    Address address = new Address();
    AddressDTOSimple addressDTOSimple = new AddressDTOSimple();
    when(addressRepository.findAddressesWithinBoundingBox(-10.0, -5.0, -20.0, -15.0))
            .thenReturn(Collections.singletonList(address));
    when(addressMapper.toDTOSimple(address)).thenReturn(addressDTOSimple);

    List<AddressDTOSimple> result = getAddressByBoundingBoxUseCase.execute(-10.0, -5.0, -20.0, -15.0);

    assertEquals(1, result.size());
    assertEquals(addressDTOSimple, result.get(0));
}

@Test
void executeHandlesZeroCoordinates() {
    Address address = new Address();
    AddressDTOSimple addressDTOSimple = new AddressDTOSimple();
    when(addressRepository.findAddressesWithinBoundingBox(0.0, 0.0, 0.0, 0.0))
            .thenReturn(Collections.singletonList(address));
    when(addressMapper.toDTOSimple(address)).thenReturn(addressDTOSimple);

    List<AddressDTOSimple> result = getAddressByBoundingBoxUseCase.execute(0.0, 0.0, 0.0, 0.0);

    assertEquals(1, result.size());
    assertEquals(addressDTOSimple, result.get(0));
}
}