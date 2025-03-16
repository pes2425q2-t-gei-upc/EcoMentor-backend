package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.controllers;


import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.*;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import org.springframework.http.ResponseEntity;
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

    public CertificateGetController(GetAllCertificatesUseCase getAllCertificatesUseCase, GetCertificateByCertificateIdUseCase getCertificateByIdUseCase, GetCertificateByAddressUseCase getCertificateByAddressUseCase, GetCertificateByClimateZoneUseCase getCertificateByClimateZoneUseCase
    , GetCertificateByNonRenewablePrimaryQualificationUseCase getCertificateByNonRenewablePrimaryQualificationUseCase
    , GetCertificateByCo2QualificationUseCase getCertificateByCo2Emissions, GetCertificateByACSQualificationUseCase getCertificateByACSQualification, GetCertificateByHeatingQualificationUseCase getCertificateByHeatingQualification
    , GetCertificateByLightingQualificationUseCase getCertificateByLightingQualification, GetCertificateByRefrigerationQualificationUseCase getCertificateByRefrigerationQualification
    , GetCertificateBySolarThermalUseCase getCertificateBySolarThermal, GetCertificateByPhotovoltaicSolarUseCase getCertificateByPhotovoltaicSolar, GetCertificateByEnergeticRehabilitationUseCase getCertificateByEnergeticRehabilitation
    , GetCertificateByDistrictNetUseCase getCertificateByDistrictNet, GetCertificateByElectricVehicleUseCase getCertificateByElectricVehicle, GetCertificateByGeothermalUseCase getCertificateByGeothermal
    , GetCertificateByBiomassUseCase getCertificateByBiomass) {
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
    public ResponseEntity<List<CertificateDTO>> getAllCertificateUseCase() {
        List<CertificateDTO> certificates =  getAllCertificatesUseCase.execute();
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/{certificateId}")
    public ResponseEntity<CertificateDTO> getCertificateByIdUseCase(@PathVariable Long certificateId) {
        CertificateDTO certificate = getCertificateByIdUseCase.execute(certificateId);
        if (certificate == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificate);
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<List<CertificateDTO>> getCertificateByAddressUseCase(@PathVariable Long addressId) {
        List<CertificateDTO> certificates = getCertificateByAddressUseCase.execute(addressId);
        if (certificates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/climateZone/{climateZone}")
    public ResponseEntity<List<CertificateDTO>> getCertificateByClimateZoneUseCase(@PathVariable String climateZone) {
        List<CertificateDTO> certificates =  getCertificateByClimateZoneUseCase.execute(climateZone);
        if (certificates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/nonRenewablePrimaryQualification/{nonRenewablePrimaryQualification}")
    public ResponseEntity<List<CertificateDTO>> getCertificateByNonRenewablePrimaryQualificationUseCase(@PathVariable Qualification nonRenewablePrimaryQualification) {
        List<CertificateDTO> certificates = getCertificateByNonRenewablePrimaryQualificationUseCase.execute(nonRenewablePrimaryQualification);
        if (certificates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/co2Qualification/{co2Qualification}")
    public ResponseEntity<List<CertificateDTO>> getCertificateByCo2Qualification(@PathVariable Qualification co2Qualification) {
        List<CertificateDTO> certificates = getCertificateByCo2Emissions.execute(co2Qualification);
        if (certificates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/acsQualification/{acsQualification}")
    public ResponseEntity<List<CertificateDTO>> getCertificateByACSQualification(@PathVariable Qualification acsQualification) {
        List<CertificateDTO> certificates = getCertificateByACSQualification.execute(acsQualification);
        if (certificates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/heatingQualification/{heatingQualification}")
    public ResponseEntity<List<CertificateDTO>> getCertificateByHeatingQualification(@PathVariable Qualification heatingQualification) {
        List<CertificateDTO> certificates = getCertificateByHeatingQualification.execute(heatingQualification);
        if (certificates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/lightingQualification/{lightingQualification}")
    public ResponseEntity<List<CertificateDTO>> getCertificateByLightingQualification(@PathVariable Qualification lightingQualification) {
        List<CertificateDTO> certificates = getCertificateByLightingQualification.execute(lightingQualification);
        if (certificates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/refrigerationQualification/{refrigerationQualification}")
    public ResponseEntity<List<CertificateDTO>> getCertificateByRefrigerationQualification(@PathVariable Qualification refrigerationQualification) {
        List<CertificateDTO> certificates = getCertificateByRefrigerationQualification.execute(refrigerationQualification);
        if (certificates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/solarThermal/{solarThermal}")
    public ResponseEntity<List<CertificateDTO>> getCertificateBySolarThermal(@PathVariable boolean solarThermal) {
        List<CertificateDTO> certificates = getCertificateBySolarThermal.execute(solarThermal);
        if (certificates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/photovoltaicSolar/{photovoltaicSolar}")
    public ResponseEntity<List<CertificateDTO>> getCertificateByPhotovoltaicSolar(@PathVariable boolean photovoltaicSolar) {
        List<CertificateDTO> certificates = getCertificateByPhotovoltaicSolar.execute(photovoltaicSolar);
        if (certificates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/energeticRehabilitation/{energeticRehabilitation}")
    public ResponseEntity<List<CertificateDTO>> getCertificateByEnergeticRehabilitation(@PathVariable boolean energeticRehabilitation) {
        List<CertificateDTO> certificates = getCertificateByEnergeticRehabilitation.execute(energeticRehabilitation);
        if (certificates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/districtNet/{districtNet}")
    public ResponseEntity<List<CertificateDTO>> getCertificateByDistrictNet(@PathVariable boolean districtNet) {
        List<CertificateDTO> certificates = getCertificateByDistrictNet.execute(districtNet);
        if (certificates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/electricVehicle/{electricVehicle}")
    public ResponseEntity<List<CertificateDTO>> getCertificateByElectricVehicle(@PathVariable boolean electricVehicle) {
        List<CertificateDTO> certificates = getCertificateByElectricVehicle.execute(electricVehicle);
        if (certificates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/geothermal/{geothermal}")
    public ResponseEntity<List<CertificateDTO>> getCertificateByGeothermal(@PathVariable boolean geothermal) {
        List<CertificateDTO> certificates = getCertificateByGeothermal.execute(geothermal);
        if (certificates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/biomass/{biomass}")
    public ResponseEntity<List<CertificateDTO>> getCertificateByBiomass(@PathVariable boolean biomass) {
        List<CertificateDTO> certificates = getCertificateByBiomass.execute(biomass);
        if (certificates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(certificates);
    }

}


