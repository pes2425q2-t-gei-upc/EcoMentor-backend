package com.EcoMentor_backend.EcoMentor.RecommendationTest.infrastructure.controllers;

        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.mockito.Mockito.when;

        import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.controllers.RecommendationPostController;
        import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GenerateRecommendationsUseCase;
        import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
        import org.junit.jupiter.api.Test;
        import org.junit.jupiter.api.extension.ExtendWith;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.junit.jupiter.MockitoExtension;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;

        import java.util.Collections;
        import java.util.List;

        @ExtendWith(MockitoExtension.class)
        public class RecommendationPostControllerTest {

            @Mock
            private GenerateRecommendationsUseCase generateRecommendationsUseCase;

            @InjectMocks
            private RecommendationPostController recommendationPostController;

            @Test
            void generateRecommendationsSuccessfully() {
                Long certificateId = 1L;
                List<RecommendationDTO> recommendations = List.of(
                        RecommendationDTO.builder().recommendationId(1L).name("Recommendation 1").build(),
                        RecommendationDTO.builder().recommendationId(2L).name("Recommendation 2").build()
                );
                when(generateRecommendationsUseCase.execute(certificateId)).thenReturn(recommendations);

                ResponseEntity<List<RecommendationDTO>> response = recommendationPostController.generateRecommendations(certificateId);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(recommendations, response.getBody());
            }

            @Test
            void generateRecommendationsReturnsEmptyListWhenNoRecommendations() {
                Long certificateId = 1L;
                when(generateRecommendationsUseCase.execute(certificateId)).thenReturn(Collections.emptyList());

                ResponseEntity<List<RecommendationDTO>> response = recommendationPostController.generateRecommendations(certificateId);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(0, response.getBody().size());
            }

            @Test
            void generateRecommendationsThrowsExceptionForInvalidCertificateId() {
                Long certificateId = 999L;
                when(generateRecommendationsUseCase.execute(certificateId))
                        .thenThrow(new RuntimeException("Certificate not found"));

                try {
                    recommendationPostController.generateRecommendations(certificateId);
                } catch (RuntimeException e) {
                    assertEquals("Certificate not found", e.getMessage());
                }
            }
        }