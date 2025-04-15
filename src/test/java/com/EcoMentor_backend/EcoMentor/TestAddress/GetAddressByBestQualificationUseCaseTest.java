package com.EcoMentor_backend.EcoMentor.TestAddress;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAddressByBestQualificationUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTOBestQualification;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAddressByBestQualificationUseCaseTest {

@Mock
private AddressRepository addressRepository;

@Mock
private AddressMapper addressMapper;

@InjectMocks
private GetAddressByBestQualificationUseCase useCase;

@Test
void shouldThrowNotFoundWhenTownHasNoAddresses() {
    String town = "NonExistentTown";

    when(addressRepository.findByTown(town)).thenReturn(Collections.emptyList());

    assertThrows(ResponseStatusException.class, () ->
            useCase.execute(town, null, null, null, null)
    );
}

@Test
void shouldThrowNotFoundWhenZipcodeHasNoAddresses() {
    Integer zipcode = 99999;

    when(addressRepository.findByZipcode(zipcode)).thenReturn(Collections.emptyList());

    assertThrows(ResponseStatusException.class, () ->
            useCase.execute(null, zipcode, null, null, null)
    );
}

@Test
void shouldThrowNotFoundWhenLatitudeLongitudeRadiusHasNoAddresses() {
    Double latitude = 41.0;
    Double longitude = 2.0;
    Integer radius = 5;

    when(addressRepository.findAddressesWithinDistance(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.anyDouble()
    )).thenReturn(Collections.emptyList());

    assertThrows(ResponseStatusException.class, () ->
            useCase.execute(null, null, latitude, longitude, radius)
    );
}

@Test
void shouldThrowNotFoundWhenNoCriteriaProvided() {
    assertThrows(ResponseStatusException.class, () ->
            useCase.execute(null, null, null, null, null)
    );
}

}
