package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculatorResultsDTO;
import org.junit.jupiter.api.Test;

public class CalculatorResultsDTOTest {

    @Test
    public void testCalculatorResultsDTOCreation() {
        CalculatorResultsDTO dto = new CalculatorResultsDTO(
                1L,
                50.0f,
                20.0f,
                15.0f,
                10.0f,
                5.0f,
                2.0f,
                Qualification.A,
                Qualification.B,
                Qualification.C,
                Qualification.D,
                Qualification.E,
                Qualification.F
        );

        assertNotNull(dto);
        assertEquals(1L, dto.getCertificateId());
        assertEquals(50.0f, dto.getIoNonRenewablePrimaryEnergy());
        assertEquals(20.0f, dto.getIoCO2E());
        assertEquals(15.0f, dto.getIoHeating());
        assertEquals(10.0f, dto.getIoRefrigeration());
        assertEquals(5.0f, dto.getIoACS());
        assertEquals(2.0f, dto.getIoLighting());
        assertEquals(Qualification.A, dto.getNonRenewablePrimaryQualification());
        assertEquals(Qualification.B, dto.getCo2Qualification());
        assertEquals(Qualification.C, dto.getHeatingQualification());
        assertEquals(Qualification.D, dto.getRefrigerationQualification());
        assertEquals(Qualification.E, dto.getAcsQualification());
        assertEquals(Qualification.F, dto.getLightingQualification());
    }
}