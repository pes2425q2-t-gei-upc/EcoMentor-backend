package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetGraphValuesRenewableUseCase;
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

class GetGraphValuesRenewableUseCaseTest {

@Mock
private AddressRepository addressRepository;

@InjectMocks
private GetGraphValuesRenewableUseCase useCase;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}

@Test
void execute_whenTownIsProvided_shouldCalculateRenewables() {
    // Arrange
    Address address = createAddress(true, false, true);
    when(addressRepository.findByTown("TestTown")).thenReturn(List.of(address));

    // Act
    List<GraphicDTO> result = useCase.execute("TestTown", null, null);

    // Assert
    assertEquals(3, result.size());
    assertEquals("biomass", result.get(0).getLabel());
    assertEquals(1.0f, result.get(0).getValue());
    assertEquals("districtNet", result.get(1).getLabel());
    assertEquals(0.0f, result.get(1).getValue());
    assertEquals("geothermal", result.get(2).getLabel());
    assertEquals(1.0f, result.get(2).getValue());
    verify(addressRepository, times(1)).findByTown("TestTown");
}

@Test
void execute_whenRegionIsProvided_shouldCalculateRenewables() {
    // Arrange
    Address address = createAddress(false, true, false);
    when(addressRepository.findByProvince("TestRegion")).thenReturn(List.of(address));

    // Act
    List<GraphicDTO> result = useCase.execute(null, "TestRegion", null);

    // Assert
    assertEquals(1.0f, result.get(1).getValue()); // districtNet
    verify(addressRepository, times(1)).findByProvince("TestRegion");
}

@Test
void execute_whenProvinceIsProvided_shouldCalculateRenewables() {
    // Arrange
    Address address = createAddress(false, false, true);
    when(addressRepository.findByProvince("TestProvince")).thenReturn(List.of(address));

    // Act
    List<GraphicDTO> result = useCase.execute(null, null, "TestProvince");

    // Assert
    assertEquals(1.0f, result.get(2).getValue()); // geothermal
    verify(addressRepository, times(1)).findByProvince("TestProvince");
}

@Test
void execute_whenNoParameters_shouldFetchAllAddresses() {
    // Arrange
    Address address = createAddress(true, true, false);
    when(addressRepository.findAll()).thenReturn(List.of(address));

    // Act
    List<GraphicDTO> result = useCase.execute(null, null, null);

    // Assert
    assertEquals(1.0f, result.get(0).getValue()); // biomass
    assertEquals(1.0f, result.get(1).getValue()); // districtNet
    assertEquals(0.0f, result.get(2).getValue()); // geothermal
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
    assertEquals("No certificates found", exception.getReason());
}

@Test
void execute_withMultipleAddresses_shouldAverageCorrectly() {
    // Arrange
    Address address1 = createAddress(true, false, true);
    Address address2 = createAddress(false, true, false);
    when(addressRepository.findAll()).thenReturn(List.of(address1, address2));

    // Act
    List<GraphicDTO> result = useCase.execute(null, null, null);

    // Assert
    assertEquals(0.5f, result.get(0).getValue()); // biomass: 1/2
    assertEquals(0.5f, result.get(1).getValue()); // districtNet: 1/2
    assertEquals(0.5f, result.get(2).getValue()); // geothermal: 1/2
    verify(addressRepository, times(1)).findAll();
}

// Helper para crear Address con una OfficialCertificate configurada
private Address createAddress(boolean biomass, boolean districtNet, boolean geothermal) {
    OfficialCertificate certificate = new OfficialCertificate();
    certificate.setCertificateType(CertificateType.OFFICIAL);
    certificate.setBiomass(biomass);
    certificate.setDistrictNet(districtNet);
    certificate.setGeothermal(geothermal);

    Address address = new Address();
    address.setCertificates(List.of(certificate));
    return address;
}
}
