package com.EcoMentor_backend.EcoMentor.CertificateTest.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import java.time.Year;
import org.junit.jupiter.api.Test;

public class OfficialCertificateTest {

    @Test
    public void testOfficialCertificateCreation() {
        OfficialCertificate officialCertificate = OfficialCertificate.builder()
                .documentId("DOC123")
                .climateZone("Zone1")
                .cadastreMeters(100)
                .buildingYear(Year.of(2020))
                .nonRenewablePrimaryQualification(Qualification.A)
                .build();

        assertNotNull(officialCertificate);
        assertEquals("DOC123", officialCertificate.getDocumentId());
        assertEquals("Zone1", officialCertificate.getClimateZone());
        assertEquals(100, officialCertificate.getCadastreMeters());
        assertEquals(Year.of(2020), officialCertificate.getBuildingYear());
        assertEquals(Qualification.A, officialCertificate.getNonRenewablePrimaryQualification());
    }
}