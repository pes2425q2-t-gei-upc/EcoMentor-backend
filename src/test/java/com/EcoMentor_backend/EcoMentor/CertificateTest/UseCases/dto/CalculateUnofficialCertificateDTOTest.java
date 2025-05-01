package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculateUnofficialCertificateDTO;
import java.time.Year;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;




public class CalculateUnofficialCertificateDTOTest {

    @Mock
    private CreateAddressDTO createAddressDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateUnofficialCertificateDTOCreation() {
        CalculateUnofficialCertificateDTO dto = new CalculateUnofficialCertificateDTO(
                createAddressDTO,
                "3",
                "B",
                "Zone1",
                Year.of(2020),
                "Residential",
                100.0f,
                50,
                70.0f,
                1000.0f,
                true,
                false,
                true,
                false,
                true,
                false,
                10.0f,
                5.0f,
                15,
                10,
                5,
                2,
                1.0f,
                true
        );

        assertNotNull(dto);
        assertEquals(createAddressDTO, dto.getCreateAddressDTO());
        assertEquals("3", dto.getFloor());
        assertEquals("B", dto.getDoor());
        assertEquals("Zone1", dto.getClimateZone());
        assertEquals(Year.of(2020), dto.getBuildingYear());
        assertEquals("Residential", dto.getBuildingUse());
        assertEquals(100.0f, dto.getCadastreMeters());
        assertEquals(50, dto.getNonRenewablePrimaryEnergyAprox());
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
        assertEquals(15, dto.getHeatingEmissionsAprox());
        assertEquals(10, dto.getRefrigerationEmissionsAprox());
        assertEquals(5, dto.getAcsEmissionsAprox());
        assertEquals(2, dto.getLightingEmissionsAprox());
        assertEquals(1.0f, dto.getResidentialUseVentilation());
        assertTrue(dto.isEnergeticRehabilitation());
    }
}