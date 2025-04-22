package com.EcoMentor_backend.EcoMentor.Recommendation.useCases;

import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAverageValuesInAZonUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AverageValuesDTO;
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
public class GenerateZoneRecommendationsUseCase {

    private final RecommendationRepository recommendationRepository;
    private final OfficialCertificateRepository certificateRepository;
    private final GetAverageValuesInAZonUseCase averageValuesUseCase;

    public GenerateZoneRecommendationsUseCase(RecommendationRepository recommendationRepository,
                                              OfficialCertificateRepository certificateRepository,
                                              GetAverageValuesInAZonUseCase averageValuesUseCase) {
        this.recommendationRepository = recommendationRepository;
        this.certificateRepository = certificateRepository;
        this.averageValuesUseCase = averageValuesUseCase;
    }

    public List<RecommendationDTO> execute(Long certificateId, Integer radius) {
        OfficialCertificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found"));

        // Obtener valores medios de la zona
        AverageValuesDTO averages = averageValuesUseCase.execute(certificate.getAddress().getLocation().getY(),
                certificate.getAddress().getLocation().getX(), radius * 1000);

        List<Recommendation> recommendations = generateByZone(certificate, averages);

        // Asociar y guardar
        for (Recommendation recommendation : recommendations) {
            if (recommendation.getCertificates() == null) {
                recommendation.setCertificates(new ArrayList<>());
            }
            recommendation.getCertificates().add(certificate);
            certificate.getRecommendations().add(recommendation);
        }

        recommendationRepository.saveAll(recommendations);
        certificateRepository.save(certificate);

        // Convertir a DTOs
        List<RecommendationDTO> dtoList = new ArrayList<>();
        for (Recommendation recommendation : recommendations) {
            RecommendationDTO dto = new RecommendationDTO();
            dto.setRecommendationId(recommendation.getRecommendationId());
            dto.setName(recommendation.getName());
            dto.setDescription(recommendation.getDescription());
            dto.setRecommendationType(recommendation.getRecommendationType());
            dto.setUpgradePercentage(recommendation.getUpgradePercentage());
            dto.setUpgradedICEE(recommendation.getUpgradedICEE());
            dto.setUpgradedAnualCost(recommendation.getUpgradedAnualCost());
            dto.setTotalPrice(recommendation.getTotalPrice());
            dtoList.add(dto);
        }
        return dtoList;
    }

    private List<Recommendation> generateByZone(OfficialCertificate certificate, AverageValuesDTO avg) {
        List<Recommendation> recommendations = new ArrayList<>();

        // Aislamiento por debajo del promedio de la zona
        if (certificate.getInsulation() < avg.getInsulation()) {
            recommendations.add(
                    Recommendation.builder()
                            .name("1")
                            .description("1")
                            .recommendationType("INSULATION")
                            .upgradePercentage((avg.getInsulation() - certificate.getInsulation())
                                    * 100 / avg.getInsulation())
                            .upgradedICEE("A")
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.9f)
                            .totalPrice(4000)
                            .build()
            );
        }

        // Paneles solares si no hay
        if (!certificate.isPhotovoltaicSolar()) {
            recommendations.add(
                    Recommendation.builder()
                            .name("2")
                            .description("2")
                            .recommendationType("SOLAR")
                            .upgradePercentage(20.0f)
                            .upgradedICEE("A")
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.8f)
                            .totalPrice(6000)
                            .build()
            );
        }

        // Eficiencia ventanas por debajo del promedio
        if (certificate.getWindowEfficiency() < avg.getWindowEfficiency()) {
            recommendations.add(
                    Recommendation.builder()
                            .name("3")
                            .description("3")
                            .recommendationType("WINDOWS")
                            .upgradePercentage((avg.getWindowEfficiency() - certificate.getWindowEfficiency())
                                    * 100 / avg.getWindowEfficiency())
                            .upgradedICEE("A")
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.85f)
                            .totalPrice(3000)
                            .build()
            );
        }

        // Bomba de calor si emisiones de calefacción superiores al promedio
        if (certificate.getHeatingEmissions() > avg.getHeatingEmissions()) {
            recommendations.add(
                    Recommendation.builder()
                            .name("4")
                            .description("4")
                            .recommendationType("HEAT_PUMP")
                            .upgradePercentage((certificate.getHeatingEmissions() - avg.getHeatingEmissions())
                                    * 100 / avg.getHeatingEmissions())
                            .upgradedICEE("A")
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.85f)
                            .totalPrice(7000)
                            .build()
            );
        }

        // Biomasa si aplica
        if (!certificate.isBiomass() && certificate.getHeatingEmissions() > avg.getHeatingEmissions()) {
            recommendations.add(
                    Recommendation.builder()
                            .name("5")
                            .description("5")
                            .recommendationType("BIOMASS")
                            .upgradePercentage(12.0f)
                            .upgradedICEE("A")
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.87f)
                            .totalPrice(5000)
                            .build()
            );
        }

        // HVAC por encima del consumo final promedio
        if (certificate.getFinalEnergyConsumption() > avg.getFinalEnergyConsumption()) {
            recommendations.add(
                    Recommendation.builder()
                            .name("6")
                            .description("6")
                            .recommendationType("HVAC")
                            .upgradePercentage((certificate.getFinalEnergyConsumption()
                                    - avg.getFinalEnergyConsumption()) * 100 / avg.getFinalEnergyConsumption())
                            .upgradedICEE("A")
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.92f)
                            .totalPrice(4000)
                            .build()
            );
        }

        // Iluminación por encima del promedio de emisiones
        if (certificate.getLightingEmissions() > avg.getLightingEmissions()) {
            recommendations.add(
                    Recommendation.builder()
                            .name("7")
                            .description("7")
                            .recommendationType("LIGHTING")
                            .upgradePercentage((certificate.getLightingEmissions() - avg.getLightingEmissions())
                                    * 100 / avg.getLightingEmissions())
                            .upgradedICEE("A")
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

        // Optimización refrigeración
        if (certificate.getRefrigerationEmissions() > avg.getRefrigerationEmissions()) {
            recommendations.add(
                    Recommendation.builder()
                            .name("13")
                            .description("13")
                            .recommendationType("REFRIGERATION")
                            .upgradePercentage((certificate.getRefrigerationEmissions()
                                    - avg.getRefrigerationEmissions()) * 100 / avg.getRefrigerationEmissions())
                            .upgradedICEE("A")
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.9f)
                            .totalPrice(6000)
                            .build()
            );
        }

        // Agua caliente sanitaria por encima del promedio
        if (certificate.getAcsEmissions() > avg.getAcsEmissions()) {
            recommendations.add(
                    Recommendation.builder()
                            .name("14")
                            .description("14")
                            .recommendationType("WATER_HEATING")
                            .upgradePercentage((certificate.getAcsEmissions() - avg.getAcsEmissions())
                                    * 100 / avg.getAcsEmissions())
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
