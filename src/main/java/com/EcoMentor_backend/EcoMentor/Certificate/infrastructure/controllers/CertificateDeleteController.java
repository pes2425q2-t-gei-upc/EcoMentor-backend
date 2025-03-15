package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.DeleteCertificateUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/certificates")
public class CertificateDeleteController {

    private final DeleteCertificateUseCase deleteCertificateUseCase;

    public CertificateDeleteController(DeleteCertificateUseCase deleteCertificateUseCase) {
        this.deleteCertificateUseCase = deleteCertificateUseCase;
    }
    @DeleteMapping("/{certificateId}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable Long certificateId){
        try {
            deleteCertificateUseCase.execute(certificateId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (RuntimeException e) { //! Hay que modificarlo por una exception personalizada
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
