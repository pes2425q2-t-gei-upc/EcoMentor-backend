package com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TotalValuesRecommendationDTO {
    private float totalCost;
    private float totalSavings;
    private float totalNewAnnualCost;
    private float totalOldAnnualCost;
    private String qualificationNew;
}