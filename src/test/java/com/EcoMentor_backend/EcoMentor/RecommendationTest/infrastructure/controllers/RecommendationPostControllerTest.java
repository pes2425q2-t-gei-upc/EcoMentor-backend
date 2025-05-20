package com.EcoMentor_backend.EcoMentor.RecommendationTest.infrastructure.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.controllers.RecommendationPostController;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.CalculateRecommendationValuesUseCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.CreateRecommendationUserCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GenerateRecommendationsUseCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.GenerateZoneRecommendationsUseCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.CreateRecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.TotalValuesRecommendationDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetUserIdByToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class RecommendationPostControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private CreateRecommendationUserCase createRecommendationUserCase;

    @Mock
    private GenerateRecommendationsUseCase generateRecommendationsUseCase;

    @Mock
    private GenerateZoneRecommendationsUseCase generateZoneRecommendationsUseCase;

    @Mock
    private CalculateRecommendationValuesUseCase calculateRecommendationValuesUseCase;

    @Mock
    private GetUserIdByToken getUserIdByToken;

    @InjectMocks
    private RecommendationPostController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateRecommendation() throws Exception {
        CreateRecommendationDTO dto = new CreateRecommendationDTO();
        dto.setName("Test");
        // set other fields as needed
        Long generatedId = 42L;
        when(createRecommendationUserCase.execute(dto)).thenReturn(generatedId);

        mockMvc.perform(post("/api/recommendation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("42"));

        verify(createRecommendationUserCase).execute(dto);
    }


    @Test
    void testCalculateValues() throws Exception {
        long certId = 7L;
        long userId = 99L;
        String token = "token123";
        CreateRecommendationDTO dto = new CreateRecommendationDTO();
        dto.setName("Calc");
        List<CreateRecommendationDTO> dtos = List.of(dto);
        TotalValuesRecommendationDTO responseDto = TotalValuesRecommendationDTO.builder()
                .totalCost(100.0f)
                .totalNewAnnualCost(50.0f)
                .totalOldAnnualCost(80.0f)
                .totalSavings(30.0f)
                .qualificationNew("A")
                .build();

        when(getUserIdByToken.execute(token)).thenReturn(userId);
        when(calculateRecommendationValuesUseCase.calculateValues(dtos, certId, userId))
                .thenReturn(responseDto);

        mockMvc.perform(post("/api/recommendation/finalValues/{certificateId}", certId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtos)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));

        verify(getUserIdByToken).execute(token);
        verify(calculateRecommendationValuesUseCase).calculateValues(dtos, certId, userId);
    }
}