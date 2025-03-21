package com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper;

import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.*;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.mapper.RecommendationMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OfficialCertificateMapper {

    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final RecommendationMapper recommendationMapper;

    public OfficialCertificateMapper(AddressMapper addressMapper, AddressRepository addressRepository, RecommendationMapper recommendationMapper) {
        this.recommendationMapper = recommendationMapper;
        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
    }

    public OfficialCertificateDTO toDTO(OfficialCertificate officialCertificate) {
        if (officialCertificate == null) {
            return null;
        }
            return OfficialCertificateDTO.builder()
                    .certificateId(officialCertificate.getCertificateId())
                    .address(addressMapper.toDTOWithoutCertificate(officialCertificate.getAddress()))
                    .certificateType(officialCertificate.getCertificateType())
                    .recommendations(officialCertificate.getRecommendations().stream().map(recommendationMapper::toDTO).collect(Collectors.toList()))
                    .documentId(officialCertificate.getDocumentId())
                    .floor(officialCertificate.getFloor())
                    .door(officialCertificate.getDoor())
                    .climateZone(officialCertificate.getClimateZone())
                    .cadastreMeters(officialCertificate.getCadastreMeters())
                    .buildingYear(officialCertificate.getBuildingYear())
                    .buildingUse(officialCertificate.getBuildingUse())
                    .nonRenewablePrimaryQualification(officialCertificate.getNonRenewablePrimaryQualification())
                    .nonRenewablePrimaryEnergy(officialCertificate.getNonRenewablePrimaryEnergy())
                    .co2Qualification(officialCertificate.getCo2Qualification())
                    .co2Emissions(officialCertificate.getCo2Emissions())
                    .finalEnergyConsumption(officialCertificate.getFinalEnergyConsumption())
                    .annualCost(officialCertificate.getAnnualCost())
                    .electricVehicle(officialCertificate.isElectricVehicle())
                    .solarThermal(officialCertificate.isSolarThermal())
                    .photovoltaicSolar(officialCertificate.isPhotovoltaicSolar())
                    .biomass(officialCertificate.isBiomass())
                    .districtNet(officialCertificate.isDistrictNet())
                    .geothermal(officialCertificate.isGeothermal())
                    .insulation(officialCertificate.getInsulation())
                    .windowEfficiency(officialCertificate.getWindowEfficiency())
                    .heatingQualification(officialCertificate.getHeatingQualification())
                    .heatingEmissions(officialCertificate.getHeatingEmissions())
                    .refrigerationQualification(officialCertificate.getRefrigerationQualification())
                    .refrigerationEmissions(officialCertificate.getRefrigerationEmissions())
                    .acsQualification(officialCertificate.getAcsQualification())
                    .acsEmissions(officialCertificate.getAcsEmissions())
                    .lightingQualification(officialCertificate.getLightingQualification())
                    .lightingEmissions(officialCertificate.getLightingEmissions())
                    .residentialUseVentilation(officialCertificate.getResidentialUseVentilation())
                    .energeticRehabilitation(officialCertificate.isEnergeticRehabilitation())
                    .entryDate(officialCertificate.getEntryDate())
                    .build();
    }

    public OfficialCertificate toEntity(CreateOfficialCertificateDTO createOfficialCertificateDTO) {
        if (createOfficialCertificateDTO == null) {
            return null;
        }
            return OfficialCertificate.builder()
                    .address(addressRepository.findByAddressId(addressMapper.toEntity(createOfficialCertificateDTO.getCreateAddressDTO()).getAddressId()))
                    .certificateType(createOfficialCertificateDTO.getCertificateType())
                    .recommendations(createOfficialCertificateDTO.getRecommendations())
                    .documentId(createOfficialCertificateDTO.getDocumentId())
                    .floor(createOfficialCertificateDTO.getFloor())
                    .door(createOfficialCertificateDTO.getDoor())
                    .climateZone(createOfficialCertificateDTO.getClimateZone())
                    .cadastreMeters(createOfficialCertificateDTO.getCadastreMeters())
                    .buildingYear(createOfficialCertificateDTO.getBuildingYear())
                    .buildingUse(createOfficialCertificateDTO.getBuildingUse())
                    .nonRenewablePrimaryQualification(createOfficialCertificateDTO.getNonRenewablePrimaryQualification())
                    .nonRenewablePrimaryEnergy(createOfficialCertificateDTO.getNonRenewablePrimaryEnergy())
                    .co2Qualification(createOfficialCertificateDTO.getCo2Qualification())
                    .co2Emissions(createOfficialCertificateDTO.getCo2Emissions())
                    .finalEnergyConsumption(createOfficialCertificateDTO.getFinalEnergyConsumption())
                    .annualCost(createOfficialCertificateDTO.getAnnualCost())
                    .electricVehicle(createOfficialCertificateDTO.isElectricVehicle())
                    .solarThermal(createOfficialCertificateDTO.isSolarThermal())
                    .photovoltaicSolar(createOfficialCertificateDTO.isPhotovoltaicSolar())
                    .biomass(createOfficialCertificateDTO.isBiomass())
                    .districtNet(createOfficialCertificateDTO.isDistrictNet())
                    .geothermal(createOfficialCertificateDTO.isGeothermal())
                    .insulation(createOfficialCertificateDTO.getInsulation())
                    .windowEfficiency(createOfficialCertificateDTO.getWindowEfficiency())
                    .heatingQualification(createOfficialCertificateDTO.getHeatingQualification())
                    .heatingEmissions(createOfficialCertificateDTO.getHeatingEmissions())
                    .refrigerationQualification(createOfficialCertificateDTO.getRefrigerationQualification())
                    .refrigerationEmissions(createOfficialCertificateDTO.getRefrigerationEmissions())
                    .acsQualification(createOfficialCertificateDTO.getAcsQualification())
                    .acsEmissions(createOfficialCertificateDTO.getAcsEmissions())
                    .lightingQualification(createOfficialCertificateDTO.getLightingQualification())
                    .lightingEmissions(createOfficialCertificateDTO.getLightingEmissions())
                    .residentialUseVentilation(createOfficialCertificateDTO.getResidentialUseVentilation())
                    .energeticRehabilitation(createOfficialCertificateDTO.isEnergeticRehabilitation())
                    .entryDate(createOfficialCertificateDTO.getEntryDate())
                    .build();

    }

    public OfficialCertificateWFE OfficialCertificateWFEToDTO(OfficialCertificate certificate) {
        if (certificate == null) {
            System.out.println("Certificate is null");
            return null;
        }
        System.out.println("Certificate is not null");
        return OfficialCertificateWFE.builder()
                .certificateId(certificate.getCertificateId())
                .certificateType(certificate.getCertificateType())
                .documentId(certificate.getDocumentId())
                .floor(certificate.getFloor())
                .door(certificate.getDoor())
                .climateZone(certificate.getClimateZone())
                .cadastreMeters(certificate.getCadastreMeters())
                .buildingYear(certificate.getBuildingYear())
                .buildingUse(certificate.getBuildingUse())
                .nonRenewablePrimaryQualification(certificate.getNonRenewablePrimaryQualification())
                .nonRenewablePrimaryEnergy(certificate.getNonRenewablePrimaryEnergy())
                .co2Qualification(certificate.getCo2Qualification())
                .co2Emissions(certificate.getCo2Emissions())
                .finalEnergyConsumption(certificate.getFinalEnergyConsumption())
                .annualCost(certificate.getAnnualCost())
                .electricVehicle(certificate.isElectricVehicle())
                .solarThermal(certificate.isSolarThermal())
                .photovoltaicSolar(certificate.isPhotovoltaicSolar())
                .biomass(certificate.isBiomass())
                .districtNet(certificate.isDistrictNet())
                .geothermal(certificate.isGeothermal())
                .insulation(certificate.getInsulation())
                .windowEfficiency(certificate.getWindowEfficiency())
                .heatingQualification(certificate.getHeatingQualification())
                .heatingEmissions(certificate.getHeatingEmissions())
                .refrigerationQualification(certificate.getRefrigerationQualification())
                .refrigerationEmissions(certificate.getRefrigerationEmissions())
                .acsQualification(certificate.getAcsQualification())
                .acsEmissions(certificate.getAcsEmissions())
                .lightingQualification(certificate.getLightingQualification())
                .lightingEmissions(certificate.getLightingEmissions())
                .residentialUseVentilation(certificate.getResidentialUseVentilation())
                .energeticRehabilitation(certificate.isEnergeticRehabilitation())
                .entryDate(certificate.getEntryDate())
                .build();

    }
}
