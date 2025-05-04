package com.EcoMentor_backend.EcoMentor.Recommendation.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.CreateRecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.TotalValuesRecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.mapper.RecommendationMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CalculateRecommendationValuesUseCase {

    private final RecommendationMapper recommendationMapper;
    private final OfficialCertificateRepository certificateRepository;

    public TotalValuesRecommendationDTO calculateValues(List<CreateRecommendationDTO> recommendationDTOs,
                                                        long certificateId) {
        OfficialCertificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));

        // Paso 1: Convertir los DTOs a entidades
        List<Recommendation> recommendations = recommendationDTOs.stream()
                .map(recommendationMapper::toEntity)
                .collect(Collectors.toList());

        // Paso 2: Calcular los valores necesarios
        float totalCost = (float) recommendations.stream()
                .mapToDouble(Recommendation::getTotalPrice)
                .sum();

        float totalNewAnnualCost = (float) recommendations.stream()
                .mapToDouble(Recommendation::getUpgradedAnualCost)
                .sum();

        float totalOldAnnualCost = certificate.getAnnualCost();
        float totalSavings = totalOldAnnualCost - totalNewAnnualCost;

        String qualificationNew = "A"; //calculo a definir

        // Paso 3: Devolver el DTO con todos los valores
        return TotalValuesRecommendationDTO.builder()
                .totalCost(totalCost)
                .totalSavings(totalSavings)
                .totalNewAnnualCost(totalNewAnnualCost)
                .totalOldAnnualCost(totalOldAnnualCost)
                .qualificationNew(qualificationNew)
                .build();
    }
}
