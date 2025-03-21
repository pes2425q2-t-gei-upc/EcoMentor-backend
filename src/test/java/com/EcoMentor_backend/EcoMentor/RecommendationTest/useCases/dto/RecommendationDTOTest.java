package com.EcoMentor_backend.EcoMentor.RecommendationTest.useCases.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import java.util.Collections;
import org.junit.jupiter.api.Test;



public class RecommendationDTOTest {

    @Test
    void recommendationDTOWithValidData() {
        RecommendationDTO dto = RecommendationDTO.builder()
                .recommendationId(1L)
                .certificates(Collections.singletonList(new Certificate()))
                .name("Valid Recommendation")
                .canUpgradeIsolation(true)
                .upgradedIsolation(10.0f)
                .canUpgradeWindows(true)
                .upgradedWindows(20.0f)
                .canUpgradeSolarPanels(true)
                .upgradedSolarPanels(30.0f)
                .canUpgradeBombHeat(true)
                .upgradedBombHeat(40.0f)
                .canUpgradeHeat(true)
                .upgradedHeat(50.0f)
                .io(1.0f)
                .ir(2.0f)
                .iss(3.0f)
                .r1(4.0f)
                .r0(5.0f)
                .upgradedICEE(6.0f)
                .totalPrice(1000.0f)
                .build();

        assertNotNull(dto);
        assertEquals(1L, dto.getRecommendationId());
        assertEquals("Valid Recommendation", dto.getName());
        assertEquals(10.0f, dto.getUpgradedIsolation());
        assertEquals(1000.0f, dto.getTotalPrice());
    }

    @Test
    void recommendationDTOWithEmptyCertificates() {
        RecommendationDTO dto = RecommendationDTO.builder()
                .recommendationId(2L)
                .certificates(Collections.emptyList())
                .name("Empty Certificates")
                .canUpgradeIsolation(false)
                .upgradedIsolation(0.0f)
                .canUpgradeWindows(false)
                .upgradedWindows(0.0f)
                .canUpgradeSolarPanels(false)
                .upgradedSolarPanels(0.0f)
                .canUpgradeBombHeat(false)
                .upgradedBombHeat(0.0f)
                .canUpgradeHeat(false)
                .upgradedHeat(0.0f)
                .io(0.0f)
                .ir(0.0f)
                .iss(0.0f)
                .r1(0.0f)
                .r0(0.0f)
                .upgradedICEE(0.0f)
                .totalPrice(0.0f)
                .build();

        assertNotNull(dto);
        assertEquals(2L, dto.getRecommendationId());
        assertEquals("Empty Certificates", dto.getName());
        assertEquals(0.0f, dto.getUpgradedIsolation());
        assertEquals(0.0f, dto.getTotalPrice());
    }
}