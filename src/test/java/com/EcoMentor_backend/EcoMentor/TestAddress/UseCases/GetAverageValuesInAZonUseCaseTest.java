package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAverageValuesInAZonUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AverageValuesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAverageValuesInAZonUseCaseTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private GetAverageValuesInAZonUseCase getAverageValuesInAZonUseCase;


    @Test
    void testExecute_withAddressesWithinRadius() {
        // Arrange: create mock data
        Double latitude = 41.3874;
        Double longitude = 2.1686;
        Integer radius = 10;

        Address mockAddress = new Address();
        Certificate certificate = new OfficialCertificate();
        certificate.setCertificateType(CertificateType.OFFICIAL);

        // Simulating a certificate with a qualification
        Qualification qualification = Qualification.A;
        ((OfficialCertificate) certificate).setNonRenewablePrimaryQualification(qualification);
        ((OfficialCertificate) certificate).setCo2Qualification(qualification);
        ((OfficialCertificate) certificate).setHeatingQualification(qualification);
        ((OfficialCertificate) certificate).setRefrigerationQualification(qualification);
        ((OfficialCertificate) certificate).setAcsQualification(qualification);
        ((OfficialCertificate) certificate).setLightingQualification(qualification);

        mockAddress.setCertificates(Arrays.asList(certificate));

        // Simulate the repository response
        when(addressRepository.findAddressesWithinDistance(
                new org.locationtech.jts.geom.GeometryFactory().createPoint(new org.locationtech.jts.geom.Coordinate(longitude, latitude)),
                radius * 1000
        )).thenReturn(Collections.singletonList(mockAddress));

        // Act: call the method under test
        AverageValuesDTO result = getAverageValuesInAZonUseCase.execute(latitude, longitude, radius);

        // Assert: check the results
        assertNotNull(result);
        assertEquals(Qualification.A, result.getNonRenewablePrimaryQualification());
        assertEquals(Qualification.A, result.getCo2Qualification());
        assertEquals(Qualification.A, result.getHeatingQualification());
        assertEquals(Qualification.A, result.getRefrigerationQualification());
        assertEquals(Qualification.A, result.getAcsQualification());
        assertEquals(Qualification.A, result.getLightingQualification());
    }

    @Test
    void testExecute_noAddressesFound() {
        // Arrange: no addresses found for the given coordinates and radius
        Double latitude = 41.3874;
        Double longitude = 2.1686;
        Integer radius = 10;

        when(addressRepository.findAddressesWithinDistance(
                new org.locationtech.jts.geom.GeometryFactory().createPoint(new org.locationtech.jts.geom.Coordinate(longitude, latitude)),
                radius * 1000
        )).thenReturn(Collections.emptyList());

        // Act & Assert: expect a ResponseStatusException to be thrown
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            getAverageValuesInAZonUseCase.execute(latitude, longitude, radius);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("No addresses found within the specified radius", exception.getReason());
    }
}
