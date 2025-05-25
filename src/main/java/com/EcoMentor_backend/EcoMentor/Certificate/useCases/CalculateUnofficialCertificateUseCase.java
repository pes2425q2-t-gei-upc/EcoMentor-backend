package com.EcoMentor_backend.EcoMentor.Certificate.useCases;

import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.AchivementProgressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.AddCertificateToAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.CreateAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculateUnofficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculatorResultsDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateUnofficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class CalculateUnofficialCertificateUseCase {
    private final CertificateRepository certificateRepository;
    private final AddCertificateToAddressUseCase addCertificateToAddressUseCase;
    private final AddressRepository addressRepository;
    private final CreateAddressUseCase createAddressUseCase;
    private final CertificateMapper certificateMapper;
    private final AchivementProgressUseCase achievementProgressUseCase;


    public CalculatorResultsDTO execute(CalculateUnofficialCertificateDTO calculateUnofficialCertificateDTO,
                                        long userId) {

        boolean solarThermal = calculateUnofficialCertificateDTO.isSolarThermal();
        boolean photovoltaicSolar = calculateUnofficialCertificateDTO.isPhotovoltaicSolar();
        String buildingUse = calculateUnofficialCertificateDTO.getBuildingUse();
        float heatingConsumption = calculateUnofficialCertificateDTO.getHeatingConsumption();
        float refrigerationConsumption = calculateUnofficialCertificateDTO.getRefrigerationConsumption();
        float acsConsumption = calculateUnofficialCertificateDTO.getAcsConsumption();
        float lightingConsumption = calculateUnofficialCertificateDTO.getLightingConsumption();
        String climateZone = calculateUnofficialCertificateDTO.getClimateZone();
        float insulation = certificateRepository.calculateAproxInsulation(calculateUnofficialCertificateDTO
                .getInsulation(), buildingUse);
        float windowEfficiency = certificateRepository
                .calculateAproxWindowEfficiciency(calculateUnofficialCertificateDTO
                .getWindowEfficiency(), buildingUse);
        float residentialUseVentilation = certificateRepository
                .calculateAproxResidentialUseVentilation(calculateUnofficialCertificateDTO
                        .getResidentialUseVentilation(), buildingUse);
        KindOfHeating typeOfHeating = calculateUnofficialCertificateDTO.getKindOfHeating();
        KindOfHeating typeOfAcs = calculateUnofficialCertificateDTO.getKindOfAcs();
        KindOfRefrigeration typeOfRefrigeration = calculateUnofficialCertificateDTO.getKindOfRefrigeration();
        float cadastreMeters = calculateUnofficialCertificateDTO.getCadastreMeters();

        CreateAddressDTO createAddressDTO = calculateUnofficialCertificateDTO.getCreateAddressDTO();
        Long id;
        if (addressRepository.existsAddressByAddressNameAndAddressNumber(createAddressDTO.getAddressName(),
                createAddressDTO.getAddressNumber())) {

            id = addressRepository.findAddressByAddressNameAndAddressNumber(createAddressDTO.getAddressName(),
                    createAddressDTO.getAddressNumber()).getAddressId();
        } else {
            id = createAddressUseCase.execute(createAddressDTO);
        }

        CalculatorResultsDTO results = certificateRepository.calculateQualificationsForANewCertificate(climateZone,
                buildingUse,
                solarThermal, photovoltaicSolar, insulation, windowEfficiency, residentialUseVentilation,
                typeOfHeating, typeOfAcs, typeOfRefrigeration, heatingConsumption, acsConsumption,
                refrigerationConsumption, lightingConsumption, cadastreMeters);

        CreateUnofficialCertificateDTO unofficialCertificateDTO = new CreateUnofficialCertificateDTO();
        unofficialCertificateDTO.setCertificateType(CertificateType.UNOFFICIAL);
        unofficialCertificateDTO.setCreateAddressDTO(createAddressDTO);
        unofficialCertificateDTO.setFloor(calculateUnofficialCertificateDTO.getFloor());
        unofficialCertificateDTO.setDoor(calculateUnofficialCertificateDTO.getDoor());
        unofficialCertificateDTO.setCadastreMeters(cadastreMeters);
        unofficialCertificateDTO.setClimateZone(climateZone);
        unofficialCertificateDTO.setBuildingYear(calculateUnofficialCertificateDTO.getBuildingYear());
        unofficialCertificateDTO.setBuildingUse(buildingUse);
        unofficialCertificateDTO.setNonRenewablePrimaryEnergy(results.getIoNonRenewablePrimaryEnergy());
        unofficialCertificateDTO.setNonRenewablePrimaryQualification(results.getNonRenewablePrimaryQualification());
        unofficialCertificateDTO.setCo2Emissions(results.getIoCO2E());
        unofficialCertificateDTO.setCo2Qualification(results.getCo2Qualification());
        unofficialCertificateDTO.setFinalEnergyConsumption(heatingConsumption + refrigerationConsumption
                + acsConsumption + lightingConsumption);
        unofficialCertificateDTO.setAnnualCost(calculateUnofficialCertificateDTO.getAnnualCost());
        unofficialCertificateDTO.setElectricVehicle(calculateUnofficialCertificateDTO.isElectricVehicle());
        unofficialCertificateDTO.setSolarThermal(solarThermal);
        unofficialCertificateDTO.setPhotovoltaicSolar(photovoltaicSolar);
        unofficialCertificateDTO.setBiomass(typeOfHeating == KindOfHeating.BIOMASSA
                || typeOfAcs == KindOfHeating.BIOMASSA);
        unofficialCertificateDTO.setDistrictNet(typeOfHeating == KindOfHeating.DISTRICTE
                || typeOfAcs == KindOfHeating.DISTRICTE || typeOfRefrigeration == KindOfRefrigeration.DISTRICTE);
        unofficialCertificateDTO.setGeothermal(typeOfHeating == KindOfHeating.GEOTERMIA
                || typeOfAcs == KindOfHeating.GEOTERMIA || typeOfRefrigeration == KindOfRefrigeration.GEOTERMIA);
        unofficialCertificateDTO.setInsulation(calculateUnofficialCertificateDTO.getInsulation());
        unofficialCertificateDTO.setWindowEfficiency(calculateUnofficialCertificateDTO.getWindowEfficiency());
        unofficialCertificateDTO.setHeatingEmissions(results.getIoHeating());
        unofficialCertificateDTO.setHeatingQualification(results.getHeatingQualification());
        unofficialCertificateDTO.setRefrigerationEmissions(results.getIoRefrigeration());
        unofficialCertificateDTO.setRefrigerationQualification(results.getRefrigerationQualification());
        unofficialCertificateDTO.setAcsEmissions(results.getIoACS());
        unofficialCertificateDTO.setAcsQualification(results.getAcsQualification());
        unofficialCertificateDTO.setLightingEmissions(results.getIoLighting());
        unofficialCertificateDTO.setLightingQualification(results.getLightingQualification());
        unofficialCertificateDTO.setResidentialUseVentilation(calculateUnofficialCertificateDTO
                .getResidentialUseVentilation());
        unofficialCertificateDTO.setEnergeticRehabilitation(calculateUnofficialCertificateDTO
                .isEnergeticRehabilitation());
        unofficialCertificateDTO.setCreationDate(new java.sql.Date(System.currentTimeMillis()));
        Certificate certificate = certificateMapper.toEntity(unofficialCertificateDTO);
        certificateRepository.save(certificate);
        addCertificateToAddressUseCase.execute(id, certificate.getCertificateId());

        achievementProgressUseCase.execute(userId, 5L);

        //todo: Aqui falta para el achivement 6,7,8,9,10 pero no lo puedo hacer porque no existe la manera de conseguir
        //todo: la media del certificado, Sanz hazlo tu mira el discord de logros



        return new CalculatorResultsDTO(certificate.getCertificateId(), results.getIoNonRenewablePrimaryEnergy(),
                 results.getIoCO2E(), results.getIoHeating(),  results.getIoRefrigeration(),
                 results.getIoACS(), results.getIoLighting(), results.getNonRenewablePrimaryQualification(),
                results.getCo2Qualification(), results.getHeatingQualification(),
                results.getRefrigerationQualification(), results.getAcsQualification(),
                results.getLightingQualification());

    }
}
