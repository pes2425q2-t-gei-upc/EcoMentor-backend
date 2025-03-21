package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.controllers;


import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.*;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.OfficialCertificateDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificate")
public class CertificateGetController {
    private final GetAllCertificatesUseCase getAllCertificatesUseCase;
    private final GetCertificateByCertificateIdUseCase getCertificateByIdUseCase;
    private final GetCertificateByAddressUseCase getCertificateByAddressUseCase;
    private final GetCertificateByClimateZoneUseCase getCertificateByClimateZoneUseCase;
    private final GetCertificateByNonRenewablePrimaryQualificationUseCase getCertificateByNonRenewablePrimaryQualificationUseCase;
    private final GetCertificateByCo2QualificationUseCase getCertificateByCo2Emissions;
    private final GetCertificateByACSQualificationUseCase getCertificateByACSQualification;
    private final GetCertificateByHeatingQualificationUseCase getCertificateByHeatingQualification;
    private final GetCertificateByLightingQualificationUseCase getCertificateByLightingQualification;
    private final GetCertificateByRefrigerationQualificationUseCase getCertificateByRefrigerationQualification;
    private final GetCertificateBySolarThermalUseCase getCertificateBySolarThermal;
    private final GetCertificateByPhotovoltaicSolarUseCase getCertificateByPhotovoltaicSolar;
    private final GetCertificateByEnergeticRehabilitationUseCase getCertificateByEnergeticRehabilitation;
    private final GetCertificateByDistrictNetUseCase getCertificateByDistrictNet;
    private final GetCertificateByElectricVehicleUseCase getCertificateByElectricVehicle;
    private final GetCertificateByGeothermalUseCase getCertificateByGeothermal;
    private final GetCertificateByBiomassUseCase getCertificateByBiomass;
    private final GetOfficialCertificataByCertificateIdUseCase getOfficialCertificateByIdUseCase;

    public CertificateGetController(GetAllCertificatesUseCase getAllCertificatesUseCase, GetCertificateByCertificateIdUseCase getCertificateByIdUseCase, GetCertificateByAddressUseCase getCertificateByAddressUseCase, GetCertificateByClimateZoneUseCase getCertificateByClimateZoneUseCase
    , GetCertificateByNonRenewablePrimaryQualificationUseCase getCertificateByNonRenewablePrimaryQualificationUseCase
    , GetCertificateByCo2QualificationUseCase getCertificateByCo2Emissions, GetCertificateByACSQualificationUseCase getCertificateByACSQualification, GetCertificateByHeatingQualificationUseCase getCertificateByHeatingQualification
    , GetCertificateByLightingQualificationUseCase getCertificateByLightingQualification, GetCertificateByRefrigerationQualificationUseCase getCertificateByRefrigerationQualification
    , GetCertificateBySolarThermalUseCase getCertificateBySolarThermal, GetCertificateByPhotovoltaicSolarUseCase getCertificateByPhotovoltaicSolar, GetCertificateByEnergeticRehabilitationUseCase getCertificateByEnergeticRehabilitation
    , GetCertificateByDistrictNetUseCase getCertificateByDistrictNet, GetCertificateByElectricVehicleUseCase getCertificateByElectricVehicle, GetCertificateByGeothermalUseCase getCertificateByGeothermal
    , GetCertificateByBiomassUseCase getCertificateByBiomass, GetOfficialCertificataByCertificateIdUseCase getOfficialCertificateByIdUseCase) {
        this.getAllCertificatesUseCase = getAllCertificatesUseCase;
        this.getCertificateByIdUseCase = getCertificateByIdUseCase;
        this.getCertificateByAddressUseCase = getCertificateByAddressUseCase;
        this.getCertificateByClimateZoneUseCase = getCertificateByClimateZoneUseCase;
        this.getCertificateByNonRenewablePrimaryQualificationUseCase = getCertificateByNonRenewablePrimaryQualificationUseCase;
        this.getCertificateByCo2Emissions = getCertificateByCo2Emissions;
        this.getCertificateByACSQualification = getCertificateByACSQualification;
        this.getCertificateByHeatingQualification = getCertificateByHeatingQualification;
        this.getCertificateByLightingQualification = getCertificateByLightingQualification;
        this.getCertificateByRefrigerationQualification = getCertificateByRefrigerationQualification;
        this.getCertificateBySolarThermal = getCertificateBySolarThermal;
        this.getCertificateByPhotovoltaicSolar = getCertificateByPhotovoltaicSolar;
        this.getCertificateByEnergeticRehabilitation = getCertificateByEnergeticRehabilitation;
        this.getCertificateByDistrictNet = getCertificateByDistrictNet;
        this.getCertificateByElectricVehicle = getCertificateByElectricVehicle;
        this.getCertificateByGeothermal = getCertificateByGeothermal;
        this.getCertificateByBiomass = getCertificateByBiomass;
        this.getOfficialCertificateByIdUseCase = getOfficialCertificateByIdUseCase;
    }

    @GetMapping
    public List<CertificateDTO> getAllCertificateUseCase() {
        return getAllCertificatesUseCase.execute();
    }

    @GetMapping("/{certificateId}")
    public CertificateDTO getCertificateByIdUseCase(@PathVariable Long certificateId) {
        return getCertificateByIdUseCase.execute(certificateId);
    }

    @GetMapping("/address/{addressId}")
    public List<CertificateDTO> getCertificateByAddressUseCase(@PathVariable Long addressId) {
        return getCertificateByAddressUseCase.execute(addressId);
    }

    @GetMapping("/climateZone/{climateZone}")
    public List<CertificateDTO> getCertificateByClimateZoneUseCase(@PathVariable String climateZone) {
        return getCertificateByClimateZoneUseCase.execute(climateZone);
    }

    @GetMapping("/nonRenewablePrimaryQualification/{nonRenewablePrimaryQualification}")
    public List<CertificateDTO> getCertificateByNonRenewablePrimaryQualificationUseCase(@PathVariable Qualification nonRenewablePrimaryQualification) {
        return getCertificateByNonRenewablePrimaryQualificationUseCase.execute(nonRenewablePrimaryQualification);
    }

    @GetMapping("/co2Qualification/{co2Qualification}")
    public List<CertificateDTO> getCertificateByCo2Qualification(@PathVariable Qualification co2Qualification) {
        return getCertificateByCo2Emissions.execute(co2Qualification);
    }

    @GetMapping("/acsQualification/{acsQualification}")
    public List<CertificateDTO> getCertificateByACSQualification(@PathVariable Qualification acsQualification) {
        return getCertificateByACSQualification.execute(acsQualification);
    }

    @GetMapping("/heatingQualification/{heatingQualification}")
    public List<CertificateDTO> getCertificateByHeatingQualification(@PathVariable Qualification heatingQualification) {
        return getCertificateByHeatingQualification.execute(heatingQualification);
    }

    @GetMapping("/lightingQualification/{lightingQualification}")
    public List<CertificateDTO> getCertificateByLightingQualification(@PathVariable Qualification lightingQualification) {
        return getCertificateByLightingQualification.execute(lightingQualification);
    }

    @GetMapping("/refrigerationQualification/{refrigerationQualification}")
    public List<CertificateDTO> getCertificateByRefrigerationQualification(@PathVariable Qualification refrigerationQualification) {
        return getCertificateByRefrigerationQualification.execute(refrigerationQualification);
    }

    @GetMapping("/solarThermal/{solarThermal}")
    public List<CertificateDTO> getCertificateBySolarThermal(@PathVariable boolean solarThermal) {
        return getCertificateBySolarThermal.execute(solarThermal);
    }

    @GetMapping("/photovoltaicSolar/{photovoltaicSolar}")
    public List<CertificateDTO> getCertificateByPhotovoltaicSolar(@PathVariable boolean photovoltaicSolar) {
        return getCertificateByPhotovoltaicSolar.execute(photovoltaicSolar);
    }

    @GetMapping("/energeticRehabilitation/{energeticRehabilitation}")
    public List<CertificateDTO> getCertificateByEnergeticRehabilitation(@PathVariable boolean energeticRehabilitation) {
        return getCertificateByEnergeticRehabilitation.execute(energeticRehabilitation);
    }

    @GetMapping("/districtNet/{districtNet}")
    public List<CertificateDTO> getCertificateByDistrictNet(@PathVariable boolean districtNet) {
        return getCertificateByDistrictNet.execute(districtNet);
    }

    @GetMapping("/electricVehicle/{electricVehicle}")
    public List<CertificateDTO> getCertificateByElectricVehicle(@PathVariable boolean electricVehicle) {
        return getCertificateByElectricVehicle.execute(electricVehicle);
    }

    @GetMapping("/geothermal/{geothermal}")
    public List<CertificateDTO> getCertificateByGeothermal(@PathVariable boolean geothermal) {
        return getCertificateByGeothermal.execute(geothermal);
    }

    @GetMapping("/biomass/{biomass}")
    public List<CertificateDTO> getCertificateByBiomass(@PathVariable boolean biomass) {
        return getCertificateByBiomass.execute(biomass);
    }

}


