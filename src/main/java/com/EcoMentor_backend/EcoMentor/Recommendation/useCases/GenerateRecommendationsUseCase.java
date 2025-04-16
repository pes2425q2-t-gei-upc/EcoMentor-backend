package com.EcoMentor_backend.EcoMentor.Recommendation.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.repositories.RecommendationRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class GenerateRecommendationsUseCase {

    private final RecommendationRepository recommendationRepository;
    private final OfficialCertificateRepository certificateRepository;

    public GenerateRecommendationsUseCase(RecommendationRepository recommendationRepository,
                                          OfficialCertificateRepository certificateRepository) {
        this.recommendationRepository = recommendationRepository;
        this.certificateRepository = certificateRepository;
    }

    public List<RecommendationDTO> execute(Long certificateId) {
        OfficialCertificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found"));

        List<Recommendation> recommendations = generate(certificate);

        for (Recommendation recommendation : recommendations) {
            if (recommendation.getCertificates() == null) {
                recommendation.setCertificates(new ArrayList<>());
            }
            recommendation.getCertificates().add(certificate);
            certificate.getRecommendations().add(recommendation);
        }

        recommendationRepository.saveAll(recommendations);
        certificateRepository.save(certificate);
        
        List<RecommendationDTO> recommendationDTOs = new ArrayList<>();
        for (Recommendation recommendation : recommendations) {
            RecommendationDTO recommendationDTO = new RecommendationDTO();
            recommendationDTO.setName(recommendation.getName());
            recommendationDTO.setDescription(recommendation.getDescription());
            recommendationDTO.setRecommendationType(recommendation.getRecommendationType());
            recommendationDTO.setUpgradePercentage(recommendation.getUpgradePercentage());
            recommendationDTO.setUpgradedICEE(recommendation.getUpgradedICEE());
            recommendationDTO.setUpgradedAnualCost(recommendation.getUpgradedAnualCost());
            recommendationDTO.setTotalPrice(recommendation.getTotalPrice());
            recommendationDTOs.add(recommendationDTO);
        }
        return recommendationDTOs;
    }

    public List<Recommendation> generate(final OfficialCertificate certificate) {
        List<Recommendation> recommendations = new ArrayList<>();

        // --- Recomendación para mejorar el Aislamiento (INSULATION) ---
        // Umbral medio: 0.5
        if (certificate.getInsulation() < 0.5) {
            recommendations.add(
                    Recommendation.builder()
                            .name("Improve insulation")
                            .description("Install better insulation materials in walls and"
                                    + " ceilings to improve thermal efficiency.")
                            .recommendationType("INSULATION")
                            .upgradePercentage(10.0f) // Estimación de mejora (10%)
                            .upgradedICEE("A") // Aquí se puede calcular la nueva calificación,
                            // este valor es un placeholder
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.9f)
                            .totalPrice(4000) // Costo aproximado
                            .build()
            );
        }

        // --- Recomendación para instalar paneles solares (SOLAR) ---
        if (!certificate.isPhotovoltaicSolar()) {
            recommendations.add(
                    Recommendation.builder()
                            .name("Install solar panels")
                            .description("Install photovoltaic solar panels to reduce dependence on "
                                    + "the electrical grid and reduce emissions.")
                            .recommendationType("SOLAR")
                            .upgradePercentage(20.0f)
                            .upgradedICEE("A") // Cálculo a definir
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.8f)
                            .totalPrice(6000)
                            .build()
            );
        }

        // --- Recomendación para mejorar la eficiencia de las ventanas (WINDOWS) ---
        // Umbral medio: 0.6
        if (certificate.getWindowEfficiency() < 0.6) {
            recommendations.add(
                    Recommendation.builder()
                            .name("Improve window efficiency")
                            .description("Replace windows with more insulated "
                                    + "and energy-efficient models.")
                            .recommendationType("WINDOWS")
                            .upgradePercentage(15.0f)
                            .upgradedICEE("A") // Cálculo a definir
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.85f)
                            .totalPrice(3000)
                            .build()
            );
        }

        // --- Recomendación para instalar bomba de calor (HEAT_PUMP) ---
        // Umbral de construcción: antes del año 2000 y emisiones de calefacción > 40
        if (certificate.getHeatingEmissions() > 40) {
            recommendations.add(
                    Recommendation.builder()
                            .name("Install heat pump")
                            .description("Install a heat pump to upgrade the air conditioning system,"
                                    + " especially in older buildings.")
                            .recommendationType("HEAT_PUMP")
                            .upgradePercentage(15.0f)
                            .upgradedICEE("A") // Cálculo a definir
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.85f)
                            .totalPrice(7000)
                            .build()
            );
        }

        // --- Recomendación para implementar sistema de biomasa (BIOMASS) ---
        // Umbral: si no se tiene biomasa y las emisiones de calefacción > 35
        if (!certificate.isBiomass() && certificate.getHeatingEmissions() > 35) {
            recommendations.add(
                    Recommendation.builder()
                            .name("Implement biomass system")
                            .description("Install a biomass system to harness "
                                    + "renewable energy for heating.")
                            .recommendationType("BIOMASS")
                            .upgradePercentage(12.0f)
                            .upgradedICEE("A") // Cálculo a definir
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.87f)
                            .totalPrice(5000)
                            .build()
            );
        }

        // --- Recomendación para optimizar el sistema HVAC (HVAC) ---
        // Umbral: consumo final de energía > 180 kWh/m²·año
        if (certificate.getFinalEnergyConsumption() > 180) {
            recommendations.add(
                    Recommendation.builder()
                            .name("Optimize HVAC system")
                            .description("Upgrade or improve your HVAC system to "
                                    + "reduce energy consumption and emissions.")
                            .recommendationType("HVAC")
                            .upgradePercentage(8.0f)
                            .upgradedICEE("A") // Cálculo a definir
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.92f)
                            .totalPrice(4000)
                            .build()
            );
        }

        // --- Recomendación para mejorar la iluminación (LIGHTING) ---
        // Umbral: emisiones de iluminación > 5 (valor de ejemplo)
        if (certificate.getLightingEmissions() > 5) {
            recommendations.add(
                    Recommendation.builder()
                            .name("Improve lighting")
                            .description("Upgrade to high-performance LED lighting systems"
                                    + " to reduce consumption and associated emissions.")
                            .recommendationType("LIGHTING")
                            .upgradePercentage(5.0f)
                            .upgradedICEE("A") // Cálculo a definir
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.95f)
                            .totalPrice(2000)
                            .build()
            );
        }

        return recommendations;
    }
}
