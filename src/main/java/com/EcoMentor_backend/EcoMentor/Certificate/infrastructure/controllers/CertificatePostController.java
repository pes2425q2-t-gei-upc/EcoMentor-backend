package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.CreateCertificateUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateBySetOfValuesUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateCertificateDTO;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Validated
@RequestMapping("/api/certificate")
public class CertificatePostController {
    private final CreateCertificateUseCase createCertificateUseCase;
    private final GetCertificateBySetOfValuesUseCase getCertificateBySetOfValuesUseCase;

    public CertificatePostController(CreateCertificateUseCase createCertificateUseCase,
                                     GetCertificateBySetOfValuesUseCase getCertificateBySetOfValuesUseCase) {
        this.createCertificateUseCase = createCertificateUseCase;
        this.getCertificateBySetOfValuesUseCase = getCertificateBySetOfValuesUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> createCertificate(@RequestBody @Validated CreateCertificateDTO certificate) {
        createCertificateUseCase.execute(certificate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/setOfValues")
    public ResponseEntity<List<CertificateWithoutForeignEntitiesDTO>> getCertificateBySetOfValues(
            @RequestParam String parameter, @RequestBody List<Object> values) {
        List<CertificateWithoutForeignEntitiesDTO> certificates = getCertificateBySetOfValuesUseCase.execute(parameter,
                values);
        if (certificates.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(certificates);
        }
    }


}
