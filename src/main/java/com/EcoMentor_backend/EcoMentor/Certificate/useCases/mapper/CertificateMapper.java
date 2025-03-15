package com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.OfficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateOfficialCertificateDTO;
import org.springframework.stereotype.Component;

@Component
public class CertificateMapper {

    public CertificateDTO toDTO(Certificate certificate) {
        if (certificate == null) {
            return null;
        }

        if (certificate instanceof OfficialCertificate officialCertificate) {
            return OfficialCertificateDTO.builder()
                    .certificateId(officialCertificate.getCertificateId())
                    .address(officialCertificate.getAddress())
                    .certificateType(officialCertificate.getCertificateType())
                    .recommendations(officialCertificate.getRecommendations())
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
        else {
            return CertificateDTO.builder()
                    .certificateId(certificate.getCertificateId())
                    .address(certificate.getAddress())
                    .certificateType(certificate.getCertificateType())
                    .recommendations(certificate.getRecommendations())
                    .build();
        }

    }

    public Certificate toEntity(CreateCertificateDTO dto) {
        if (dto == null) {
            return null;
        }
        if(dto instanceof CreateOfficialCertificateDTO createOfficialCertificateDTO) {
            return OfficialCertificate.builder()
                    .address(createOfficialCertificateDTO.getAddress())
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
        else {
            return Certificate.builder()
                    .address(dto.getAddress())
                    .certificateType(dto.getCertificateType())
                    .recommendations(dto.getRecommendations())
                    .build();
        }

    }
}
