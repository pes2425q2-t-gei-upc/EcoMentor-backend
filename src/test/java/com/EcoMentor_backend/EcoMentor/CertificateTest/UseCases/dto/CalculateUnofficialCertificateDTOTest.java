package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.KindOfHeating;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.KindOfRefrigeration;
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
                1000.0f,
                true,
                false,
                true,
                KindOfHeating.GAS,
                KindOfRefrigeration.DISTRICTE,
                KindOfHeating.ELECTRICA,
                10,
                5,
                1,
                true,
                50.0f,
                20.0f,
                10.0f,
                5.0f
        );

        assertNotNull(dto);
        assertEquals(createAddressDTO, dto.getCreateAddressDTO());
        assertEquals("3", dto.getFloor());
        assertEquals("B", dto.getDoor());
        assertEquals("Zone1", dto.getClimateZone());
        assertEquals(Year.of(2020), dto.getBuildingYear());
        assertEquals("Residential", dto.getBuildingUse());
        assertEquals(100.0f, dto.getCadastreMeters());
        assertEquals(1000.0f, dto.getAnnualCost());
        assertTrue(dto.isElectricVehicle());
        assertFalse(dto.isSolarThermal());
        assertTrue(dto.isPhotovoltaicSolar());
        assertEquals(KindOfHeating.GAS, dto.getKindOfHeating());
        assertEquals(KindOfRefrigeration.DISTRICTE, dto.getKindOfRefrigeration());
        assertEquals(KindOfHeating.ELECTRICA, dto.getKindOfAcs());
        assertEquals(10, dto.getInsulation());
        assertEquals(5, dto.getWindowEfficiency());
        assertEquals(1, dto.getResidentialUseVentilation());
        assertTrue(dto.isEnergeticRehabilitation());
        assertEquals(50.0f, dto.getHeatingConsumption());
        assertEquals(20.0f, dto.getRefrigerationConsumption());
        assertEquals(10.0f, dto.getAcsConsumption());
        assertEquals(5.0f, dto.getLightingConsumption());
    }
}