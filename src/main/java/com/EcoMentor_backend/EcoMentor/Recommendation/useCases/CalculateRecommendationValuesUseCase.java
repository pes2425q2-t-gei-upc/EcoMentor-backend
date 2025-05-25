package com.EcoMentor_backend.EcoMentor.Recommendation.useCases;

import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.AchivementProgressUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
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
    private final AchivementProgressUseCase achievementProgressUseCase;
    private final OfficialCertificateRepository certificateRepository;
    private final AchivementProgressUseCase achivementProgressUseCase;


    public TotalValuesRecommendationDTO calculateValues(List<CreateRecommendationDTO> recommendationDTOs,
                                                        long certificateId, long userId) {
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

        double minCost = recommendations.stream()
                .mapToDouble(Recommendation::getUpgradedAnualCost)
                .min()
                .orElse(0.0);

        float totalNewAnnualCost = (float) (recommendations.size() > 1 ? minCost * 0.93 : minCost);


        float totalOldAnnualCost = certificate.getAnnualCost();
        float totalSavings = totalOldAnnualCost - totalNewAnnualCost;

        // Calcular la mejor calificación (A es mejor que G)
        String qualificationNew = recommendations.stream()
                .map(Recommendation::getUpgradedICEE)
                .min(String::compareTo)
                .orElse("G"); // Por defecto, la peor si no hay recomendaciones

        if (qualificationNew.equals("A")) {
            achievementProgressUseCase.execute(userId, 2L);
            achievementProgressUseCase.execute(userId, 3L);
            achievementProgressUseCase.execute(userId, 4L);
        } else if (qualificationNew.equals("B")) {
            achievementProgressUseCase.execute(userId, 3L);
            achievementProgressUseCase.execute(userId, 2L);
        } else if (qualificationNew.equals("C")) {
            achivementProgressUseCase.execute(userId, 2L);
        }

        return TotalValuesRecommendationDTO.builder()
                .totalCost(totalCost)
                .totalSavings(totalSavings)
                .totalNewAnnualCost(totalNewAnnualCost)
                .totalOldAnnualCost(totalOldAnnualCost)
                .qualificationNew(qualificationNew)
                .build();
    }
}
