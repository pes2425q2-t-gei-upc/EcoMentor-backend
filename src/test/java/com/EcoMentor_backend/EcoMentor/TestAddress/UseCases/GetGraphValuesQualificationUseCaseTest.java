package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetGraphValuesQualificationUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
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

class GetGraphValuesQualificationUseCaseTest {

@Mock
private AddressRepository addressRepository;

@InjectMocks
private GetGraphValuesQualificationUseCase useCase;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
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



// Helper method para crear Address con una OfficialCertificate configurada
private Address createAddressWithQualification(Integer nonRenewable, Integer co2, Integer heating, Integer refrigeration, Integer acs, Integer lighting) {
    OfficialCertificate certificate = new OfficialCertificate();
    certificate.setCertificateType(CertificateType.OFFICIAL);

    certificate.setNonRenewablePrimaryQualification(Qualification.fromValue(nonRenewable));
    certificate.setCo2Qualification(Qualification.fromValue(co2));
    certificate.setHeatingQualification(Qualification.fromValue(heating));
    certificate.setRefrigerationQualification(Qualification.fromValue(refrigeration));
    certificate.setAcsQualification(Qualification.fromValue(acs));
    certificate.setLightingQualification(Qualification.fromValue(lighting));

    Address address = new Address();
    address.setCertificates(List.of(certificate));

    return address;
}
}
