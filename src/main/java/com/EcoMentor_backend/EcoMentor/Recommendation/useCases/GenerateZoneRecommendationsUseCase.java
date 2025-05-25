package com.EcoMentor_backend.EcoMentor.Recommendation.useCases;

import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAverageValuesInAZonUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AverageValuesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CustomCertificateRepositoryImpl;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculatorResultsDTO;
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
    private final CustomCertificateRepositoryImpl customCertificateRepository;

    public GenerateZoneRecommendationsUseCase(RecommendationRepository recommendationRepository,
                                              OfficialCertificateRepository certificateRepository,
                                              GetAverageValuesInAZonUseCase averageValuesUseCase,
                                              CustomCertificateRepositoryImpl customCertificateRepository) {
        this.recommendationRepository = recommendationRepository;
        this.certificateRepository = certificateRepository;
        this.averageValuesUseCase = averageValuesUseCase;
        this.customCertificateRepository = customCertificateRepository;
    }

    public List<RecommendationDTO> execute(Long certificateId, Integer radius) {
        OfficialCertificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found"));

        // Limpiar las recomendaciones existentes del certificado
        recommendationRepository.deleteAll(certificate.getRecommendations());
        certificate.getRecommendations().clear();

        // Obtener valores medios de la zona
        AverageValuesDTO averages = averageValuesUseCase.execute(certificate.getAddress().getLocation().getY(),
                certificate.getAddress().getLocation().getX(), radius);

        if (averages == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Average values not found");
        }

        List<Recommendation> recommendations = generateByZone(certificate, averages);

        // Asociar y guardar
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

        String climateZone = certificate.getClimateZone();
        String buildingUse = certificate.getBuildingUse();
        float nonRenewablePrimaryEnergyInitial = certificate.getNonRenewablePrimaryEnergy();
        boolean solarThermal = certificate.isSolarThermal();
        boolean photovoltaicSolar = certificate.isPhotovoltaicSolar();
        boolean biomass = certificate.isBiomass();
        boolean districtNet = certificate.isDistrictNet();
        boolean geothermal = certificate.isGeothermal();
        float insulation = certificate.getInsulation();
        float windowEfficiency = certificate.getWindowEfficiency();
        float heatingEmissionsInitial = certificate.getHeatingEmissions();
        float refrigerationEmissionsInitial = certificate.getRefrigerationEmissions();
        float acsEmissionsInitial = certificate.getAcsEmissions();
        float lightingEmissionsInitial = certificate.getLightingEmissions();
        float residentialUseVentilation = certificate.getResidentialUseVentilation();

        // Aislamiento por debajo del promedio de la zona
        if (certificate.getInsulation() < avg.getInsulation()) {
            CalculatorResultsDTO resultsDTO = customCertificateRepository.calculateRecomendationQualifications(
                    climateZone,
                    buildingUse,
                    nonRenewablePrimaryEnergyInitial,
                    solarThermal,
                    photovoltaicSolar,
                    biomass,
                    districtNet,
                    geothermal,
                    customCertificateRepository.calculateAproxInsulation(2, buildingUse),
                    windowEfficiency,
                    heatingEmissionsInitial,
                    refrigerationEmissionsInitial,
                    acsEmissionsInitial,
                    lightingEmissionsInitial,
                    residentialUseVentilation
            );
            recommendations.add(
                    Recommendation.builder()
                            .name("1")
                            .description("1")
                            .recommendationType("INSULATION")
                            .upgradePercentage(resultsDTO.getIoNonRenewablePrimaryEnergy()
                                    / nonRenewablePrimaryEnergyInitial * 100)
                            .upgradedICEE(resultsDTO.getNonRenewablePrimaryQualification().toString())
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.9f)
                            .totalPrice(4000)
                            .build()
            );
        }

        // Paneles solares si no hay
        if (!certificate.isPhotovoltaicSolar()) {
            CalculatorResultsDTO resultsDTO = customCertificateRepository.calculateRecomendationQualifications(
                    climateZone,
                    buildingUse,
                    nonRenewablePrimaryEnergyInitial,
                    solarThermal,
                    true,
                    biomass,
                    districtNet,
                    geothermal,
                    insulation,
                    windowEfficiency,
                    heatingEmissionsInitial,
                    refrigerationEmissionsInitial,
                    acsEmissionsInitial,
                    lightingEmissionsInitial,
                    residentialUseVentilation
            );
            recommendations.add(
                    Recommendation.builder()
                            .name("2")
                            .description("2")
                            .recommendationType("SOLAR")
                            .upgradePercentage(resultsDTO.getIoNonRenewablePrimaryEnergy()
                                    / nonRenewablePrimaryEnergyInitial * 100)
                            .upgradedICEE(resultsDTO.getNonRenewablePrimaryQualification().toString())
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.8f)
                            .totalPrice(6000)
                            .build()
            );
        }

        // Eficiencia ventanas por debajo del promedio
        if (certificate.getWindowEfficiency() < avg.getWindowEfficiency()) {
            CalculatorResultsDTO resultsDTO = customCertificateRepository.calculateRecomendationQualifications(
                    climateZone,
                    buildingUse,
                    nonRenewablePrimaryEnergyInitial,
                    solarThermal,
                    photovoltaicSolar,
                    biomass,
                    districtNet,
                    geothermal,
                    insulation,
                    customCertificateRepository.calculateAproxWindowEfficiciency(2, buildingUse),
                    heatingEmissionsInitial,
                    refrigerationEmissionsInitial,
                    acsEmissionsInitial,
                    lightingEmissionsInitial,
                    residentialUseVentilation
            );
            recommendations.add(
                    Recommendation.builder()
                            .name("3")
                            .description("3")
                            .recommendationType("WINDOWS")
                            .upgradePercentage(resultsDTO.getIoNonRenewablePrimaryEnergy()
                                    / nonRenewablePrimaryEnergyInitial * 100)
                            .upgradedICEE(resultsDTO.getNonRenewablePrimaryQualification().toString())
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.85f)
                            .totalPrice(3000)
                            .build()
            );
        }

        // Bomba de calor si emisiones de calefacción superiores al promedio
        if (certificate.getHeatingEmissions() > avg.getHeatingEmissions()) {
            CalculatorResultsDTO resultsDTO = customCertificateRepository.calculateRecomendationQualifications(
                    climateZone,
                    buildingUse,
                    nonRenewablePrimaryEnergyInitial,
                    solarThermal,
                    photovoltaicSolar,
                    biomass,
                    districtNet,
                    geothermal,
                    insulation,
                    windowEfficiency,
                    heatingEmissionsInitial * 0.75f, // Mejora en emisiones de calefacción
                    refrigerationEmissionsInitial,
                    acsEmissionsInitial,
                    lightingEmissionsInitial,
                    residentialUseVentilation
            );
            recommendations.add(
                    Recommendation.builder()
                            .name("4")
                            .description("4")
                            .recommendationType("HEAT_PUMP")
                            .upgradePercentage(resultsDTO.getIoNonRenewablePrimaryEnergy()
                                    / nonRenewablePrimaryEnergyInitial * 100)
                            .upgradedICEE(resultsDTO.getNonRenewablePrimaryQualification().toString())
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.85f)
                            .totalPrice(7000)
                            .build()
            );
        }

        // Biomasa si aplica
        if (!certificate.isBiomass()) {
            CalculatorResultsDTO resultsDTO = customCertificateRepository.calculateRecomendationQualifications(
                    climateZone,
                    buildingUse,
                    nonRenewablePrimaryEnergyInitial,
                    solarThermal,
                    photovoltaicSolar,
                    true,
                    districtNet,
                    geothermal,
                    insulation,
                    windowEfficiency,
                    heatingEmissionsInitial,
                    refrigerationEmissionsInitial,
                    acsEmissionsInitial,
                    lightingEmissionsInitial,
                    residentialUseVentilation
            );
            recommendations.add(
                    Recommendation.builder()
                            .name("5")
                            .description("5")
                            .recommendationType("BIOMASS")
                            .upgradePercentage(resultsDTO.getIoNonRenewablePrimaryEnergy()
                                    / nonRenewablePrimaryEnergyInitial * 100)
                            .upgradedICEE(resultsDTO.getNonRenewablePrimaryQualification().toString())
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.87f)
                            .totalPrice(5000)
                            .build()
            );
        }

        // HVAC por encima del consumo final promedio
        if (certificate.getFinalEnergyConsumption() > avg.getFinalEnergyConsumption()) {
            CalculatorResultsDTO resultsDTO = customCertificateRepository.calculateRecomendationQualifications(
                    climateZone,
                    buildingUse,
                    nonRenewablePrimaryEnergyInitial,
                    solarThermal,
                    photovoltaicSolar,
                    biomass,
                    districtNet,
                    geothermal,
                    insulation,
                    customCertificateRepository.calculateAproxWindowEfficiciency(2, buildingUse),
                    heatingEmissionsInitial * 0.8f, // Mejora del 10% en emisiones de calefacción
                    refrigerationEmissionsInitial,
                    acsEmissionsInitial,
                    lightingEmissionsInitial,
                    residentialUseVentilation
            );
            recommendations.add(
                    Recommendation.builder()
                            .name("6")
                            .description("6")
                            .recommendationType("HVAC")
                            .upgradePercentage(resultsDTO.getIoNonRenewablePrimaryEnergy()
                                    / nonRenewablePrimaryEnergyInitial * 100)
                            .upgradedICEE(resultsDTO.getNonRenewablePrimaryQualification().toString())
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.92f)
                            .totalPrice(4000)
                            .build()
            );
        }

        // Iluminación por encima del promedio de emisiones
        if (certificate.getLightingEmissions() > avg.getLightingEmissions()) {
            CalculatorResultsDTO resultsDTO = customCertificateRepository.calculateRecomendationQualifications(
                    climateZone,
                    buildingUse,
                    nonRenewablePrimaryEnergyInitial,
                    solarThermal,
                    photovoltaicSolar,
                    biomass,
                    districtNet,
                    geothermal,
                    insulation,
                    windowEfficiency,
                    heatingEmissionsInitial,
                    refrigerationEmissionsInitial,
                    acsEmissionsInitial,
                    lightingEmissionsInitial * 0.85f,
                    residentialUseVentilation
            );
            recommendations.add(
                    Recommendation.builder()
                            .name("7")
                            .description("7")
                            .recommendationType("LIGHTING")
                            .upgradePercentage(resultsDTO.getIoNonRenewablePrimaryEnergy()
                                    / nonRenewablePrimaryEnergyInitial * 100)
                            .upgradedICEE(resultsDTO.getNonRenewablePrimaryQualification().toString())
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.95f)
                            .totalPrice(2000)
                            .build()
            );
        }

        // Solar térmico
        if (!certificate.isSolarThermal()) {
            CalculatorResultsDTO resultsDTO = customCertificateRepository.calculateRecomendationQualifications(
                    climateZone,
                    buildingUse,
                    nonRenewablePrimaryEnergyInitial,
                    true,
                    photovoltaicSolar,
                    biomass,
                    districtNet,
                    geothermal,
                    insulation,
                    windowEfficiency,
                    heatingEmissionsInitial,
                    refrigerationEmissionsInitial,
                    acsEmissionsInitial,
                    lightingEmissionsInitial,
                    residentialUseVentilation
            );
            recommendations.add(
                    Recommendation.builder()
                            .name("8")
                            .description("8")
                            .recommendationType("SOLAR_THERMAL")
                            .upgradePercentage(resultsDTO.getIoNonRenewablePrimaryEnergy()
                                    / nonRenewablePrimaryEnergyInitial * 100)
                            .upgradedICEE(resultsDTO.getNonRenewablePrimaryQualification().toString())
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.75f)
                            .totalPrice(3500)
                            .build()
            );
        }

        // Red de calor urbano
        if (!certificate.isDistrictNet()) {
            CalculatorResultsDTO resultsDTO = customCertificateRepository.calculateRecomendationQualifications(
                    climateZone,
                    buildingUse,
                    nonRenewablePrimaryEnergyInitial,
                    solarThermal,
                    photovoltaicSolar,
                    biomass,
                    true,
                    geothermal,
                    insulation,
                    windowEfficiency,
                    heatingEmissionsInitial,
                    refrigerationEmissionsInitial,
                    acsEmissionsInitial,
                    lightingEmissionsInitial,
                    residentialUseVentilation
            );
            recommendations.add(
                    Recommendation.builder()
                            .name("9")
                            .description("9")
                            .recommendationType("DISTRICT_HEATING")
                            .upgradePercentage(resultsDTO.getIoNonRenewablePrimaryEnergy()
                                    / nonRenewablePrimaryEnergyInitial * 100)
                            .upgradedICEE(resultsDTO.getNonRenewablePrimaryQualification().toString())
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.82f)
                            .totalPrice(2000)
                            .build()
            );
        }

        // Geotermia
        if (!certificate.isGeothermal()) {
            CalculatorResultsDTO resultsDTO = customCertificateRepository.calculateRecomendationQualifications(
                    climateZone,
                    buildingUse,
                    nonRenewablePrimaryEnergyInitial,
                    solarThermal,
                    photovoltaicSolar,
                    biomass,
                    districtNet,
                    true,
                    insulation,
                    windowEfficiency,
                    heatingEmissionsInitial,
                    refrigerationEmissionsInitial,
                    acsEmissionsInitial,
                    lightingEmissionsInitial,
                    residentialUseVentilation
            );
            recommendations.add(
                    Recommendation.builder()
                            .name("10")
                            .description("10")
                            .recommendationType("GEOTHERMAL")
                            .upgradePercentage(resultsDTO.getIoNonRenewablePrimaryEnergy()
                                    / nonRenewablePrimaryEnergyInitial * 100)
                            .upgradedICEE(resultsDTO.getNonRenewablePrimaryQualification().toString())
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.8f)
                            .totalPrice(12000)
                            .build()
            );
        }

        // Carga vehículo eléctrico
        if (!certificate.isElectricVehicle()) {
            CalculatorResultsDTO resultsDTO = customCertificateRepository.calculateRecomendationQualifications(
                    climateZone,
                    buildingUse,
                    nonRenewablePrimaryEnergyInitial,
                    solarThermal,
                    photovoltaicSolar,
                    biomass,
                    districtNet,
                    geothermal,
                    insulation,
                    windowEfficiency,
                    heatingEmissionsInitial,
                    refrigerationEmissionsInitial,
                    acsEmissionsInitial,
                    lightingEmissionsInitial * 0.97f,
                    residentialUseVentilation
            );
            recommendations.add(
                    Recommendation.builder()
                            .name("11")
                            .description("11")
                            .recommendationType("EV_CHARGING")
                            .upgradePercentage(resultsDTO.getIoNonRenewablePrimaryEnergy()
                                    / nonRenewablePrimaryEnergyInitial * 100)
                            .upgradedICEE(resultsDTO.getNonRenewablePrimaryQualification().toString())
                            .upgradedAnualCost(certificate.getAnnualCost() * 1.05f)
                            .totalPrice(800)
                            .build()
            );
        }

        // Recuperación de calor en ventilación
        if (certificate.getResidentialUseVentilation() > customCertificateRepository
                .calculateAproxResidentialUseVentilation(3, buildingUse)) {
            CalculatorResultsDTO resultsDTO = customCertificateRepository.calculateRecomendationQualifications(
                    climateZone,
                    buildingUse,
                    nonRenewablePrimaryEnergyInitial,
                    solarThermal,
                    photovoltaicSolar,
                    biomass,
                    districtNet,
                    geothermal,
                    insulation,
                    windowEfficiency,
                    heatingEmissionsInitial,
                    refrigerationEmissionsInitial,
                    acsEmissionsInitial,
                    lightingEmissionsInitial,
                    customCertificateRepository.calculateAproxResidentialUseVentilation(2,
                            buildingUse)
            );
            recommendations.add(
                    Recommendation.builder()
                            .name("12")
                            .description("12")
                            .recommendationType("VENTILATION")
                            .upgradePercentage(resultsDTO.getIoNonRenewablePrimaryEnergy()
                                    / nonRenewablePrimaryEnergyInitial * 100)
                            .upgradedICEE(resultsDTO.getNonRenewablePrimaryQualification().toString())
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.88f)
                            .totalPrice(2500)
                            .build()
            );
        }

        // Optimización refrigeración
        if (certificate.getRefrigerationEmissions() > avg.getRefrigerationEmissions()) {
            CalculatorResultsDTO resultsDTO = customCertificateRepository.calculateRecomendationQualifications(
                    climateZone,
                    buildingUse,
                    nonRenewablePrimaryEnergyInitial,
                    solarThermal,
                    photovoltaicSolar,
                    biomass,
                    districtNet,
                    geothermal,
                    insulation,
                    windowEfficiency,
                    heatingEmissionsInitial,
                    refrigerationEmissionsInitial * 0.85f,
                    acsEmissionsInitial,
                    lightingEmissionsInitial,
                    residentialUseVentilation
            );
            recommendations.add(
                    Recommendation.builder()
                            .name("13")
                            .description("13")
                            .recommendationType("REFRIGERATION")
                            .upgradePercentage(resultsDTO.getIoNonRenewablePrimaryEnergy()
                                    / nonRenewablePrimaryEnergyInitial * 100)
                            .upgradedICEE(resultsDTO.getNonRenewablePrimaryQualification().toString())
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.9f)
                            .totalPrice(6000)
                            .build()
            );
        }

        // Agua caliente sanitaria por encima del promedio
        if (certificate.getAcsEmissions() > avg.getAcsEmissions()) {
            CalculatorResultsDTO resultsDTO = customCertificateRepository.calculateRecomendationQualifications(
                    climateZone,
                    buildingUse,
                    nonRenewablePrimaryEnergyInitial,
                    solarThermal,
                    photovoltaicSolar,
                    biomass,
                    districtNet,
                    geothermal,
                    insulation,
                    windowEfficiency,
                    heatingEmissionsInitial,
                    refrigerationEmissionsInitial,
                    acsEmissionsInitial * 0.85f,
                    lightingEmissionsInitial,
                    residentialUseVentilation
            );
            recommendations.add(
                    Recommendation.builder()
                            .name("14")
                            .description("14")
                            .recommendationType("WATER_HEATING")
                            .upgradePercentage(resultsDTO.getIoNonRenewablePrimaryEnergy()
                                    / nonRenewablePrimaryEnergyInitial * 100)
                            .upgradedICEE(resultsDTO.getNonRenewablePrimaryQualification().toString())
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.85f)
                            .totalPrice(4500)
                            .build()
            );
        }

        // Rehabilitación energética profunda
        if (!certificate.isEnergeticRehabilitation()) {
            CalculatorResultsDTO resultsDTO = customCertificateRepository.calculateRecomendationQualifications(
                    climateZone,
                    buildingUse,
                    nonRenewablePrimaryEnergyInitial,
                    solarThermal,
                    photovoltaicSolar,
                    biomass,
                    districtNet,
                    geothermal,
                    insulation * 0.91f,
                    windowEfficiency * 0.87f,
                    heatingEmissionsInitial * 0.94f,
                    refrigerationEmissionsInitial * 0.90f,
                    acsEmissionsInitial * 0.90f,
                    lightingEmissionsInitial * 0.92f,
                    residentialUseVentilation
            );
            recommendations.add(
                    Recommendation.builder()
                            .name("15")
                            .description("15")
                            .recommendationType("REHABILITATION")
                            .upgradePercentage(resultsDTO.getIoNonRenewablePrimaryEnergy()
                                    / nonRenewablePrimaryEnergyInitial * 100)
                            .upgradedICEE(resultsDTO.getNonRenewablePrimaryQualification().toString())
                            .upgradedAnualCost(certificate.getAnnualCost() * 0.65f)
                            .totalPrice(15000)
                            .build()
            );
        }

        // Retro‑comisionado
        CalculatorResultsDTO resultsDTO = customCertificateRepository.calculateRecomendationQualifications(
                climateZone,
                buildingUse,
                nonRenewablePrimaryEnergyInitial * 0.95f,
                solarThermal,
                photovoltaicSolar,
                biomass,
                districtNet,
                geothermal,
                insulation,
                windowEfficiency * 0.98f,
                heatingEmissionsInitial * 0.95f,
                refrigerationEmissionsInitial * 0.95f,
                acsEmissionsInitial * 0.97f,
                lightingEmissionsInitial * 0.89f,
                residentialUseVentilation * 0.95f
        );
        recommendations.add(
                Recommendation.builder()
                        .name("16")
                        .description("16")
                        .recommendationType("COMMISSIONING")
                        .upgradePercentage(resultsDTO.getIoNonRenewablePrimaryEnergy()
                                / nonRenewablePrimaryEnergyInitial * 100)
                        .upgradedICEE(resultsDTO.getNonRenewablePrimaryQualification().toString())
                        .upgradedAnualCost(certificate.getAnnualCost() * 0.85f)
                        .totalPrice(2000)
                        .build()
        );

        // Control inteligente
        resultsDTO = customCertificateRepository.calculateRecomendationQualifications(
                climateZone,
                buildingUse,
                nonRenewablePrimaryEnergyInitial,
                solarThermal,
                photovoltaicSolar,
                biomass,
                districtNet,
                geothermal,
                insulation,
                windowEfficiency * 0.98f,
                heatingEmissionsInitial * 0.97f,
                refrigerationEmissionsInitial * 0.96f,
                acsEmissionsInitial,
                lightingEmissionsInitial * 0.98f,
                residentialUseVentilation * 0.97f
        );
        recommendations.add(
                Recommendation.builder()
                        .name("17")
                        .description("17")
                        .recommendationType("SMART_CONTROL")
                        .upgradePercentage(resultsDTO.getIoNonRenewablePrimaryEnergy()
                                / nonRenewablePrimaryEnergyInitial * 100)
                        .upgradedICEE(resultsDTO.getNonRenewablePrimaryQualification().toString())
                        .upgradedAnualCost(certificate.getAnnualCost() * 0.95f)
                        .totalPrice(1500)
                        .build()
        );

        return recommendations;
    }
}
