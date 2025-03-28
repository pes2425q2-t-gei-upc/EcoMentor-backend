package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.OfficialCertificateDTO;
import java.sql.Date;
import java.time.Year;
import org.junit.jupiter.api.Test;





public class OfficialCertificateDTOTest {

    @Test
    public void testOfficialCertificateDTOCreation() {
        OfficialCertificateDTO dto = OfficialCertificateDTO.builder()
                .certificateId(1L)
                .certificateType(CertificateType.OFFICIAL)
                .documentId("DOC123")
                .climateZone("Zone1")
                .cadastreMeters(100)
                .buildingYear(Year.of(2020))
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
                .entryDate(Date.valueOf("2023-01-01"))
                .build();

        assertNotNull(dto);
        assertEquals(1L, dto.getCertificateId());
        assertEquals(CertificateType.OFFICIAL, dto.getCertificateType());
        assertEquals("DOC123", dto.getDocumentId());
        assertEquals("Zone1", dto.getClimateZone());
        assertEquals(100, dto.getCadastreMeters());
        assertEquals(Year.of(2020), dto.getBuildingYear());
        assertEquals(Qualification.A, dto.getNonRenewablePrimaryQualification());
        assertEquals(50.0f, dto.getNonRenewablePrimaryEnergy());
        assertEquals(Qualification.B, dto.getCo2Qualification());
        assertEquals(20.0f, dto.getCo2Emissions());
        assertEquals(70.0f, dto.getFinalEnergyConsumption());
        assertEquals(1000.0f, dto.getAnnualCost());
        assertTrue(dto.isElectricVehicle());
        assertFalse(dto.isSolarThermal());
        assertTrue(dto.isPhotovoltaicSolar());
        assertFalse(dto.isBiomass());
        assertTrue(dto.isDistrictNet());
        assertFalse(dto.isGeothermal());
        assertEquals(10.0f, dto.getInsulation());
        assertEquals(5.0f, dto.getWindowEfficiency());
        assertEquals(Qualification.C, dto.getHeatingQualification());
        assertEquals(15.0f, dto.getHeatingEmissions());
        assertEquals(Qualification.D, dto.getRefrigerationQualification());
        assertEquals(10.0f, dto.getRefrigerationEmissions());
        assertEquals(Qualification.E, dto.getAcsQualification());
        assertEquals(5.0f, dto.getAcsEmissions());
        assertEquals(Qualification.F, dto.getLightingQualification());
        assertEquals(2.0f, dto.getLightingEmissions());
        assertEquals(1.0f, dto.getResidentialUseVentilation());
        assertTrue(dto.isEnergeticRehabilitation());
        assertEquals(Date.valueOf("2023-01-01"), dto.getEntryDate());
    }
}