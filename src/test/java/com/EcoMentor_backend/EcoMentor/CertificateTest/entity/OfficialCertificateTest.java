package com.EcoMentor_backend.EcoMentor.CertificateTest.entity;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import org.junit.jupiter.api.Test;
import java.time.Year;
import static org.junit.jupiter.api.Assertions.*;

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