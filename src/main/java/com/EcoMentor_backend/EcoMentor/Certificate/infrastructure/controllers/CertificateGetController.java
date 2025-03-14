package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.controllers;


import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.useCases.*;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.useCases.dto.CertificateDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class CertificateGetController {
    private final GetAllCertificatesUseCase getAllCertificatesUseCase;
    private final GetCertificateByCertificateIdUseCase getCertificateByIdUseCase;
    private final GetCertificateByAddressUseCase getCertificateByAddressUseCase;
    private final GetCertificateByClimateZoneUseCase getCertificateByClimateZoneUseCase;
    private final GetCertificateByNonRenewablePrimaryQualificationUseCase getCertificateByNonRenewablePrimaryQualificationUseCase;
    private final GetCertificateByCo2Qualification getCertificateByCo2Emissions;
    private final GetCertificateByACSQualification getCertificateByACSQualification;
    private final GetCertificateByHeatingQualification getCertificateByHeatingQualification;
    private final GetCertificateByLightingQualification getCertificateByLightingQualification;
    private final GetCertificateByRefrigerationQualification getCertificateByRefrigerationQualification;
    private final GetCertificateBySolarThermal getCertificateBySolarThermal;
    private final GetCertificateByPhotovoltaicSolar getCertificateByPhotovoltaicSolar;
    private final GetCertificateByEnergeticRehabilitation getCertificateByEnergeticRehabilitation;
    private final GetCertificateByDistrictNet getCertificateByDistrictNet;
    private final GetCertificateByElectricVehicle getCertificateByElectricVehicle;
    private final GetCertificateByGeothermal getCertificateByGeothermal;
    private final GetCertificateByBiomass getCertificateByBiomass;

    public CertificateGetController(GetAllCertificatesUseCase getAllCertificatesUseCase, GetCertificateByCertificateIdUseCase getCertificateByIdUseCase, GetCertificateByAddressUseCase getCertificateByAddressUseCase, GetCertificateByClimateZoneUseCase getCertificateByClimateZoneUseCase
    , GetCertificateByNonRenewablePrimaryQualificationUseCase getCertificateByNonRenewablePrimaryQualificationUseCase
    , GetCertificateByCo2Qualification getCertificateByCo2Emissions, GetCertificateByACSQualification getCertificateByACSQualification, GetCertificateByHeatingQualification getCertificateByHeatingQualification
    , GetCertificateByLightingQualification getCertificateByLightingQualification, GetCertificateByRefrigerationQualification getCertificateByRefrigerationQualification
    , GetCertificateBySolarThermal getCertificateBySolarThermal, GetCertificateByPhotovoltaicSolar getCertificateByPhotovoltaicSolar, GetCertificateByEnergeticRehabilitation getCertificateByEnergeticRehabilitation
    , GetCertificateByDistrictNet getCertificateByDistrictNet, GetCertificateByElectricVehicle getCertificateByElectricVehicle, GetCertificateByGeothermal getCertificateByGeothermal
    , GetCertificateByBiomass getCertificateByBiomass) {
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
    }

    @GetMapping
    public List<CertificateDTO> getAllCertificateUseCase() {
        return getAllCertificatesUseCase.execute();
    }

    @GetMapping
    public CertificateDTO getCertificateByIdUseCase(@RequestParam Long id) {
        return getCertificateByIdUseCase.execute(id);
    }

    @GetMapping("/address/{addressId}")
    public List<CertificateDTO> getCertificateByAddressUseCase(@PathVariable Long addressId) {
        return getCertificateByAddressUseCase.execute(addressId);
    }

    @GetMapping
    public List<CertificateDTO> getCertificateByClimateZoneUseCase(@RequestParam String climaticZone) {
        return getCertificateByClimateZoneUseCase.execute(climaticZone);
    }

    @GetMapping
    public List<CertificateDTO> getCertificateByNonRenewablePrimaryQualificationUseCase(@RequestParam Qualification qualification) {
        return getCertificateByNonRenewablePrimaryQualificationUseCase.execute(qualification);
    }

    @GetMapping
    public List<CertificateDTO> getCertificateByCo2Qualification(@RequestParam Qualification Co2Qualification) {
        return getCertificateByCo2Emissions.execute(Co2Qualification);
    }

    @GetMapping
    public List<CertificateDTO> getCertificateByACSQualification(@RequestParam Qualification ACSQualification) {
        return getCertificateByACSQualification.execute(ACSQualification);
    }

    @GetMapping
    public List<CertificateDTO> getCertificateByHeatingQualification(@RequestParam Qualification HeatingQualification) {
        return getCertificateByHeatingQualification.execute(HeatingQualification);
    }

    @GetMapping
    public List<CertificateDTO> getCertificateByLightingQualification(@RequestParam Qualification LightingQualification) {
        return getCertificateByLightingQualification.execute(LightingQualification);
    }

    @GetMapping
    public List<CertificateDTO> getCertificateByRefrigerationQualification(@RequestParam Qualification RefrigerationQualification) {
        return getCertificateByRefrigerationQualification.execute(RefrigerationQualification);
    }

    @GetMapping
    public List<CertificateDTO> getCertificateBySolarThermal(@RequestParam boolean solarThermal) {
        return getCertificateBySolarThermal.execute(solarThermal);
    }

    @GetMapping
    public List<CertificateDTO> getCertificateByPhotovoltaicSolar(@RequestParam boolean photovoltaicSolar) {
        return getCertificateByPhotovoltaicSolar.execute(photovoltaicSolar);
    }

    @GetMapping
    public List<CertificateDTO> getCertificateByEnergeticRehabilitation(@RequestParam boolean energeticRehabilitation) {
        return getCertificateByEnergeticRehabilitation.execute(energeticRehabilitation);
    }

    @GetMapping
    public List<CertificateDTO> getCertificateByDistrictNet(@RequestParam boolean districtNet) {
        return getCertificateByDistrictNet.execute(districtNet);
    }

    @GetMapping
    public List<CertificateDTO> getCertificateByElectricVehicle(@RequestParam boolean electricVehicle) {
        return getCertificateByElectricVehicle.execute(electricVehicle);
    }

    @GetMapping
    public List<CertificateDTO> getCertificateByGeothermal(@RequestParam boolean geothermal) {
        return getCertificateByGeothermal.execute(geothermal);
    }

    @GetMapping
    public List<CertificateDTO> getCertificateByBiomass(@RequestParam boolean biomass) {
        return getCertificateByBiomass.execute(biomass);
    }

}


