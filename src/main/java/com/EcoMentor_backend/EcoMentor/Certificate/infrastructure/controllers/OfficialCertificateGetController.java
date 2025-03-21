package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetOfficialCertificataByCertificateIdUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.OfficialCertificateDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/official_certificate")
public class OfficialCertificateGetController {
    private final GetOfficialCertificataByCertificateIdUseCase getOfficialCertificateByIdUseCase;

    public OfficialCertificateGetController(GetOfficialCertificataByCertificateIdUseCase getOfficialCertificateByIdUseCase) {
        this.getOfficialCertificateByIdUseCase = getOfficialCertificateByIdUseCase;
    }
    @GetMapping("/{certificateId}")
    public OfficialCertificateDTO getOfficialCertificateById(@PathVariable Long certificateId) {
        return getOfficialCertificateByIdUseCase.execute(certificateId);
    }


}
