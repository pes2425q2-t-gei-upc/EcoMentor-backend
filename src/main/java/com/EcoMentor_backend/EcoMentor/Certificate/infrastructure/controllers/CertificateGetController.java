package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.controllers;


import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.*;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
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

    @GetMapping("/certificate/all")
    public List<CertificateDTO> getAllCertificateUseCase() {
        return getAllCertificatesUseCase.execute();
    }

    @GetMapping("/certificate/{id}")
    public CertificateDTO getCertificateByIdUseCase(@PathVariable Long id) {
        return getCertificateByIdUseCase.execute(id);
    }

    @GetMapping("/certificate/address/{addressId}")
    public List<CertificateDTO> getCertificateByAddressUseCase(@PathVariable Long addressId) {
        return getCertificateByAddressUseCase.execute(addressId);
    }

    @GetMapping("/certificate/{climateZone}")
    public List<CertificateDTO> getCertificateByClimateZoneUseCase(@PathVariable String climateZone) {
        return getCertificateByClimateZoneUseCase.execute(climateZone);
    }

    @GetMapping("/certificate/nonRenewablePrimaryQualification")
    public List<CertificateDTO> getCertificateByNonRenewablePrimaryQualificationUseCase(@PathVariable Qualification nonRenewablePrimaryQualification) {
        return getCertificateByNonRenewablePrimaryQualificationUseCase.execute(nonRenewablePrimaryQualification);
    }

    @GetMapping("/certificate/{co2Qualification}")
    public List<CertificateDTO> getCertificateByCo2Qualification(@PathVariable Qualification co2Qualification) {
        return getCertificateByCo2Emissions.execute(co2Qualification);
    }

    @GetMapping("/certificate/{acsQualification}")
    public List<CertificateDTO> getCertificateByACSQualification(@PathVariable Qualification acsQualification) {
        return getCertificateByACSQualification.execute(acsQualification);
    }

    @GetMapping("/certificate/{heatingQualification}")
    public List<CertificateDTO> getCertificateByHeatingQualification(@PathVariable Qualification heatingQualification) {
        return getCertificateByHeatingQualification.execute(heatingQualification);
    }

    @GetMapping("/certificate/{lightingQualification}")
    public List<CertificateDTO> getCertificateByLightingQualification(@PathVariable Qualification lightingQualification) {
        return getCertificateByLightingQualification.execute(lightingQualification);
    }

    @GetMapping("/certificate/{refrigerationQualification}")
    public List<CertificateDTO> getCertificateByRefrigerationQualification(@PathVariable Qualification refrigerationQualification) {
        return getCertificateByRefrigerationQualification.execute(refrigerationQualification);
    }

    @GetMapping("/certificate/{solarThermal}")
    public List<CertificateDTO> getCertificateBySolarThermal(@PathVariable boolean solarThermal) {
        return getCertificateBySolarThermal.execute(solarThermal);
    }

    @GetMapping("/certificate/{photovoltaicSolar}")
    public List<CertificateDTO> getCertificateByPhotovoltaicSolar(@PathVariable boolean photovoltaicSolar) {
        return getCertificateByPhotovoltaicSolar.execute(photovoltaicSolar);
    }

    @GetMapping("/certificate/{energeticRehabilitation}")
    public List<CertificateDTO> getCertificateByEnergeticRehabilitation(@PathVariable boolean energeticRehabilitation) {
        return getCertificateByEnergeticRehabilitation.execute(energeticRehabilitation);
    }

    @GetMapping("/certificate/{districtNet}")
    public List<CertificateDTO> getCertificateByDistrictNet(@PathVariable boolean districtNet) {
        return getCertificateByDistrictNet.execute(districtNet);
    }

    @GetMapping("/certificate/{electricVehicle}")
    public List<CertificateDTO> getCertificateByElectricVehicle(@PathVariable boolean electricVehicle) {
        return getCertificateByElectricVehicle.execute(electricVehicle);
    }

    @GetMapping("/certificate/{geothermal}")
    public List<CertificateDTO> getCertificateByGeothermal(@PathVariable boolean geothermal) {
        return getCertificateByGeothermal.execute(geothermal);
    }

    @GetMapping("/certificate/{biomass}")
    public List<CertificateDTO> getCertificateByBiomass(@PathVariable boolean biomass) {
        return getCertificateByBiomass.execute(biomass);
    }

}


