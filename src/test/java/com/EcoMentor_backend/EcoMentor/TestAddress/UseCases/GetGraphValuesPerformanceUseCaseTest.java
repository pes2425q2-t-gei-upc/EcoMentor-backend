package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetGraphValuesPerformanceUseCase;
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

class GetGraphValuesPerformanceUseCaseTest {

@Mock
private AddressRepository addressRepository;

@InjectMocks
private GetGraphValuesPerformanceUseCase useCase;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}

@Test
void execute_whenTownIsProvided_shouldFetchByTown() {
    // Arrange
    Address address = createAddressWithCertificate(100f, 50f, 75f, 1200f);
    when(addressRepository.findByTown("TestTown")).thenReturn(List.of(address));

    // Act
    List<GraphicDTO> result = useCase.execute("TestTown", null, null);

    // Assert
    assertEquals(4, result.size());
    assertEquals("nonRenewablePrimaryEnergy", result.get(0).getLabel());
    assertEquals(100f, result.get(0).getValue());
    assertEquals("co2Emissions", result.get(1).getLabel());
    assertEquals(50f, result.get(1).getValue());
    assertEquals("finalEnergyConsumption", result.get(2).getLabel());
    assertEquals(75f, result.get(2).getValue());
    assertEquals("annualCost", result.get(3).getLabel());
    assertEquals(1200f, result.get(3).getValue());
    verify(addressRepository, times(1)).findByTown("TestTown");
}

@Test
void execute_whenRegionIsProvided_shouldFetchByProvince() {
    // Arrange
    Address address = createAddressWithCertificate(80f, 40f, 60f, 1000f);
    when(addressRepository.findByProvince("TestRegion")).thenReturn(List.of(address));

    // Act
    List<GraphicDTO> result = useCase.execute(null, "TestRegion", null);

    // Assert
    assertEquals(80f, result.get(0).getValue());
    verify(addressRepository, times(1)).findByProvince("TestRegion");
}

@Test
void execute_whenProvinceIsProvided_shouldFetchByProvince() {
    // Arrange
    Address address = createAddressWithCertificate(70f, 35f, 55f, 900f);
    when(addressRepository.findByProvince("TestProvince")).thenReturn(List.of(address));

    // Act
    List<GraphicDTO> result = useCase.execute(null, null, "TestProvince");

    // Assert
    assertEquals(70f, result.get(0).getValue());
    verify(addressRepository, times(1)).findByProvince("TestProvince");
}

@Test
void execute_whenNoParameters_shouldFetchAll() {
    // Arrange
    Address address = createAddressWithCertificate(60f, 30f, 45f, 800f);
    when(addressRepository.findAll()).thenReturn(List.of(address));

    // Act
    List<GraphicDTO> result = useCase.execute(null, null, null);

    // Assert
    assertEquals(60f, result.get(0).getValue());
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
private Address createAddressWithCertificate(float nonRenewablePrimaryEnergy, float co2Emissions, float finalEnergyConsumption, float annualCost) {
    OfficialCertificate certificate = new OfficialCertificate();
    certificate.setCertificateType(CertificateType.OFFICIAL);
    certificate.setNonRenewablePrimaryEnergy(nonRenewablePrimaryEnergy);
    certificate.setCo2Emissions(co2Emissions);
    certificate.setFinalEnergyConsumption(finalEnergyConsumption);
    certificate.setAnnualCost(annualCost);

    Address address = new Address();
    address.setCertificates(List.of(certificate));

    return address;
}
}
