package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.useCases.CreateCertificateUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.useCases.dto.CreateCertificateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/certificates")
public class CertificatePostController {
    private final CreateCertificateUseCase createCertificateUseCase;

    public CertificatePostController(CreateCertificateUseCase createCertificateUseCase) {this.createCertificateUseCase = createCertificateUseCase;}

    @PostMapping
    public ResponseEntity<Void> createCertificate(@RequestBody @Validated CreateCertificateDTO certificate) {
        createCertificateUseCase.execute(certificate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/official_certificates")
    public ResponseEntity<Void> createOfficialCertificate(@RequestBody @Validated CreateCertificateDTO officialCertificate) {
        createCertificateUseCase.execute(officialCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
