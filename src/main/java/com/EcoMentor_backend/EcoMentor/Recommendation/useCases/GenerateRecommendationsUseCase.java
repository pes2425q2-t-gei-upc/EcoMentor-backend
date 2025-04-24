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

        // Limpiar las recomendaciones existentes del certificado
        certificate.getRecommendations().clear();

        List<Recommendation> recommendations = generate(certificate);

        for (Recommendation recommendation : recommendations) {
            if (recommendation.getCertificates() == null) {
                recommendation.setCertificates(new ArrayList<>());
            }
            if (certificate.getRecommendations().contains(recommendation)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recommendation already exists");
            } else {
                recommendation.getCertificates().add(certificate);
                certificate.getRecommendations().add(recommendation);
            }
        }

        recommendationRepository.saveAll(recommendations);
        
        List<RecommendationDTO> recommendationDTOs = new ArrayList<>();
        for (Recommendation recommendation : recommendations) {
            RecommendationDTO recommendationDTO = new RecommendationDTO();
            recommendationDTO.setRecommendationId(recommendation.getRecommendationId());
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
                            .name("1")
                            .description("1")
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
                            .name("2")
                            .description("2")
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
                            .name("3")
                            .description("3")
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
                            .name("4")
                            .description("4")
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
                            .name("5")
                            .description("5")
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
                            .name("6")
                            .description("6")
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
                            .name("7")
                            .description("7")
                            .recommendationType("LIGHTING")
                            .upgradePercentage(5.0f)
                            .upgradedICEE("A") // Cálculo a definir
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.95f)
                            .totalPrice(2000)
                            .build()
            );
        }

        // Solar térmico
        if (!certificate.isSolarThermal()) {
            recommendations.add(
                    Recommendation.builder()
                            .name("8")
                            .description("8")
                            .recommendationType("SOLAR_THERMAL")
                            .upgradePercentage(25.0f)
                            .upgradedICEE("B")
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.75f)
                            .totalPrice(3500)
                            .build()
            );
        }

        // Red de calor urbano
        if (!certificate.isDistrictNet()) {
            recommendations.add(
                    Recommendation.builder()
                            .name("9")
                            .description("9")
                            .recommendationType("DISTRICT_HEATING")
                            .upgradePercentage(18.0f)
                            .upgradedICEE("B")
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.82f)
                            .totalPrice(2000)
                            .build()
            );
        }

        // Geotermia
        if (!certificate.isGeothermal()) {
            recommendations.add(
                    Recommendation.builder()
                            .name("10")
                            .description("10")
                            .recommendationType("GEOTHERMAL")
                            .upgradePercentage(20.0f)
                            .upgradedICEE("A")
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.8f)
                            .totalPrice(12000)
                            .build()
            );
        }

        // Carga vehículo eléctrico
        if (!certificate.isElectricVehicle()) {
            recommendations.add(
                    Recommendation.builder()
                            .name("11")
                            .description("11")
                            .recommendationType("EV_CHARGING")
                            .upgradePercentage(0f)
                            .upgradedICEE(certificate.getAnnualCost() > 0 ? "A" : "B")
                            .upgradedAnualCost(certificate.getAnnualCost() + 100)
                            .totalPrice(800)
                            .build()
            );
        }

        // Recuperación de calor en ventilación
        if (certificate.getResidentialUseVentilation() > 0) {
            recommendations.add(
                    Recommendation.builder()
                            .name("12")
                            .description("12")
                            .recommendationType("VENTILATION")
                            .upgradePercentage(10.0f)
                            .upgradedICEE("B")
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.88f)
                            .totalPrice(2500)
                            .build()
            );
        }

        // Optimización de refrigeración
        if (certificate.getRefrigerationEmissions() > 10) {
            recommendations.add(
                    Recommendation.builder()
                            .name("13")
                            .description("13")
                            .recommendationType("REFRIGERATION")
                            .upgradePercentage(15.0f)
                            .upgradedICEE("B")
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.9f)
                            .totalPrice(6000)
                            .build()
            );
        }

        // Agua caliente sanitaria
        if (certificate.getAcsEmissions() > 10) {
            recommendations.add(
                    Recommendation.builder()
                            .name("14")
                            .description("14")
                            .recommendationType("WATER_HEATING")
                            .upgradePercentage(15.0f)
                            .upgradedICEE("A")
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.85f)
                            .totalPrice(4500)
                            .build()
            );
        }

        // Rehabilitación energética profunda
        if (!certificate.isEnergeticRehabilitation()) {
            recommendations.add(
                    Recommendation.builder()
                            .name("15")
                            .description("15")
                            .recommendationType("REHABILITATION")
                            .upgradePercentage(35.0f)
                            .upgradedICEE("A")
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.65f)
                            .totalPrice(15000)
                            .build()
            );
        }

        // Retro‑comisionado
        recommendations.add(
                Recommendation.builder()
                        .name("16")
                        .description("16")
                        .recommendationType("COMMISSIONING")
                        .upgradePercentage(15.0f)
                        .upgradedICEE("B")
                        .upgradedAnualCost(certificate.getAnnualCost() * 0.85f)
                        .totalPrice(2000)
                        .build()
        );

        // Control inteligente
        recommendations.add(
                Recommendation.builder()
                        .name("17")
                        .description("17")
                        .recommendationType("SMART_CONTROL")
                        .upgradePercentage(5.0f)
                        .upgradedICEE("B")
                        .upgradedAnualCost(certificate.getAnnualCost() * 0.95f)
                        .totalPrice(1500)
                        .build()
        );

        return recommendations;
    }
}
