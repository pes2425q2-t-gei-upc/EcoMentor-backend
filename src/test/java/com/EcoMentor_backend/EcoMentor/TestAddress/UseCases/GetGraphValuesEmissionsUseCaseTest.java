package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetGraphValuesEmissionsUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.GraphicDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetGraphValuesEmissionsUseCaseTest {

@Mock
private AddressRepository addressRepository;

@InjectMocks
private GetGraphValuesEmissionsUseCase useCase;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}

@Test
void execute_whenTownIsProvided_shouldFetchByTown() {
    // Arrange
    Address address = createAddressWithCertificate(10f, 20f, 30f, 40f);
    when(addressRepository.findByTown("TestTown")).thenReturn(List.of(address));

    // Act
    List<GraphicDTO> result = useCase.execute("TestTown", null, null);

    // Assert
    assertEquals(4, result.size());
    assertEquals("heatingEmissions", result.get(0).getLabel());
    assertEquals(10f, result.get(0).getValue());
    verify(addressRepository, times(1)).findByTown("TestTown");
}

@Test
void execute_whenRegionIsProvided_shouldFetchByProvince() {
    // Arrange
    Address address = createAddressWithCertificate(5f, 10f, 15f, 20f);
    when(addressRepository.findByProvince("TestRegion")).thenReturn(List.of(address));

    // Act
    List<GraphicDTO> result = useCase.execute(null, "TestRegion", null);

    // Assert
    assertEquals(5f, result.get(0).getValue());
    verify(addressRepository, times(1)).findByProvince("TestRegion");
}

@Test
void execute_whenProvinceIsProvided_shouldFetchByProvince() {
    // Arrange
    Address address = createAddressWithCertificate(2f, 4f, 6f, 8f);
    when(addressRepository.findByProvince("TestProvince")).thenReturn(List.of(address));

    // Act
    List<GraphicDTO> result = useCase.execute(null, null, "TestProvince");

    // Assert
    assertEquals(2f, result.get(0).getValue());
    verify(addressRepository, times(1)).findByProvince("TestProvince");
}

@Test
void execute_whenNoParameters_shouldFetchAll() {
    // Arrange
    Address address = createAddressWithCertificate(1f, 2f, 3f, 4f);
    when(addressRepository.findAll()).thenReturn(List.of(address));

    // Act
    List<GraphicDTO> result = useCase.execute(null, null, null);

    // Assert
    assertEquals(1f, result.get(0).getValue());
    verify(addressRepository, times(1)).findAll();
}

@Test
void execute_whenNoAddressesFound_shouldThrowException() {
    // Arrange
    when(addressRepository.findByTown("EmptyTown")).thenReturn(Collections.emptyList());

    // Act & Assert
    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
        useCase.execute("EmptyTown", null, null);
    });

    assertEquals(404, exception.getStatusCode().value());
    assertTrue(exception.getReason().contains("No certificates found"));
}

// Helper method to create an Address with one OfficialCertificate
private Address createAddressWithCertificate(float heating, float refrigeration, float acs, float lighting) {
    OfficialCertificate certificate = new OfficialCertificate();
    certificate.setCertificateType(CertificateType.OFFICIAL);
    certificate.setHeatingEmissions(heating);
    certificate.setRefrigerationEmissions(refrigeration);
    certificate.setAcsEmissions(acs);
    certificate.setLightingEmissions(lighting);

    Address address = new Address();
    address.setCertificates(List.of(certificate));

    return address;
}
}
