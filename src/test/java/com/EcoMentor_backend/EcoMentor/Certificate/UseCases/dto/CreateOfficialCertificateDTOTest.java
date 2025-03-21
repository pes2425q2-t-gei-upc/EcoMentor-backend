package com.EcoMentor_backend.EcoMentor.Certificate.UseCases.dto;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateOfficialCertificateDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateOfficialCertificateDTOTest {

    @Test
    void testCreateOfficialCertificateDTO() {
        Qualification qualification = Mockito.mock(Qualification.class);

        CreateOfficialCertificateDTO createOfficialCertificateDTO = CreateOfficialCertificateDTO.builder()
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

        assertEquals("DOC123", createOfficialCertificateDTO.getDocumentId());
        assertEquals("1st", createOfficialCertificateDTO.getFloor());
        assertEquals("A", createOfficialCertificateDTO.getDoor());
        assertEquals("Zone1", createOfficialCertificateDTO.getClimateZone());
        assertEquals(100, createOfficialCertificateDTO.getCadastreMeters());
        assertEquals(Year.of(2023), createOfficialCertificateDTO.getBuildingYear());
        assertEquals("Residential", createOfficialCertificateDTO.getBuildingUse());
        assertEquals(qualification, createOfficialCertificateDTO.getNonRenewablePrimaryQualification());
        assertEquals(50.0f, createOfficialCertificateDTO.getNonRenewablePrimaryEnergy());
        assertEquals(qualification, createOfficialCertificateDTO.getCo2Qualification());
        assertEquals(20.0f, createOfficialCertificateDTO.getCo2Emissions());
        assertEquals(100.0f, createOfficialCertificateDTO.getFinalEnergyConsumption());
        assertEquals(200.0f, createOfficialCertificateDTO.getAnnualCost());
        assertTrue(createOfficialCertificateDTO.isElectricVehicle());
        assertTrue(createOfficialCertificateDTO.isSolarThermal());
        assertTrue(createOfficialCertificateDTO.isPhotovoltaicSolar());
        assertTrue(createOfficialCertificateDTO.isBiomass());
        assertTrue(createOfficialCertificateDTO.isDistrictNet());
        assertTrue(createOfficialCertificateDTO.isGeothermal());
        assertEquals(10.0f, createOfficialCertificateDTO.getInsulation());
        assertEquals(5.0f, createOfficialCertificateDTO.getWindowEfficiency());
        assertEquals(qualification, createOfficialCertificateDTO.getHeatingQualification());
        assertEquals(30.0f, createOfficialCertificateDTO.getHeatingEmissions());
        assertEquals(qualification, createOfficialCertificateDTO.getRefrigerationQualification());
        assertEquals(15.0f, createOfficialCertificateDTO.getRefrigerationEmissions());
        assertEquals(qualification, createOfficialCertificateDTO.getAcsQualification());
        assertEquals(10.0f, createOfficialCertificateDTO.getAcsEmissions());
        assertEquals(qualification, createOfficialCertificateDTO.getLightingQualification());
        assertEquals(5.0f, createOfficialCertificateDTO.getLightingEmissions());
        assertEquals(2.0f, createOfficialCertificateDTO.getResidentialUseVentilation());
        assertTrue(createOfficialCertificateDTO.isEnergeticRehabilitation());
        assertEquals(Date.valueOf("2023-01-01"), createOfficialCertificateDTO.getEntryDate());
    }
}