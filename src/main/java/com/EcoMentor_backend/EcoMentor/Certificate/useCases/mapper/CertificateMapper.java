package com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper;

import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.UnofficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateOfficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateUnofficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.OfficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.OfficialCertificateWFEDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.UnofficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.UnofficialCertificateWFEDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.infrastructure.repositories.RecommendationRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.mapper.RecommendationMapper;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;



@Component
public class CertificateMapper {

    private final AddressMapper addressMapper;
    private final RecommendationMapper recommendationMapper;
    private final AddressRepository addressRepository;


    public CertificateMapper(AddressMapper addressMapper, AddressRepository addressRepository,
                             RecommendationMapper recommendationMapper) {
        this.recommendationMapper = recommendationMapper;
        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
    }

    public CertificateWithoutForeignEntitiesDTO toDTOW(Certificate certificate) {
        if (certificate == null) {
            return null;
        }

        if (certificate instanceof OfficialCertificate officialCertificate) {
            return OfficialCertificateWFEDTO.builder()
                    .certificateId(officialCertificate.getCertificateId())
                    .certificateType(officialCertificate.getCertificateType())
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
        } else if (certificate instanceof UnofficialCertificate unofficialCertificate) {
            return UnofficialCertificateWFEDTO.builder()
                    .certificateId(unofficialCertificate.getCertificateId())
                    .certificateType(unofficialCertificate.getCertificateType())
                    .floor(unofficialCertificate.getFloor())
                    .door(unofficialCertificate.getDoor())
                    .climateZone(unofficialCertificate.getClimateZone())
                    .buildingYear(unofficialCertificate.getBuildingYear())
                    .buildingUse(unofficialCertificate.getBuildingUse())
                    .nonRenewablePrimaryQualification(unofficialCertificate.getNonRenewablePrimaryQualification())
                    .nonRenewablePrimaryEnergy(unofficialCertificate.getNonRenewablePrimaryEnergy())
                    .co2Qualification(unofficialCertificate.getCo2Qualification())
                    .co2Emissions(unofficialCertificate.getCo2Emissions())
                    .finalEnergyConsumption(unofficialCertificate.getFinalEnergyConsumption())
                    .annualCost(unofficialCertificate.getAnnualCost())
                    .electricVehicle(unofficialCertificate.isElectricVehicle())
                    .solarThermal(unofficialCertificate.isSolarThermal())
                    .photovoltaicSolar(unofficialCertificate.isPhotovoltaicSolar())
                    .biomass(unofficialCertificate.isBiomass())
                    .districtNet(unofficialCertificate.isDistrictNet())
                    .geothermal(unofficialCertificate.isGeothermal())
                    .insulation(unofficialCertificate.getInsulation())
                    .windowEfficiency(unofficialCertificate.getWindowEfficiency())
                    .heatingQualification(unofficialCertificate.getHeatingQualification())
                    .heatingEmissions(unofficialCertificate.getHeatingEmissions())
                    .refrigerationQualification(unofficialCertificate.getRefrigerationQualification())
                    .refrigerationEmissions(unofficialCertificate.getRefrigerationEmissions())
                    .acsQualification(unofficialCertificate.getAcsQualification())
                    .acsEmissions(unofficialCertificate.getAcsEmissions())
                    .lightingQualification(unofficialCertificate.getLightingQualification())
                    .lightingEmissions(unofficialCertificate.getLightingEmissions())
                    .residentialUseVentilation(unofficialCertificate.getResidentialUseVentilation())
                    .energeticRehabilitation(unofficialCertificate.isEnergeticRehabilitation())
                    .creationDate(unofficialCertificate.getCreationDate())
                    .build();

        } else {
            return CertificateWithoutForeignEntitiesDTO.builder()
                    .certificateId(certificate.getCertificateId())
                    .certificateType(certificate.getCertificateType())
                    .build();
        }
    }

    public CertificateDTO toDTO(Certificate certificate) {
        if (certificate == null) {
            return null;
        }

        if (certificate instanceof OfficialCertificate officialCertificate) {
            return OfficialCertificateDTO.builder()
                    .certificateId(officialCertificate.getCertificateId())
                    .addressDTO(addressMapper.toDTOWithoutCertificate(officialCertificate.getAddress()))
                    .certificateType(officialCertificate.getCertificateType())
                    .recommendations(officialCertificate.getRecommendations().stream()
                            .map(recommendationMapper::toDTO).collect(Collectors.toList()))
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
        } else if (certificate instanceof UnofficialCertificate unofficialCertificate) {
            return UnofficialCertificateDTO.builder()
                    .certificateId(unofficialCertificate.getCertificateId())
                    .addressDTO(addressMapper.toDTOWithoutCertificate(unofficialCertificate.getAddress()))
                    .certificateType(unofficialCertificate.getCertificateType())
                    .recommendations(unofficialCertificate.getRecommendations().stream()
                            .map(recommendationMapper::toDTO).collect(Collectors.toList()))
                    .floor(unofficialCertificate.getFloor())
                    .door(unofficialCertificate.getDoor())
                    .climateZone(unofficialCertificate.getClimateZone())
                    .buildingYear(unofficialCertificate.getBuildingYear())
                    .buildingUse(unofficialCertificate.getBuildingUse())
                    .nonRenewablePrimaryQualification(unofficialCertificate
                            .getNonRenewablePrimaryQualification())
                    .nonRenewablePrimaryEnergy(unofficialCertificate.getNonRenewablePrimaryEnergy())
                    .co2Qualification(unofficialCertificate.getCo2Qualification())
                    .co2Emissions(unofficialCertificate.getCo2Emissions())
                    .finalEnergyConsumption(unofficialCertificate.getFinalEnergyConsumption())
                    .annualCost(unofficialCertificate.getAnnualCost())
                    .electricVehicle(unofficialCertificate.isElectricVehicle())
                    .solarThermal(unofficialCertificate.isSolarThermal())
                    .photovoltaicSolar(unofficialCertificate.isPhotovoltaicSolar())
                    .biomass(unofficialCertificate.isBiomass())
                    .districtNet(unofficialCertificate.isDistrictNet())
                    .geothermal(unofficialCertificate.isGeothermal())
                    .insulation(unofficialCertificate.getInsulation())
                    .windowEfficiency(unofficialCertificate.getWindowEfficiency())
                    .heatingQualification(unofficialCertificate.getHeatingQualification())
                    .heatingEmissions(unofficialCertificate.getHeatingEmissions())
                    .refrigerationQualification(unofficialCertificate.getRefrigerationQualification())
                    .refrigerationEmissions(unofficialCertificate.getRefrigerationEmissions())
                    .acsQualification(unofficialCertificate.getAcsQualification())
                    .acsEmissions(unofficialCertificate.getAcsEmissions())
                    .lightingQualification(unofficialCertificate.getLightingQualification())
                    .lightingEmissions(unofficialCertificate.getLightingEmissions())
                    .residentialUseVentilation(unofficialCertificate.getResidentialUseVentilation())
                    .energeticRehabilitation(unofficialCertificate.isEnergeticRehabilitation())
                    .creationDate(unofficialCertificate.getCreationDate()).build();

        } else {
            return CertificateDTO.builder()
                    .certificateId(certificate.getCertificateId())
                    .addressDTO(addressMapper.toDTOWithoutCertificate(certificate.getAddress()))
                    .certificateType(certificate.getCertificateType())
                    .recommendations(certificate.getRecommendations().stream().map(recommendationMapper::toDTO)
                            .collect(Collectors.toList()))
                    .build();
        }

    }

    public Certificate toEntity(CreateCertificateDTO dto) {
        if (dto == null) {
            return null;
        }
        if (dto instanceof CreateOfficialCertificateDTO createOfficialCertificateDTO) {
            return OfficialCertificate.builder()
                    .address(addressRepository.findByAddressId(addressMapper.toEntity(createOfficialCertificateDTO
                            .getCreateAddressDTO()).getAddressId()))
                    .certificateType(createOfficialCertificateDTO.getCertificateType())
                    .recommendations(createOfficialCertificateDTO.getRecommendations())
                    .documentId(createOfficialCertificateDTO.getDocumentId())
                    .floor(createOfficialCertificateDTO.getFloor())
                    .door(createOfficialCertificateDTO.getDoor())
                    .climateZone(createOfficialCertificateDTO.getClimateZone())
                    .cadastreMeters(createOfficialCertificateDTO.getCadastreMeters())
                    .buildingYear(createOfficialCertificateDTO.getBuildingYear())
                    .buildingUse(createOfficialCertificateDTO.getBuildingUse())
                    .nonRenewablePrimaryQualification(createOfficialCertificateDTO
                            .getNonRenewablePrimaryQualification())
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
        } else if (dto instanceof CreateUnofficialCertificateDTO createUnofficialCertificateDTO) {
            return UnofficialCertificate.builder()
                     .address(addressRepository.findByAddressId(addressMapper.toEntity(createUnofficialCertificateDTO
                             .getCreateAddressDTO()).getAddressId()))
                     .certificateType(createUnofficialCertificateDTO.getCertificateType())
                     .recommendations(createUnofficialCertificateDTO.getRecommendations())
                     .floor(createUnofficialCertificateDTO.getFloor())
                     .door(createUnofficialCertificateDTO.getDoor())
                     .climateZone(createUnofficialCertificateDTO.getClimateZone())
                     .buildingYear(createUnofficialCertificateDTO.getBuildingYear())
                     .buildingUse(createUnofficialCertificateDTO.getBuildingUse())
                     .nonRenewablePrimaryQualification(createUnofficialCertificateDTO
                             .getNonRenewablePrimaryQualification())
                     .nonRenewablePrimaryEnergy(createUnofficialCertificateDTO.getNonRenewablePrimaryEnergy())
                     .co2Qualification(createUnofficialCertificateDTO.getCo2Qualification())
                     .co2Emissions(createUnofficialCertificateDTO.getCo2Emissions())
                     .finalEnergyConsumption(createUnofficialCertificateDTO.getFinalEnergyConsumption())
                     .annualCost(createUnofficialCertificateDTO.getAnnualCost())
                     .electricVehicle(createUnofficialCertificateDTO.isElectricVehicle())
                     .solarThermal(createUnofficialCertificateDTO.isSolarThermal())
                     .photovoltaicSolar(createUnofficialCertificateDTO.isPhotovoltaicSolar())
                     .biomass(createUnofficialCertificateDTO.isBiomass())
                     .districtNet(createUnofficialCertificateDTO.isDistrictNet())
                     .geothermal(createUnofficialCertificateDTO.isGeothermal())
                     .insulation(createUnofficialCertificateDTO.getInsulation())
                     .windowEfficiency(createUnofficialCertificateDTO.getWindowEfficiency())
                     .heatingQualification(createUnofficialCertificateDTO.getHeatingQualification())
                     .heatingEmissions(createUnofficialCertificateDTO.getHeatingEmissions())
                     .refrigerationQualification(createUnofficialCertificateDTO.getRefrigerationQualification())
                     .refrigerationEmissions(createUnofficialCertificateDTO.getRefrigerationEmissions())
                     .acsQualification(createUnofficialCertificateDTO.getAcsQualification())
                     .acsEmissions(createUnofficialCertificateDTO.getAcsEmissions())
                     .lightingQualification(createUnofficialCertificateDTO.getLightingQualification())
                     .lightingEmissions(createUnofficialCertificateDTO.getLightingEmissions())
                     .residentialUseVentilation(createUnofficialCertificateDTO.getResidentialUseVentilation())
                     .energeticRehabilitation(createUnofficialCertificateDTO.isEnergeticRehabilitation())
                     .creationDate(createUnofficialCertificateDTO.getCreationDate())
                     .build();
        } else {
            return Certificate.builder()
                    .address(addressRepository.findByAddressId(addressMapper.toEntity(dto.getCreateAddressDTO())
                            .getAddressId()))
                    .certificateType(dto.getCertificateType())
                    .recommendations(dto.getRecommendations())
                    .build();
        }

    }
}