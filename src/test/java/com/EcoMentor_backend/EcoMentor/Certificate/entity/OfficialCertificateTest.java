package com.EcoMentor_backend.EcoMentor.Certificate.entity;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.Year;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OfficialCertificateTest {

    @Mock
    private Address address;

    @Mock
    private Recommendation recommendation;

    @Mock
    private Recommendation recommendation2;

    private OfficialCertificate officialCertificate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        officialCertificate = OfficialCertificate.builder()
                .documentId("12345")
                .floor("1")
                .door("A")
                .climateZone("Zone1")
                .cadastreMeters(100)
                .buildingYear(Year.of(2020))
                .buildingUse("Residential")
                .nonRenewablePrimaryQualification(Qualification.A)
                .nonRenewablePrimaryEnergy(50.0f)
                .co2Qualification(Qualification.B)
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
                .heatingQualification(Qualification.C)
                .heatingEmissions(30.0f)
                .refrigerationQualification(Qualification.D)
                .refrigerationEmissions(40.0f)
                .acsQualification(Qualification.E)
                .acsEmissions(50.0f)
                .lightingQualification(Qualification.F)
                .lightingEmissions(60.0f)
                .residentialUseVentilation(70.0f)
                .energeticRehabilitation(true)
                .entryDate(new Date(System.currentTimeMillis()))
                .build();
    }

    @Test
    void officialCertificateShouldHandleNullRecommendations() {
        officialCertificate.setRecommendations(null);
        assertNull(officialCertificate.getRecommendations());
    }

    @Test
    void officialCertificateShouldRemoveRecommendation() {
        officialCertificate.getRecommendations().add(recommendation);
        officialCertificate.getRecommendations().remove(recommendation);
        assertFalse(officialCertificate.getRecommendations().contains(recommendation));
    }

    @Test
    void officialCertificateShouldNotAddDuplicateRecommendations() {
        officialCertificate.getRecommendations().add(recommendation);
        officialCertificate.getRecommendations().add(recommendation);
        assertEquals(1, officialCertificate.getRecommendations().size());
    }

    @Test
    void officialCertificateShouldHandleMultipleRecommendations() {
        officialCertificate.getRecommendations().add(recommendation);
        officialCertificate.getRecommendations().add(recommendation2);
        assertEquals(2, officialCertificate.getRecommendations().size());
    }

    @Test
    void officialCertificateShouldHaveValidDocumentId() {
        assertNotNull(officialCertificate.getDocumentId());
    }

    @Test
    void officialCertificateShouldHaveValidFloor() {
        assertNotNull(officialCertificate.getFloor());
    }

    @Test
    void officialCertificateShouldHaveValidDoor() {
        assertNotNull(officialCertificate.getDoor());
    }

    @Test
    void officialCertificateShouldHaveValidClimateZone() {
        assertNotNull(officialCertificate.getClimateZone());
    }

    @Test
    void officialCertificateShouldHaveValidCadastreMeters() {
        assertNotNull(officialCertificate.getCadastreMeters());
    }

    @Test
    void officialCertificateShouldHaveValidBuildingYear() {
        assertNotNull(officialCertificate.getBuildingYear());
    }

    @Test
    void officialCertificateShouldHaveValidBuildingUse() {
        assertNotNull(officialCertificate.getBuildingUse());
    }

    @Test
    void officialCertificateShouldHaveValidNonRenewablePrimaryQualification() {
        assertNotNull(officialCertificate.getNonRenewablePrimaryQualification());
    }

    @Test
    void officialCertificateShouldHaveValidNonRenewablePrimaryEnergy() {
        assertEquals(50.0f, officialCertificate.getNonRenewablePrimaryEnergy());
    }

    @Test
    void officialCertificateShouldHaveValidCo2Qualification() {
        assertNotNull(officialCertificate.getCo2Qualification());
    }

    @Test
    void officialCertificateShouldHaveValidCo2Emissions() {
        assertEquals(20.0f, officialCertificate.getCo2Emissions());
    }

    @Test
    void officialCertificateShouldHaveValidFinalEnergyConsumption() {
        assertEquals(100.0f, officialCertificate.getFinalEnergyConsumption());
    }

    @Test
    void officialCertificateShouldHaveValidAnnualCost() {
        assertEquals(200.0f, officialCertificate.getAnnualCost());
    }

    @Test
    void officialCertificateShouldHaveValidElectricVehicle() {
        assertTrue(officialCertificate.isElectricVehicle());
    }

    @Test
    void officialCertificateShouldHaveValidSolarThermal() {
        assertTrue(officialCertificate.isSolarThermal());
    }

    @Test
    void officialCertificateShouldHaveValidPhotovoltaicSolar() {
        assertTrue(officialCertificate.isPhotovoltaicSolar());
    }

    @Test
    void officialCertificateShouldHaveValidBiomass() {
        assertTrue(officialCertificate.isBiomass());
    }

    @Test
    void officialCertificateShouldHaveValidDistrictNet() {
        assertTrue(officialCertificate.isDistrictNet());
    }

    @Test
    void officialCertificateShouldHaveValidGeothermal() {
        assertTrue(officialCertificate.isGeothermal());
    }

    @Test
    void officialCertificateShouldHaveValidInsulation() {
        assertEquals(10.0f, officialCertificate.getInsulation());
    }

    @Test
    void officialCertificateShouldHaveValidWindowEfficiency() {
        assertEquals(5.0f, officialCertificate.getWindowEfficiency());
    }

    @Test
    void officialCertificateShouldHaveValidHeatingQualification() {
        assertNotNull(officialCertificate.getHeatingQualification());
    }

    @Test
    void officialCertificateShouldHaveValidHeatingEmissions() {
        assertEquals(30.0f, officialCertificate.getHeatingEmissions());
    }

    @Test
    void officialCertificateShouldHaveValidRefrigerationQualification() {
        assertNotNull(officialCertificate.getRefrigerationQualification());
    }

    @Test
    void officialCertificateShouldHaveValidRefrigerationEmissions() {
        assertEquals(40.0f, officialCertificate.getRefrigerationEmissions());
    }

    @Test
    void officialCertificateShouldHaveValidAcsQualification() {
        assertNotNull(officialCertificate.getAcsQualification());
    }

    @Test
    void officialCertificateShouldHaveValidAcsEmissions() {
        assertEquals(50.0f, officialCertificate.getAcsEmissions());
    }

    @Test
    void officialCertificateShouldHaveValidLightingQualification() {
        assertNotNull(officialCertificate.getLightingQualification());
    }

    @Test
    void officialCertificateShouldHaveValidLightingEmissions() {
        assertEquals(60.0f, officialCertificate.getLightingEmissions());
    }

    @Test
    void officialCertificateShouldHaveValidResidentialUseVentilation() {
        assertEquals(70.0f, officialCertificate.getResidentialUseVentilation());
    }

    @Test
    void officialCertificateShouldHaveValidEnergeticRehabilitation() {
        assertTrue(officialCertificate.isEnergeticRehabilitation());
    }

    @Test
    void officialCertificateShouldHaveValidEntryDate() {
        assertNotNull(officialCertificate.getEntryDate());
    }
}