package com.EcoMentor_backend.EcoMentor.RecommendationTest.useCases.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.CreateRecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.mapper.RecommendationMapper;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;



public class RecommendationMapperTest {

    @InjectMocks
    private RecommendationMapper recommendationMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toDTOWithValidRecommendation() {
        Recommendation recommendation = Recommendation.builder()
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

        RecommendationDTO dto = recommendationMapper.toDTO(recommendation);

        assertNotNull(dto);
        assertEquals(1L, dto.getRecommendationId());
        assertEquals("Valid Recommendation", dto.getName());
        assertEquals(10.0f, dto.getUpgradedIsolation());
        assertEquals(1000.0f, dto.getTotalPrice());
    }

    @Test
    void toEntityWithValidCreateRecommendationDTO() {
        CreateRecommendationDTO dto = CreateRecommendationDTO.builder()
                .certificates(Collections.singletonList(new Certificate()))
                .name("Test Recommendation")
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

        Recommendation recommendation = recommendationMapper.toEntity(dto);

        assertNotNull(recommendation);
        assertEquals("Test Recommendation", recommendation.getName());
        assertEquals(10.0f, recommendation.getUpgradedIsolation());
        assertEquals(1000.0f, recommendation.getTotalPrice());
    }
}