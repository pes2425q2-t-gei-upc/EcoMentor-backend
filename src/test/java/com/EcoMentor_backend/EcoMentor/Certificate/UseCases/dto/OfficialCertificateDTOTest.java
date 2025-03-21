package com.EcoMentor_backend.EcoMentor.Certificate.UseCases.dto;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.OfficialCertificateDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Date;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OfficialCertificateDTOTest {

    @Mock
    private Qualification qualification;

    private OfficialCertificateDTO officialCertificateDTO;

    @Test
    void testOfficialCertificateDTO() {

        officialCertificateDTO = OfficialCertificateDTO.builder()
                .documentId("DOC123")
                .floor("1st")
                .door("A")
                .climateZone("Zone1")
                .cadastreMeters(100)
                .buildingYear(Year.of(2023))
                .buildingUse("Residential")
                .nonRenewablePrimaryQualification(qualification)
                .nonRenewablePrimaryEnergy(50.0f)
                .co2Qualification(qualification)
                .co2Emissions(20.0f)
                .finalEnergyConsumption(100.0f)
                .annualCost(200.0f)
                .electricVehicle(true)
                .solarThermal(true)
                .photovoltaicSolar(true)
                .biomass(true)
                .districtNet(true)
                .geothermal(true)
                .insulation(10.0f)
                .windowEfficiency(5.0f)
                .heatingQualification(qualification)
                .heatingEmissions(30.0f)
                .refrigerationQualification(qualification)
                .refrigerationEmissions(15.0f)
                .acsQualification(qualification)
                .acsEmissions(10.0f)
                .lightingQualification(qualification)
                .lightingEmissions(5.0f)
                .residentialUseVentilation(2.0f)
                .energeticRehabilitation(true)
                .entryDate(Date.valueOf("2023-01-01"))
                .build();

        assertEquals("DOC123", officialCertificateDTO.getDocumentId());
        assertEquals("1st", officialCertificateDTO.getFloor());
        assertEquals("A", officialCertificateDTO.getDoor());
        assertEquals("Zone1", officialCertificateDTO.getClimateZone());
        assertEquals(100, officialCertificateDTO.getCadastreMeters());
        assertEquals(Year.of(2023), officialCertificateDTO.getBuildingYear());
        assertEquals("Residential", officialCertificateDTO.getBuildingUse());
        assertEquals(qualification, officialCertificateDTO.getNonRenewablePrimaryQualification());
        assertEquals(50.0f, officialCertificateDTO.getNonRenewablePrimaryEnergy());
        assertEquals(qualification, officialCertificateDTO.getCo2Qualification());
        assertEquals(20.0f, officialCertificateDTO.getCo2Emissions());
        assertEquals(100.0f, officialCertificateDTO.getFinalEnergyConsumption());
        assertEquals(200.0f, officialCertificateDTO.getAnnualCost());
        assertTrue(officialCertificateDTO.isElectricVehicle());
        assertTrue(officialCertificateDTO.isSolarThermal());
        assertTrue(officialCertificateDTO.isPhotovoltaicSolar());
        assertTrue(officialCertificateDTO.isBiomass());
        assertTrue(officialCertificateDTO.isDistrictNet());
        assertTrue(officialCertificateDTO.isGeothermal());
        assertEquals(10.0f, officialCertificateDTO.getInsulation());
        assertEquals(5.0f, officialCertificateDTO.getWindowEfficiency());
        assertEquals(qualification, officialCertificateDTO.getHeatingQualification());
        assertEquals(30.0f, officialCertificateDTO.getHeatingEmissions());
        assertEquals(qualification, officialCertificateDTO.getRefrigerationQualification());
        assertEquals(15.0f, officialCertificateDTO.getRefrigerationEmissions());
        assertEquals(qualification, officialCertificateDTO.getAcsQualification());
        assertEquals(10.0f, officialCertificateDTO.getAcsEmissions());
        assertEquals(qualification, officialCertificateDTO.getLightingQualification());
        assertEquals(5.0f, officialCertificateDTO.getLightingEmissions());
        assertEquals(2.0f, officialCertificateDTO.getResidentialUseVentilation());
        assertTrue(officialCertificateDTO.isEnergeticRehabilitation());
        assertEquals(Date.valueOf("2023-01-01"), officialCertificateDTO.getEntryDate());
    }
}