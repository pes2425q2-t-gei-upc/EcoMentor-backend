package com.EcoMentor_backend.EcoMentor.CertificateTest.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.UnofficialCertificate;
import java.sql.Date;
import java.time.Year;
import org.junit.jupiter.api.Test;


public class UnofficialCertificateTest {

    @Test
    public void testUnofficialCertificateCreation() {
        UnofficialCertificate certificate = UnofficialCertificate.builder()
                .floor("3")
                .door("B")
                .climateZone("Zone1")
                .buildingYear(Year.of(2020))
                .buildingUse("Residential")
                .nonRenewablePrimaryQualification(Qualification.A)
                .nonRenewablePrimaryEnergy(50.0f)
                .co2Qualification(Qualification.B)
                .co2Emissions(20.0f)
                .finalEnergyConsumption(70.0f)
                .annualCost(1000.0f)
                .electricVehicle(true)
                .solarThermal(false)
                .photovoltaicSolar(true)
                .biomass(false)
                .districtNet(true)
                .geothermal(false)
                .insulation(10.0f)
                .windowEfficiency(5.0f)
                .heatingQualification(Qualification.C)
                .heatingEmissions(15.0f)
                .refrigerationQualification(Qualification.D)
                .refrigerationEmissions(10.0f)
                .acsQualification(Qualification.E)
                .acsEmissions(5.0f)
                .lightingQualification(Qualification.F)
                .lightingEmissions(2.0f)
                .residentialUseVentilation(1.0f)
                .energeticRehabilitation(true)
                .creationDate(Date.valueOf("2023-01-01"))
                .build();

        assertNotNull(certificate);
        assertEquals("3", certificate.getFloor());
        assertEquals("B", certificate.getDoor());
        assertEquals("Zone1", certificate.getClimateZone());
        assertEquals(Year.of(2020), certificate.getBuildingYear());
        assertEquals("Residential", certificate.getBuildingUse());
        assertEquals(Qualification.A, certificate.getNonRenewablePrimaryQualification());
        assertEquals(50.0f, certificate.getNonRenewablePrimaryEnergy());
        assertEquals(Qualification.B, certificate.getCo2Qualification());
        assertEquals(20.0f, certificate.getCo2Emissions());
        assertEquals(70.0f, certificate.getFinalEnergyConsumption());
        assertEquals(1000.0f, certificate.getAnnualCost());
        assertTrue(certificate.isElectricVehicle());
        assertFalse(certificate.isSolarThermal());
        assertTrue(certificate.isPhotovoltaicSolar());
        assertFalse(certificate.isBiomass());
        assertTrue(certificate.isDistrictNet());
        assertFalse(certificate.isGeothermal());
        assertEquals(10.0f, certificate.getInsulation());
        assertEquals(5.0f, certificate.getWindowEfficiency());
        assertEquals(Qualification.C, certificate.getHeatingQualification());
        assertEquals(15.0f, certificate.getHeatingEmissions());
        assertEquals(Qualification.D, certificate.getRefrigerationQualification());
        assertEquals(10.0f, certificate.getRefrigerationEmissions());
        assertEquals(Qualification.E, certificate.getAcsQualification());
        assertEquals(5.0f, certificate.getAcsEmissions());
        assertEquals(Qualification.F, certificate.getLightingQualification());
        assertEquals(2.0f, certificate.getLightingEmissions());
        assertEquals(1.0f, certificate.getResidentialUseVentilation());
        assertTrue(certificate.isEnergeticRehabilitation());
        assertEquals(Date.valueOf("2023-01-01"), certificate.getCreationDate());
    }

}
