package com.EcoMentor_backend.EcoMentor.RecommendationTest.useCases.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.CreateRecommendationDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateRecommendationDTOTest {

    @DisplayName("should correctly create CreateRecommendationDTO using builder")
    @Test
    void correctlyCreatesCreateRecommendationDTOUsingBuilder() {
        CreateRecommendationDTO dto = CreateRecommendationDTO.builder()
                .name("Test Name")
                .description("Test Description")
                .recommendationType("Insulation")
                .upgradedICEE(10.5f)
                .upgradePercentage(20.0f)
                .upgradedAnualCost(500.0f)
                .totalPrice(1000.0f)
                .build();

        assertEquals("Test Name", dto.getName());
        assertEquals("Test Description", dto.getDescription());
        assertEquals("Insulation", dto.getRecommendationType());
        assertEquals(10.5f, dto.getUpgradedICEE());
        assertEquals(20.0f, dto.getUpgradePercentage());
        assertEquals(500.0f, dto.getUpgradedAnualCost());
        assertEquals(1000.0f, dto.getTotalPrice());
    }

    @DisplayName("should correctly create CreateRecommendationDTO using no-args constructor and setters")
    @Test
    void correctlyCreatesCreateRecommendationDTOUsingNoArgsConstructorAndSetters() {
        CreateRecommendationDTO dto = new CreateRecommendationDTO();
        dto.setName("Test Name");
        dto.setDescription("Test Description");
        dto.setRecommendationType("Windows");
        dto.setUpgradedICEE(15.0f);
        dto.setUpgradePercentage(25.0f);
        dto.setUpgradedAnualCost(600.0f);
        dto.setTotalPrice(1200.0f);

        assertEquals("Test Name", dto.getName());
        assertEquals("Test Description", dto.getDescription());
        assertEquals("Windows", dto.getRecommendationType());
        assertEquals(15.0f, dto.getUpgradedICEE());
        assertEquals(25.0f, dto.getUpgradePercentage());
        assertEquals(600.0f, dto.getUpgradedAnualCost());
        assertEquals(1200.0f, dto.getTotalPrice());
    }
}