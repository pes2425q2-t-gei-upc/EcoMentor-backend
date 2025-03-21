package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.controllers;



import com.EcoMentor_backend.EcoMentor.Certificate.useCases.*;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificate")
public class CertificateGetController {
    private final GetAllCertificatesUseCase getAllCertificatesUseCase;
    private final GetCertificateByCertificateIdUseCase getCertificateByIdUseCase;
    private final GetCertificateByAddressUseCase getCertificateByAddressUseCase;
    private final GetCertificateByParameterUseCase getCertificateByParameterUseCase;
    private final GetCertificateByMinMaxRangeUseCase getCertificateByMinMaxRangeUseCase;
    private final GetCertificateByCertificateIdWFEUseCase getCertificateByCertificateIdWithoutUseCase;

    public CertificateGetController(GetCertificateByParameterUseCase getCertificateByParameterUseCase, GetAllCertificatesUseCase getAllCertificatesUseCase,
                                    GetCertificateByCertificateIdUseCase getCertificateByIdUseCase, GetCertificateByAddressUseCase getCertificateByAddressUseCase,
                                    GetCertificateByMinMaxRangeUseCase getCertificateByMinMaxRangeUseCase,
                                    GetCertificateByCertificateIdWFEUseCase getCertificateByCertificateIdWithoutUseCase) {
        this.getAllCertificatesUseCase = getAllCertificatesUseCase;
        this.getCertificateByIdUseCase = getCertificateByIdUseCase;
        this.getCertificateByAddressUseCase = getCertificateByAddressUseCase;
        this.getCertificateByParameterUseCase = getCertificateByParameterUseCase;
        this.getCertificateByMinMaxRangeUseCase = getCertificateByMinMaxRangeUseCase;
        this.getCertificateByCertificateIdWithoutUseCase = getCertificateByCertificateIdWithoutUseCase;
    }

    @GetMapping
    public List<CertificateWithoutForeignEntitiesDTO> getAllCertificateUseCase() {
        return getAllCertificatesUseCase.execute();
    }

    @GetMapping("/{certificateId}")
    public CertificateDTO getCertificateByIdUseCase(@PathVariable Long certificateId) {
        return getCertificateByIdUseCase.execute(certificateId);
    }

    @GetMapping("/address/{addressId}")
    public List<CertificateWithoutForeignEntitiesDTO> getCertificateByAddressUseCase(@PathVariable Long addressId) {
        return getCertificateByAddressUseCase.execute(addressId);
    }

    @GetMapping("/parameter")
    public List<CertificateWithoutForeignEntitiesDTO> getCertificateByParameter(@RequestParam String parameter, @RequestParam String value) {
        return getCertificateByParameterUseCase.execute(parameter, value);
    }

    @GetMapping("/range")
    public List<CertificateWithoutForeignEntitiesDTO> getCertificateByMinMaxRange(@RequestParam String parameter, @RequestParam String min, @RequestParam String max) {
        return getCertificateByMinMaxRangeUseCase.execute(parameter, min, max);
    }
    @GetMapping("/wfe/{certificateId}")
    public CertificateWithoutForeignEntitiesDTO getCertificateByCertificateIdWithoutUseCase(@PathVariable Long certificateId) {
        return getCertificateByCertificateIdWithoutUseCase.execute(certificateId);
    }

}


