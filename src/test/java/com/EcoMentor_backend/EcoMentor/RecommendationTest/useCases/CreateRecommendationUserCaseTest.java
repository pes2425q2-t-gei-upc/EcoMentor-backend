package com.EcoMentor_backend.EcoMentor.RecommendationTest.useCases;

import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.repositories.RecommendationRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.CreateRecommendationUserCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.CreateRecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.mapper.RecommendationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CreateRecommendationUserCaseTest {

    @Mock
    private RecommendationRepository recommendationRepository;

    @Mock
    private RecommendationMapper recommendationMapper;

    @InjectMocks
    private CreateRecommendationUserCase createRecommendationUserCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRecommendationSuccessfully() {
        CreateRecommendationDTO createRecommendationDTO = CreateRecommendationDTO.builder()
                .name("Test Recommendation")
                .totalPrice(1000.0f)
                .build();
        Recommendation recommendation = Recommendation.builder()
                .recommendationId(1L)
                .name("Test Recommendation")
                .totalPrice(1000.0f)
                .build();

        when(recommendationMapper.toEntity(createRecommendationDTO)).thenReturn(recommendation);
        when(recommendationRepository.save(recommendation)).thenReturn(recommendation);

        long recommendationId = createRecommendationUserCase.execute(createRecommendationDTO);

        assertEquals(1L, recommendationId);
    }

    @Test
    void createRecommendationWithInvalidData() {
        CreateRecommendationDTO createRecommendationDTO = CreateRecommendationDTO.builder()
                .name("")
                .totalPrice(-1000.0f)
                .build();

        when(recommendationMapper.toEntity(createRecommendationDTO)).thenThrow(new RuntimeException("Invalid data"));

        try {
            createRecommendationUserCase.execute(createRecommendationDTO);
        } catch (RuntimeException e) {
            assertEquals("Invalid data", e.getMessage());
        }
    }
}