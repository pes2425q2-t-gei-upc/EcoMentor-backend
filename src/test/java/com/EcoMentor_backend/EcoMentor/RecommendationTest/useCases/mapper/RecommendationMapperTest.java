package com.EcoMentor_backend.EcoMentor.RecommendationTest.useCases.mapper;

         import static org.junit.jupiter.api.Assertions.assertEquals;
         import static org.mockito.Mockito.mock;

         import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
         import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.CreateRecommendationDTO;
         import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
         import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.mapper.RecommendationMapper;
         import org.junit.jupiter.api.BeforeEach;
         import org.junit.jupiter.api.DisplayName;
         import org.junit.jupiter.api.Test;

         class RecommendationMapperTest {

             private RecommendationMapper recommendationMapper;

             @BeforeEach
             void setUp() {
                 recommendationMapper = new RecommendationMapper();
             }

             @DisplayName("toDTO should map Recommendation to RecommendationDTO correctly")
             @Test
             void toDTOMapsRecommendationToDTOCorrectly() {
                 Recommendation recommendation = Recommendation.builder()
                         .recommendationId(1L)
                         .name("Test Name")
                         .description("Test Description")
                         .recommendationType("Insulation")
                         .upgradedICEE("A")
                         .upgradePercentage(20.0f)
                         .upgradedAnualCost(500.0f)
                         .totalPrice(1000.0f)
                         .build();

                 RecommendationDTO result = recommendationMapper.toDTO(recommendation);

                 assertEquals(recommendation.getRecommendationId(), result.getRecommendationId());
                 assertEquals(recommendation.getName(), result.getName());
                 assertEquals(recommendation.getDescription(), result.getDescription());
                 assertEquals(recommendation.getRecommendationType(), result.getRecommendationType());
                 assertEquals(recommendation.getUpgradedICEE(), result.getUpgradedICEE());
                 assertEquals(recommendation.getUpgradePercentage(), result.getUpgradePercentage());
                 assertEquals(recommendation.getUpgradedAnualCost(), result.getUpgradedAnualCost());
                 assertEquals(recommendation.getTotalPrice(), result.getTotalPrice());
             }

             @DisplayName("toEntity should map CreateRecommendationDTO to Recommendation correctly")
             @Test
             void toEntityMapsCreateRecommendationDTOToRecommendationCorrectly() {
                 CreateRecommendationDTO createRecommendationDTO = CreateRecommendationDTO.builder()
                         .name("Test Name")
                         .description("Test Description")
                         .recommendationType("Windows")
                         .upgradedICEE("A")
                         .upgradePercentage(25.0f)
                         .upgradedAnualCost(600.0f)
                         .totalPrice(1200.0f)
                         .build();

                 Recommendation result = recommendationMapper.toEntity(createRecommendationDTO);

                 assertEquals(createRecommendationDTO.getName(), result.getName());
                 assertEquals(createRecommendationDTO.getDescription(), result.getDescription());
                 assertEquals(createRecommendationDTO.getRecommendationType(), result.getRecommendationType());
                 assertEquals(createRecommendationDTO.getUpgradedICEE(), result.getUpgradedICEE());
                 assertEquals(createRecommendationDTO.getUpgradePercentage(), result.getUpgradePercentage());
                 assertEquals(createRecommendationDTO.getUpgradedAnualCost(), result.getUpgradedAnualCost());
                 assertEquals(createRecommendationDTO.getTotalPrice(), result.getTotalPrice());
             }
         }