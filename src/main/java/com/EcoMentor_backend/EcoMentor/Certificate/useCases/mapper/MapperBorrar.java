package com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper;

import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.BorrarOfficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.OfficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.mapper.RecommendationMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MapperBorrar {
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final RecommendationMapper recommendationMapper;

    public MapperBorrar(AddressMapper addressMapper, AddressRepository addressRepository, RecommendationMapper recommendationMapper) {
        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
        this.recommendationMapper = recommendationMapper;
    }

    public BorrarOfficialCertificateDTO toDTO(OfficialCertificate officialCertificate) {
        return BorrarOfficialCertificateDTO.builder()
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

}
