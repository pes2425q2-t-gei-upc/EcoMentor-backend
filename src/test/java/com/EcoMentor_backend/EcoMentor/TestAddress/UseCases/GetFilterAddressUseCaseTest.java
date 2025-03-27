package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetFilterAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTOSimple;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import org.junit.jupiter.api.Assertions;
import java.util.Collections;
import java.util.List;


public class GetFilterAddressUseCaseTest {

@Mock
private AddressRepository addressRepository;

@Mock
private AddressMapper addressMapper;

@Mock
private CertificateRepository certificateRepository;

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
    Mockito.when(certificateRepository.convertToCorrectType(Mockito.anyString(), Mockito.anyString())).
            thenReturn("correctValue");
    Mockito.when(addressRepository.findAddresByCertificateByParameter(Mockito.anyString(), Mockito.any(),
                    Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyDouble()))
            .thenReturn(Collections.singletonList(address));
    Mockito.when(addressMapper.toDTOSimple(Mockito.any(Address.class))).thenReturn(dto);

    List<AddressDTOSimple> result = getFilterAddressUseCase.execute("parameter", "value", 0.0,
            10.0, 0.0, 10.0);

    Assertions.assertEquals(1, result.size());
    Assertions.assertEquals(dto, result.get(0));
}

@Test
void executeReturnsEmptyListWhenNoAddressesFound() {
    Mockito.when(certificateRepository.convertToCorrectType(Mockito.anyString(), Mockito.anyString()))
            .thenReturn("correctValue");
    Mockito.when(addressRepository.findAddresByCertificateByParameter(Mockito.anyString(), Mockito.any(),
                    Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyDouble()))
            .thenReturn(Collections.emptyList());

    List<AddressDTOSimple> result = getFilterAddressUseCase.execute("parameter", "value", 0.0,
            10.0, 0.0, 10.0);

    Assertions.assertTrue(result.isEmpty());
}

@Test
void executeHandlesNullParameter() {
    Mockito.when(certificateRepository.convertToCorrectType(Mockito.anyString(), Mockito.isNull())).thenReturn(null);
    Mockito.when(addressRepository.findAddresByCertificateByParameter(Mockito.anyString(), Mockito.isNull(),
                    Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyDouble()))
            .thenReturn(Collections.emptyList());

    List<AddressDTOSimple> result = getFilterAddressUseCase.execute("parameter", null, 0.0,
            10.0, 0.0, 10.0);

    Assertions.assertTrue(result.isEmpty());
}

@Test
void executeHandlesEmptyParameter() {
    Mockito.when(certificateRepository.convertToCorrectType(Mockito.anyString(), Mockito.eq("")))
            .thenReturn("");
    Mockito.when(addressRepository.findAddresByCertificateByParameter(Mockito.anyString(), Mockito.eq(""),
                    Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyDouble()))
            .thenReturn(Collections.emptyList());

    List<AddressDTOSimple> result = getFilterAddressUseCase.execute("parameter", "", 0.0,
            10.0, 0.0, 10.0);



    Assertions.assertTrue(result.isEmpty());
}

@Test
void executeHandlesNullValue() {
    Mockito.when(certificateRepository.convertToCorrectType(Mockito.anyString(), Mockito.isNull())).thenReturn(null);
    Mockito.when(addressRepository.findAddresByCertificateByParameter(Mockito.anyString(), Mockito.isNull(),
                    Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyDouble()))
            .thenReturn(Collections.emptyList());

    List<AddressDTOSimple> result = getFilterAddressUseCase.execute("parameter", null, 0.0,
            10.0, 0.0, 10.0);

    Assertions.assertTrue(result.isEmpty());
}

@Test
void executeHandlesEmptyValue() {
    Mockito.when(certificateRepository.convertToCorrectType(Mockito.anyString(), Mockito.eq("")))
            .thenReturn("");
    Mockito.when(addressRepository.findAddresByCertificateByParameter(Mockito.anyString(), Mockito.eq(""),
                    Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyDouble()))
            .thenReturn(Collections.emptyList());

    List<AddressDTOSimple> result = getFilterAddressUseCase.execute("parameter", "", 0.0,
            10.0, 0.0, 10.0);

    Assertions.assertTrue(result.isEmpty());
}
}
